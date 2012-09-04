package net.phuonglm.regex;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NamedPattern {

	private static final Pattern GROUP_PATTERN = Pattern.compile("\\(\\{(\\w+)\\}");

	private Pattern pattern;
	private String namedPattern;
	private Map<String,List<NameGroupData>> groupInfo;


  public static NamedPattern compile(String regex) {
      return new NamedPattern(regex, 0);
  }

  public static NamedPattern compile(String regex, int flags) {
      return new NamedPattern(regex, flags);
  }

  private NamedPattern(String regex, int flags) {
    namedPattern = regex;
    pattern = buildStandardPattern(regex, flags);
    groupInfo = extractGroupInfo(regex);
	}

  public int indexOf(String groupName) {
    return indexOf(groupName, 0);
  }

  public int indexOf(String groupName, int index) {
    int idx = -1;
    if (groupInfo.containsKey(groupName)) {
      List<NameGroupData> list = groupInfo.get(groupName);
      if (index < list.size()) {
        idx = list.get(index).getGroupIndex();
      }
    }
    return idx;
  }

	public NamedMatcherImpl matcher(CharSequence input) {
		return new NamedMatcherImpl(this, input);
	}

  public boolean matches(String s){
    return pattern.matcher(s).matches();
  }

	public Pattern pattern() {
		return pattern;
	}

	public String toString() {
		return namedPattern;
	}

	static private boolean isEscapedParent(String s, int pos) {
		int numSlashes = 0;
		while (pos > 0 && (s.charAt(pos - 1) == '\\')) {
			pos--;
			numSlashes++;
		}
		return numSlashes % 2 != 0;
	}

	static private boolean isNoncapturingParent(String s, int pos) {
		int len = s.length();
		boolean isLookbehind = false;
		if (pos >= 0 && pos + 4 < len) {
			String pre = s.substring(pos, pos+4);
			isLookbehind = pre.equals("(?<=") || pre.equals("(?<!");
		}
		return (pos >= 0 && pos + 2 < len) &&
				s.charAt(pos + 1) == '?' &&
				(isLookbehind || s.charAt(pos + 2) != '<');
	}

	static private int countOpenParens(String s, int pos) {
		Pattern p = Pattern.compile("\\(");
		Matcher m = p.matcher(s.subSequence(0, pos));

		int numParens = 0;

		while (m.find()) {
			String match = m.group(0);

			// ignore escaped parens
			if (isEscapedParent(s, m.start())) continue;

			if (match.equals("(") && !isNoncapturingParent(s, m.start())) {
				numParens++;
			}
		}
		return numParens;
	}

	static public Map<String,List<NameGroupData>> extractGroupInfo(String namedPattern) {
		Map<String,List<NameGroupData>> groupInfo = new LinkedHashMap<String,List<NameGroupData>>();
		Matcher matcher = GROUP_PATTERN.matcher(namedPattern);
		while(matcher.find()) {

			int pos = matcher.start();

			// ignore escaped paren
			if (isEscapedParent(namedPattern, pos)) continue;

			String name = matcher.group(1);
			int groupIndex = countOpenParens(namedPattern, pos);

			List<NameGroupData> list;
			if (groupInfo.containsKey(name)) {
				list = groupInfo.get(name);
			} else {
				list = new ArrayList<NameGroupData>();
			}
			list.add(new NameGroupData(groupIndex,pos));
			groupInfo.put(name, list);
		}
		return groupInfo;
	}

	static private Pattern buildStandardPattern(String namedPattern, Integer flags) {

		StringBuilder s = new StringBuilder(namedPattern);
		Matcher m = GROUP_PATTERN.matcher(s);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			
			if (isEscapedParent(s.toString(), start)) {
				continue;
			}

			s.replace(start, end, "(");
			m.reset();
		}
		
		return Pattern.compile(s.toString(), flags);
	}

}


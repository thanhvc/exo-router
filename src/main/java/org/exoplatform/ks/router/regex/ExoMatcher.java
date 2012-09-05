package org.exoplatform.ks.router.regex;

import java.util.regex.MatchResult;

public interface ExoMatcher extends MatchResult {

	public String group(String groupName);
	public String group(int group);
	public boolean matches();
	public boolean find();
	public String replaceAll(String replacement);

}

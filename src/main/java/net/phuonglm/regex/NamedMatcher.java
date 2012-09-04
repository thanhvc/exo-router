package net.phuonglm.regex;

import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;

public interface NamedMatcher extends MatchResult {

	public String group(String groupName);

}

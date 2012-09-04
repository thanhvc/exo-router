/**
 * Copyright (C) 2012 The named-regexp Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.phuonglm.regex;

import java.util.regex.Matcher;

public class NamedMatcherImpl implements NamedMatcher {

	private Matcher matcher;
	private NamedPattern parentPattern;

	NamedMatcherImpl(NamedPattern parentPattern, CharSequence input) {
		this.parentPattern = parentPattern;
		this.matcher = parentPattern.pattern().matcher(input);
	}

	public boolean matches() {
		return matcher.matches();
	}

	public boolean find() {
		return matcher.find();
	}

	public String group() {
		return matcher.group();
	}

	public String group(int group) {
		return matcher.group(group);
	}

	public int groupCount() {
		return matcher.groupCount();
	}

	public String group(String groupName) {
		return group(groupIndex(groupName));
	}

	private int groupIndex(String groupName) {
		int idx = parentPattern.indexOf(groupName);
		return idx > -1 ? idx + 1 : -1;
	}

	public int start() {
		return matcher.start();
	}

	public int start(int group) {
		return matcher.start(group);
	}

	public int end() {
		return matcher.end();
	}

	public int end(int group) {
		return matcher.end(group);
	}

	public String replaceAll(String replacement) {
		return matcher.replaceAll(replacement);
	}

	public boolean equals(Object obj) {
		return matcher.equals(obj);
	}

	public int hashCode() {
		return matcher.hashCode();
	}

	public String toString() {
		return matcher.toString();
	}

}

/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.ks.router;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import junit.framework.TestCase;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com Apr
 * 23, 2012
 */
public class RegexTest extends TestCase {

  /**
   * Lop ky tu Compares characters with [post] match "post12343","bean"
   * http://www.cantuananh.com/category/web-develop/regular-expression/
   * 
   * @throws Exception
   */
  public void testCharacter() throws Exception {
    Pattern pattern = Pattern.compile("[post]");
    Matcher matcher = pattern.matcher("post12343");
    if (matcher.matches()) {
      assertEquals("post12343", matcher.group());
    }
  }

  /**
   * Lop ku tu phu dinh
   * 
   * @throws Exception
   */
  public void testCharacterNagative() throws Exception {
    Pattern pattern = Pattern.compile("p[^u]st");
    Matcher matcher = pattern.matcher("pust");
    assertFalse(matcher.find());

    matcher = pattern.matcher("post");
    assertTrue(matcher.find());
  }

  /**
   * Start of line with "cat"
   * 
   * @throws Exception
   */
  public void testStartString() throws Exception {
    Pattern pattern = Pattern.compile("^cat");
    Matcher matcher = pattern.matcher("bed in cat");
    assertFalse(matcher.find());

    matcher = pattern.matcher("cat on bed");
    assertTrue(matcher.find());
  }

  /**
   * End of line with "cat"
   * 
   * @throws Exception
   */
  public void testEndString() throws Exception {
    Pattern pattern = Pattern.compile("cat$");
    Matcher matcher = pattern.matcher("bed in cat");
    assertTrue(matcher.find());

    matcher = pattern.matcher("cat on bed");
    assertFalse(matcher.find());
  }

  public void testCompileFail() throws Exception {
    try {
      Pattern.compile("abc(jhds");
      fail();
    } catch (PatternSyntaxException ex) {

    }

    try {
      Pattern.compile("abc)jhds");
      fail();
    } catch (PatternSyntaxException ex) {

    }
    
    try {
      Pattern.compile("abc\\)jhds");
    } catch (PatternSyntaxException ex) {

    }

  }
  
  public void testSpecialCharacter() throws Exception {
   
    try {
      Pattern.compile("abc\\)jhds");
    } catch (PatternSyntaxException ex) {

    }

  }
  
  public void testMatching() throws Exception {
    Pattern captPattern = Pattern.compile("abc.*def(.*)ghij(.*)klmn");
    Matcher captMatcher = captPattern.matcher("abcXXXdef123456789ghij987654321klmn");
    
    assertTrue(captMatcher.find());
    assertEquals("123456789", captMatcher.group(1));
    assertEquals("987654321", captMatcher.group(2));
    
    //pattern get argument
    Pattern captPattern1 = Pattern.compile("/(\\{.*\\})/topic/(\\{.*\\})");
    Matcher captMatcher1 = captPattern1.matcher("/{pageID}/topic/{topicID}");
    
    assertTrue(captMatcher1.find());
    assertEquals("{pageID}", captMatcher1.group(1));
    assertEquals("{topicID}", captMatcher1.group(2));
    
    //pattern get argument
    String INPUT = "/{pageID}/topic/{topicID}";
    Pattern captPattern2 = Pattern.compile("\\{([a-zA-Z_][a-zA-Z_0-9]*)\\}");
    Matcher captMatcher2 = captPattern2.matcher(INPUT);
    String patternArg = INPUT;
    String patternStr = INPUT;

    List<String> args = new ArrayList<String>();
    while (captMatcher2.find()) {
      args.add(captMatcher2.group(1));
    }

    for (String arg : args) {
      patternArg = patternArg.replace("{" + arg + "}", "\\{<[^/]+>" + arg + "\\}");
      patternStr = patternStr.replace("{" + arg + "}", "\\{" + arg + "<[^/]+>\\}");
    }
 
    Pattern patternNew = Pattern.compile(patternArg);
    assertEquals("/\\{<[^/]+>pageID\\}/topic/\\{<[^/]+>topicID\\}", patternArg);
    assertEquals("/\\{pageID<[^/]+>\\}/topic/\\{topicID<[^/]+>\\}", patternStr);
  }

}

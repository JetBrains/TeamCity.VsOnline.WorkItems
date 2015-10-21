/*
 * Copyright 2000-2015 JetBrains s.r.o.
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

package jetbrains.buildServer.vsonline;

import jetbrains.buildServer.BaseTestCase;
import jetbrains.buildServer.issueTracker.IssueData;
import jetbrains.buildServer.util.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Oleg Rybak (oleg.rybak@jetbrains.com)
 */
public class VsOnlineIssueParserTest extends BaseTestCase {

  private VsOnlineIssueParser myParser;

  @BeforeMethod
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    myParser = new VsOnlineIssueParser();
  }

  @Test
  public void testParseIssue_issue1() throws Exception {
    IssueData data = myParser.parse(getTestFileContents("issue1.json"));
    assertNotNull(data);
    assertEquals("1", data.getId());
    assertEquals("New", data.getState());
    assertEquals("Feature", data.getType());
  }

  @Test(expectedExceptions = RuntimeException.class)
  public void testParseIssue_Invalid() throws Exception {
      myParser.parse(getTestFileContents("invalid.txt"));
  }

  private String getTestFileContents(@NotNull final String fileName) throws Exception {
    return FileUtil.readResourceAsString(getClass(), "/" + fileName, Charset.forName("UTF-8"));
  }
}

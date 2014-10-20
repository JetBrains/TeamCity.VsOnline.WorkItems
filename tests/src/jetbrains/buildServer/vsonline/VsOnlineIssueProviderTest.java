/*
 * Copyright 2000-2014 JetBrains s.r.o.
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
import jetbrains.buildServer.issueTracker.IssueFetcher;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.util.CollectionsUtil;
import org.jmock.Mockery;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Map;

/**
 * @author Oleg Rybak <oleg.rybak@jetbrains.com>
 */
public class VsOnlineIssueProviderTest extends BaseTestCase {

  private Mockery m;

  private IssueFetcher myFetcher;

  private VsOnlineIssueProvider myProvider;

  @BeforeMethod
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    m = new Mockery();
    myFetcher = m.mock(IssueFetcher.class);
    myProvider = new VsOnlineIssueProvider(myFetcher);
  }

  @Test
  public void testPropertiesProcessor() throws Exception{
    final Map<String, String> properties = CollectionsUtil.asMap("project", "");
    final PropertiesProcessor processor = myProvider.getPropertiesProcessor();
    final Collection<InvalidProperty> result = processor.process(properties);
    assertNotNull(result);
    assertNotEmpty(result);
    boolean propertyFound = false;
    for (InvalidProperty p: result) {
      if ("project".equals(p.getPropertyName())) {
        propertyFound = true;
        break;
      }
    }
    assertTrue("Project property should be invalid", propertyFound);
  }

  @AfterMethod
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    m.assertIsSatisfied();
  }
}

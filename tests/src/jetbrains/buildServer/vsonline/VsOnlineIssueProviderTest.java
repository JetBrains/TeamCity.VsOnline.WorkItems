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
import org.jmock.Mockery;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

/**
 * @author Oleg Rybak <oleg.rybak@jetbrains.com>
 */
public class VsOnlineIssueProviderTest extends BaseTestCase {

  private Mockery m;

  private VsOnlineIssueProvider myProvider;

  @BeforeMethod
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    m = new Mockery();
    final IssueFetcher fetcher = m.mock(IssueFetcher.class);
    myProvider = new VsOnlineIssueProvider(fetcher);
  }

  @Test
  public void testPropertiesProcessor() throws Exception{
    final Set<String> mandatoryProperties = new HashSet<String>(Arrays.asList("project", "account", "username", "secure:password"));
    final Map<String, String> properties = new HashMap<String, String>();
    for (String name: mandatoryProperties) {
      properties.put(name, "");
    }
    final PropertiesProcessor processor = myProvider.getPropertiesProcessor();
    final Collection<InvalidProperty> result = processor.process(properties);
    assertNotNull(result);
    assertNotEmpty(result);
    Set<String> results = new HashSet<String>();
    for (InvalidProperty p: result) {
      if (mandatoryProperties.contains(p.getPropertyName())) {
        results.add(p.getPropertyName());
      }
    }
    assertTrue(results.containsAll(mandatoryProperties));
  }

  @AfterMethod
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    m.assertIsSatisfied();
  }
}

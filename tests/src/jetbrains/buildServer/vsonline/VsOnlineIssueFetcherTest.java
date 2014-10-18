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

import jetbrains.buildServer.issueTracker.IssueData;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.serverSide.impl.BaseServerTestCase;
import jetbrains.buildServer.util.CollectionsUtil;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.cache.EhCacheUtil;
import jetbrains.buildServer.util.cache.ResetCacheRegister;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Class for manual testing of {@code VsOnlineIssueFetcher}
 *
 * @author Oleg Rybak <oleg.rybak@jetbrains.com>
 */
public class VsOnlineIssueFetcherTest extends BaseServerTestCase {

  private VsOnlineIssueFetcher myFetcher;

  private Credentials myCredentials;

  @SuppressWarnings("unchecked")
  @BeforeMethod
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    final String login = System.getProperty("vsonline.test.login");
    final String password = System.getProperty("vsonline.test.password");
    if (login == null || "".equals(login.trim()) ||
            password == null || "".equals(password.trim())){
      throw new IllegalStateException("Provide $vsonline.test.login and $vsonline.test.password for tests to work");
    }


    EhCacheUtil util = new EhCacheUtil(
            myServer.getSingletonService(ServerPaths.class),
            myServer.getSingletonService(EventDispatcher.class),
            myServer.getSingletonService(ResetCacheRegister.class)
            );
    myFetcher = new VsOnlineIssueFetcher(util);
    myCredentials = new UsernamePasswordCredentials(login, password);
  }

  @Test
  public void testSimple() throws Exception {
    IssueData data = myFetcher.getIssue("http://olegrybak.visualstudio.com", "1", myCredentials);
    assertNotNull(data);
    System.out.println(data);
    System.out.println(CollectionsUtil.to_s(CollectionsUtil.flatten(data.getAllFields())));
  }
}

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

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.issueTracker.AbstractIssueFetcher;
import jetbrains.buildServer.issueTracker.IssueData;
import jetbrains.buildServer.util.cache.EhCacheHelper;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Oleg Rybak <oleg.rybak@jetbrains.com>
 */
public class VsOnlineIssueFetcher extends AbstractIssueFetcher {

  private final static Logger LOG = Logger.getInstance(VsOnlineIssueFetcher.class.getName());

  // host + / collection / area
  // http://account.visualstudio.com/collection/project
  private final Pattern p = Pattern.compile("^(http[s]?://.+\\.visualstudio.com)/(.+)/(.+)/$");
  private String fetchHost;

  private static final String URL_TEMPLATE_GET_ISSUE = "%s/%s/_apis/wit/workitems/%s?$expand=all&api-version=%s";

  @NotNull
  private final VsOnlineIssueParser myIssueParser;

  public VsOnlineIssueFetcher(@NotNull final EhCacheHelper cacheHelper,
                              @NotNull final VsOnlineIssueParser issueParser) {
    super(cacheHelper);
    myIssueParser = issueParser;
  }

  /*
   * see doc:
   * http://www.visualstudio.com/en-us/integrate/reference/reference-vso-work-item-overview-vsi
   * http://www.visualstudio.com/en-us/integrate/reference/reference-vso-work-item-work-items-vsi#byids
   */
  private static final String apiVersion = "1.0"; // rest api version

  // host is sanitized in the form "host/collection/project/"
  @NotNull
  public IssueData getIssue(@NotNull final String host, @NotNull final String id, @Nullable final Credentials credentials) throws Exception {
    final Matcher m = p.matcher(host);
    if (!m.matches()) {
      throw new RuntimeException("Wrong host for issue tracker provided: [" + host + "]");
    }
    final String hostOnly = m.group(1);
    final String collection = m.group(2);
    final String cacheKey = getUrl(host, id);
    final String restUrl = String.format(URL_TEMPLATE_GET_ISSUE, hostOnly, collection, id, apiVersion);
    return getFromCacheOrFetch(cacheKey, new FetchFunction() {
      @NotNull
      public IssueData fetch() throws Exception {
        InputStream body;
        try {
          body = fetchHttpFile(restUrl, credentials);
        } catch (IOException e) {
          LOG.error("Could not fetch issue with id [" + id + "]. Request url: [" + restUrl + ".", e);
          throw e;
        }
        final String responseString = IOUtils.toString(body, "UTF-8");
        return myIssueParser.parse(responseString);
      }
    });
  }

  public void setFetchHost(String host) {
    fetchHost = host;
  }

  @NotNull
  public String getUrl(@NotNull String host, @NotNull String id) {
    return fetchHost + "_workitems/edit/" + id;
  }
}

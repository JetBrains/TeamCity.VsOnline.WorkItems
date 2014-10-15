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

import com.fasterxml.jackson.databind.ObjectMapper;
import jetbrains.buildServer.issueTracker.AbstractIssueFetcher;
import jetbrains.buildServer.issueTracker.IssueData;
import jetbrains.buildServer.util.CollectionsUtil;
import jetbrains.buildServer.util.ExceptionUtil;
import jetbrains.buildServer.util.cache.EhCacheUtil;
import org.apache.commons.httpclient.Credentials;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.Map;

/**
 * @author Oleg Rybak <oleg.rybak@jetbrains.com>
 */
public class VsOnlineIssueFetcher extends AbstractIssueFetcher {

  private static final String URL_TEMPLATE_GET_ISSUE = "%s/defaultcollection/_apis/wit/workitems/%s?$expand=all&api-version=%s";


  public VsOnlineIssueFetcher(@NotNull final EhCacheUtil cacheUtil) {
    super(cacheUtil);
  }

  /*
   * see doc:
   * http://www.visualstudio.com/en-us/integrate/reference/reference-vso-work-item-overview-vsi
   * http://www.visualstudio.com/en-us/integrate/reference/reference-vso-work-item-work-items-vsi#byids
   */

  private static final String apiVersion = "1.0-preview.2"; // rest api version

  @NotNull
  public IssueData getIssue(@NotNull final String host, @NotNull final String id, @Nullable final Credentials credentials) throws Exception {
    final String cacheKey = getCacheKey(host, credentials, id);
    final String restUrl = String.format(URL_TEMPLATE_GET_ISSUE, host, id, apiVersion);

    return getFromCacheOrFetch(cacheKey, new FetchFunction() {
      @NotNull
      public IssueData fetch() throws Exception {
        InputStream body = fetchHttpFile(restUrl, credentials);
        return doGetIssue(body);
      }
    });
  }

  @Override
  protected String getCacheKey(String host, Credentials credentials, String id) {
    return host + "|" + id;
  }

  private IssueData doGetIssue(@NotNull final InputStream input) throws Exception {
    final Map map = new ObjectMapper().readValue(input, Map.class);
    return parseIssueData(map);
  }

  private IssueData parseIssueData(@NotNull final Map map) {
    final Map fields = (Map) map.get("fields");
    final Map links = (Map) map.get("_links");
    final Map html = (Map) links.get("html");
    final String href = (String) html.get("href");

    return new IssueData(
            String.valueOf(map.get("id")),
            CollectionsUtil.asMap(
                    IssueData.SUMMARY_FIELD, (String) fields.get("System.Title"),
                    IssueData.STATE_FIELD, (String) fields.get("System.State"),
                    IssueData.TYPE_FIELD, (String) fields.get("System.WorkItemType"),
                    "href", href
            ),
            false, // todo: state
            "Feature".equals(fields.get("System.WorkItemType")),
            href
    );
  }

  // this implementation should not be called if issue is not fetched yet,
  // otherwise it will cause synchronous issue fetching
  @NotNull
  public String getUrl(@NotNull String host, @NotNull final Credentials credentials, @NotNull String id) {
    String result = "";
    try {
      final IssueData data = getIssue(host, id, credentials);
      result = data.getAllFields().get("href");
    } catch (Exception e) {
      ExceptionUtil.rethrowAsRuntimeException(e);
    }
    return result;
  }
}

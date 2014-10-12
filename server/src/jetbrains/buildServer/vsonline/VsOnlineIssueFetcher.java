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


  public VsOnlineIssueFetcher(@NotNull EhCacheUtil cacheUtil) {
    super(cacheUtil);
  }

  /*
  api.version - version of the api
  area - ex. 'tcissues' - project

   */

  private static final String apiVersion = "1.0-preview.2"; // rest api version
  private static final String account = "olegrybak"; // orybak.visualstudio.com
  private static final String area = "tcissues"; // aka project




  @NotNull
  public IssueData getIssue(@NotNull String host, @NotNull String id, @Nullable final Credentials credentials) throws Exception {
    final String cacheKey = area + "." + id;
    final String url = getUrl(host, id);
    final String restUrl = String.format(
            "https://%s.visualstudio.com/defaultcollection/_apis/wit/workitems/%s?api-version=%s",
            account, id, apiVersion
    );


    return getFromCacheOrFetch(cacheKey, new FetchFunction() {
      @NotNull
      public IssueData fetch() throws Exception {
        InputStream body = fetchHttpFile(restUrl, credentials);
        return doGetIssue(body, url);
      }
    });
  }

  private IssueData doGetIssue(@NotNull final InputStream input, String url) throws Exception {
    final Map map = new ObjectMapper().readValue(input, Map.class);
    return parseIssueData(url, map);
  }

  private IssueData parseIssueData(String url, Map map) {
    Map fields = (Map) map.get("fields");

    return new IssueData(
            String.valueOf(map.get("id")),
            CollectionsUtil.asMap(
                    IssueData.SUMMARY_FIELD, (String) fields.get("System.Title"),
                    IssueData.STATE_FIELD, (String) fields.get("System.State"),
                    IssueData.TYPE_FIELD, (String) fields.get("System.WorkItemType")
            ),
            false, // todo: state
            !"Feature".equals(map.get("System.WorkItemType")),
            url
    );



  }


  // https://fabrikam.visualstudio.com/DefaultCollection/_apis/wit/workItems/220

  // todo: issue id unique for the account??
  @NotNull
  public String getUrl(@NotNull String host, @NotNull String id) {
    return String.format("https://%s.visualstudio.com/DefaultCollection/_apis/wit/workItems/%s",
            account,
            id
            );
  }
}

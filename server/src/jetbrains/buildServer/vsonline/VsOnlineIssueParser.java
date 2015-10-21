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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.issueTracker.IssueData;
import jetbrains.buildServer.util.CollectionsUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Oleg Rybak (oleg.rybak@jetbrains.com)
 */
public class VsOnlineIssueParser {

  private final static Logger LOG = Logger.getInstance(VsOnlineIssueParser.class.getName());

  private interface Containers {
    String CONTAINER_FIELDS = "fields";
    String CONTAINER_LINKS  = "_links";
    String CONTAINER_HTML   = "html";
  }

  private interface Fields {
    String FIELD_SUMMARY = "System.Title";
    String FIELD_STATE   = "System.State";
    String FIELD_TYPE    = "System.WorkItemType";
    String FIELD_HREF    = "href";
  }

  public IssueData parse(@NotNull final String issueString) throws Exception {
    try {
      final Map map = new ObjectMapper().readValue(issueString, Map.class);
      return parseIssueData(map);
    } catch (Exception e) {
      LOG.error("Could not parse issue json. Error message is: " + e.getMessage());
      if (LOG.isDebugEnabled()) {
        LOG.debug("Could not parse issue json. Response (cut to first 100 symbols): ["
                + issueString.substring(Math.min(100, issueString.length() - 1))
        );
      }
      throw e;
    }
  }

  private IssueData parseIssueData(@NotNull final Map map) {
    final Map fields = getContainer(map, Containers.CONTAINER_FIELDS);
    final Map links = getContainer(map, Containers.CONTAINER_LINKS);
    final Map html = getContainer(links, Containers.CONTAINER_HTML);
    final String href = getField(html, Fields.FIELD_HREF);

    return new IssueData(
            String.valueOf(map.get("id")),
            CollectionsUtil.asMap(
                    IssueData.SUMMARY_FIELD, getField(fields, Fields.FIELD_SUMMARY),
                    IssueData.STATE_FIELD, getField(fields, Fields.FIELD_STATE),
                    IssueData.TYPE_FIELD, getField(fields, Fields.FIELD_TYPE),
                    "href", href
            ),
            false, // todo: state
            "Feature".equals(getField(fields, Fields.FIELD_TYPE)),
            href
    );
  }

  private Map getContainer(final Map map, @NotNull final String name) {
    return (Map) map.get(name);
  }

  private String getField(final Map map, @NotNull final String name) {
    return (String) map.get(name);
  }
}

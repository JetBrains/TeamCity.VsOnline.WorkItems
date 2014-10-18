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
import jetbrains.buildServer.issueTracker.AbstractIssueProvider;
import jetbrains.buildServer.issueTracker.IssueFetcher;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

/**
 * @author Oleg Rybak <oleg.rybak@jetbrains.com>
 */
public class VsOnlineIssueProvider extends AbstractIssueProvider {

  private static final Logger LOG = Logger.getInstance(VsOnlineIssueProvider.class.getName());

  public static final String TYPE = "vsonline";

  public VsOnlineIssueProvider(@NotNull final IssueFetcher fetcher) {
    super(TYPE, fetcher);
  }

  @Override
  @NotNull
  protected String extractId(@NotNull String match) {
    final Matcher matcher = myPattern.matcher(match);
    if (matcher.find() && matcher.groupCount() >= 1) {
      String group = matcher.group(1);
      if (group == null) {
        LOG.warn("Failed to extract the issue id. Regex=" + myPattern + ", input=" + match);
        return super.extractId(match);
      }
      return group;
    } else {
      return super.extractId(match);
    }
  }

}

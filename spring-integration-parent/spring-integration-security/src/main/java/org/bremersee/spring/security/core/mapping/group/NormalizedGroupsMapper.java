/*
 * Copyright 2024-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bremersee.spring.security.core.mapping.group;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.bremersee.spring.security.core.Group;
import org.bremersee.spring.security.core.NormalizedGroup;
import org.bremersee.spring.security.core.mapping.AbstractNormalizedMapper;
import org.bremersee.spring.security.core.mapping.CaseTransformation;
import org.jspecify.annotations.NonNull;

/**
 * The normalized mapper of groups.
 *
 * @author Christian Bremer
 */
public class NormalizedGroupsMapper extends AbstractNormalizedMapper<Group>
    implements GroupsMapper {

  /**
   * Instantiates a new normalized mapper of groups.
   *
   * @param defaultGroups the default groups
   * @param groupMapping the group mapping
   * @param groupPrefix the group prefix
   * @param groupCaseTransformation the group case transformation
   * @param groupStringReplacements the group string replacements
   */
  public NormalizedGroupsMapper(
      List<String> defaultGroups,
      Map<String, String> groupMapping,
      String groupPrefix,
      CaseTransformation groupCaseTransformation,
      Map<String, String> groupStringReplacements) {
    super(defaultGroups, groupMapping, groupPrefix, groupCaseTransformation,
        groupStringReplacements);
  }

  @Override
  protected @NonNull String getStringValue(@NonNull Group value) {
    return value.getName();
  }

  @Override
  protected @NonNull Group createTarget(@NonNull String mappedValue) {
    return NormalizedGroup.of(mappedValue);
  }

  @Override
  protected @NonNull String getDefaultPrefix() {
    return "";
  }

  @Override
  public @NonNull Collection<Group> mapGroups(
      @NonNull Collection<? extends Group> groups) {

    return map(groups);
  }
}

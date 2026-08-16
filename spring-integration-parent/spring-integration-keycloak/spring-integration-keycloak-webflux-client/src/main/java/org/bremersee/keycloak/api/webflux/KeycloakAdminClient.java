/*
 * Copyright 2026 the original author or authors.
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

package org.bremersee.keycloak.api.webflux;

import static java.util.Objects.requireNonNullElseGet;
import static org.springframework.util.ObjectUtils.isEmpty;

import org.bremersee.keycloak.api.GetGroupsParameters;
import org.bremersee.keycloak.api.GetUsersParameters;
import org.bremersee.keycloak.api.model.GroupRepresentation;
import org.bremersee.keycloak.api.model.UserRepresentation;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The keycloak admin client.
 *
 * @author Christian Bremer
 */
public class KeycloakAdminClient {

  private final AdminApi adminApi;

  /**
   * Instantiates a new Keycloak admin client.
   *
   * @param adminApi the admin api
   */
  public KeycloakAdminClient(AdminApi adminApi) {
    Assert.notNull(adminApi, "adminApi must not be null.");
    this.adminApi = adminApi;
  }

  /**
   * Returns a stream of users. Note that the 'credentials' field in the returned UserRepresentation
   * objects is typically not populated for performance reasons. If specific credential metadata is
   * required, use the dedicated 'GET /admin/realms/{realm}/users/{user-id}/credentials' endpoint.
   *
   * @param realm the realm name
   * @param parameters the parameters
   * @return the stream of users
   */
  public Flux<UserRepresentation> getUsers(
      @NonNull String realm,
      @Nullable GetUsersParameters parameters) {

    GetUsersParameters params = requireNonNullElseGet(parameters, GetUsersParameters::defaults);
    return adminApi.adminRealmsRealmUsersGet(
        realm,
        params.getBriefRepresentation(),
        params.getCreatedAfterAsString(),
        params.getCreatedBeforeAsString(),
        params.getEmail(),
        params.getEmailVerified(),
        params.getEnabled(),
        params.getExact(),
        params.getFirst(),
        params.getFirstName(),
        params.getIdpAlias(),
        params.getIdpUserId(),
        params.getLastName(),
        params.getMax(),
        params.getQuery(),
        params.getSearch(),
        params.getUsername());
  }

  /**
   * Get representation of the user.
   *
   * @param realm the realm name
   * @param userId the user ID
   * @param userProfileMetadata Indicates if the user profile metadata should be added to the
   *     response
   * @return the user
   */
  public Mono<UserRepresentation> getUserById(
      @NonNull String realm,
      @NonNull String userId,
      @Nullable Boolean userProfileMetadata) {
    return adminApi.adminRealmsRealmUsersUserIdGet(realm, userId, userProfileMetadata);
  }

  /**
   * Adds the given user to the given group.
   *
   * @param realm the realm name
   * @param userId the user ID
   * @param groupId the group ID
   * @return void
   */
  public Mono<Void> addUserToGroup(
      @NonNull String realm,
      @NonNull String userId,
      @NonNull String groupId) {
    return adminApi.adminRealmsRealmUsersUserIdGroupsGroupIdPut(realm, userId, groupId);
  }

  /**
   * Removes the given user from the given group.
   *
   * @param realm the realm name
   * @param userId the user ID
   * @param groupId the group ID
   * @return void
   */
  public Mono<Void> removeUserFromGroup(
      @NonNull String realm,
      @NonNull String userId,
      @NonNull String groupId) {
    return adminApi.adminRealmsRealmUsersUserIdGroupsGroupIdDelete(realm, userId, groupId);
  }

  /**
   * Get group hierarchy. Only `name` and `id` are returned. `subGroups` are only returned when
   * using the `search` or `q` parameter. If none of these parameters is provided, the top-level
   * groups are returned without `subGroups` being filled.
   *
   * @param realm the realm name
   * @param parameters the parameters
   * @return the stream of groups
   */
  public Flux<GroupRepresentation> getGroups(
      @NonNull String realm,
      @Nullable GetGroupsParameters parameters) {

    GetGroupsParameters params = requireNonNullElseGet(parameters, GetGroupsParameters::defaults);
    return adminApi.adminRealmsRealmGroupsGet(
        realm,
        params.getBriefRepresentation(),
        params.getExact(),
        params.getFirst(),
        params.getMax(),
        params.getPopulateHierarchy(),
        params.getQuery(),
        params.getSearch(),
        params.getSubGroupsCount());
  }

  /**
   * Gets group by ID.
   *
   * @param realm the realm name
   * @param groupId the group ID
   * @return the group
   */
  public Mono<GroupRepresentation> getGroupById(
      @NonNull String realm,
      @NonNull String groupId) {
    return adminApi.adminRealmsRealmGroupsGroupIdGet(realm, groupId);
  }

  /**
   * Gets group by path.
   *
   * @param realm the realm name
   * @param path the group path (e.g. '/groupname-1/groupsname-2')
   * @return the group
   */
  public Mono<GroupRepresentation> getGroupByPath(@NonNull String realm, @NonNull String path) {
    return adminApi.adminRealmsRealmGroupByPathPathGet(realm, path);
  }

  /**
   * Gets group members.
   *
   * @param realm the realm name
   * @param groupId the group ID
   * @param briefRepresentation Only return basic information (only guaranteed to return id,
   *     username, created, first and last name, email, enabled state, email verification state,
   *     federation link, and access. Note that it means that namely user attributes, required
   *     actions, and not before are not returned.)
   * @param first Pagination offset
   * @param max Maximum results size (defaults to 100)
   * @return the group members
   */
  public Flux<UserRepresentation> getGroupMembers(
      @NonNull String realm,
      @NonNull String groupId,
      @Nullable Boolean briefRepresentation,
      @Nullable Integer first,
      @Nullable Integer max) {

    return adminApi.adminRealmsRealmGroupsGroupIdMembersGet(
        realm,
        groupId,
        briefRepresentation,
        first,
        max);
  }

  /**
   * Creates or updates a group.
   *
   * @param realm the realm name
   * @param group the group
   * @return the group
   */
  public Mono<GroupRepresentation> saveGroup(
      @NonNull String realm,
      @NonNull GroupRepresentation group) {

    Assert.hasText(group.getName(), "Group name must not be null or empty.");
    if (isEmpty(group.getId())) {
      return adminApi.adminRealmsRealmGroupsPost(realm, Mono.just(group))
          .then(adminApi.adminRealmsRealmGroupByPathPathGet(realm, "/" + group.getName()));
    }
    return adminApi.adminRealmsRealmGroupsGroupIdPut(realm, group.getId(), Mono.just(group))
        .then(adminApi.adminRealmsRealmGroupsGroupIdGet(realm, group.getId()));
  }

  /**
   * Creates a subgroup.
   *
   * @param realm the realm name
   * @param groupId the parent group ID
   * @param group the subgroup
   * @return the subgroup
   */
  public Mono<GroupRepresentation> createSubGroup(
      @NonNull String realm,
      @NonNull String groupId,
      @NonNull GroupRepresentation group) {

    Assert.hasText(group.getName(), "Group name must not be null or empty.");
    return getGroupById(realm, groupId)
        .flatMap(parentGroup -> adminApi
            .adminRealmsRealmGroupsGroupIdChildrenPost(realm, groupId, Mono.just(group))
            .then(adminApi.adminRealmsRealmGroupByPathPathGet(
                realm, parentGroup.getPath() + "/" + group.getName())));
  }

  /**
   * Deletes group.
   *
   * @param realm the realm name
   * @param groupId the group ID
   * @return void
   */
  public Mono<Void> deleteGroup(@NonNull String realm, @NonNull String groupId) {
    return adminApi.adminRealmsRealmGroupsGroupIdDelete(realm, groupId);
  }

}

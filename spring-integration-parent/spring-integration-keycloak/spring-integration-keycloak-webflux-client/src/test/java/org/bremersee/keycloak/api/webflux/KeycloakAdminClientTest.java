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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.UUID;
import org.bremersee.keycloak.api.model.GroupRepresentation;
import org.bremersee.keycloak.api.model.UserRepresentation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The keycloak admin client test.
 *
 * @author Christian Bremer
 */
@ExtendWith({MockitoExtension.class})
class KeycloakAdminClientTest {

  private static final String REALM = "junit";

  private static final String USER_ID = UUID.randomUUID().toString();

  private static final String GROUP_ID = UUID.randomUUID().toString();

  @Mock
  private AdminApi adminApi;

  @InjectMocks
  private KeycloakAdminClient target;

  /**
   * Gets users.
   */
  @Test
  void getUsers() {
    UserRepresentation expected = mock(UserRepresentation.class);
    Flux<UserRepresentation> response = Flux.just(expected);
    doReturn(response)
        .when(adminApi)
        .adminRealmsRealmUsersGet(eq(REALM), any(), any(), any(), any(), any(), any(), any(), any(),
            any(), any(), any(), any(), any(), any(), any(), any());
    UserRepresentation actual = target.getUsers(REALM, null)
        .last()
        .block();
    assertThat(actual)
        .isEqualTo(expected);
  }

  /**
   * Gets user by id.
   */
  @Test
  void getUserById() {
    UserRepresentation expected = mock(UserRepresentation.class);
    Mono<UserRepresentation> response = Mono.just(expected);
    doReturn(response)
        .when(adminApi)
        .adminRealmsRealmUsersUserIdGet(eq(REALM), eq(USER_ID), any());
    UserRepresentation actual = target.getUserById(REALM, USER_ID, null).block();
    assertThat(actual)
        .isEqualTo(expected);
  }

  /**
   * Add user to group.
   */
  @Test
  void addUserToGroup() {
    Mono<Void> response = Mono.empty();
    doReturn(response)
        .when(adminApi)
        .adminRealmsRealmUsersUserIdGroupsGroupIdPut(REALM, USER_ID, GROUP_ID);
    target.addUserToGroup(REALM, USER_ID, GROUP_ID).block();
    verify(adminApi).adminRealmsRealmUsersUserIdGroupsGroupIdPut(REALM, USER_ID, GROUP_ID);
  }

  /**
   * Remove user from group.
   */
  @Test
  void removeUserFromGroup() {
    Mono<Void> response = Mono.empty();
    doReturn(response)
        .when(adminApi)
        .adminRealmsRealmUsersUserIdGroupsGroupIdDelete(REALM, USER_ID, GROUP_ID);
    target.removeUserFromGroup(REALM, USER_ID, GROUP_ID).block();
    verify(adminApi).adminRealmsRealmUsersUserIdGroupsGroupIdDelete(REALM, USER_ID, GROUP_ID);
  }

  /**
   * Gets groups.
   */
  @Test
  void getGroups() {
    GroupRepresentation expected = mock(GroupRepresentation.class);
    Flux<GroupRepresentation> response = Flux.just(expected);
    doReturn(response)
        .when(adminApi)
        .adminRealmsRealmGroupsGet(
            eq(REALM), any(), any(), any(), any(), any(), any(), any(), any());
    GroupRepresentation actual = target
        .getGroups(REALM, null)
        .last()
        .block();
    assertThat(actual)
        .isEqualTo(expected);
  }

  /**
   * Gets group by id.
   */
  @Test
  void getGroupById() {
    GroupRepresentation expected = mock(GroupRepresentation.class);
    Mono<GroupRepresentation> response = Mono.just(expected);
    doReturn(response)
        .when(adminApi)
        .adminRealmsRealmGroupsGroupIdGet(REALM, GROUP_ID);
    GroupRepresentation actual = target.getGroupById(REALM, GROUP_ID).block();
    assertThat(actual)
        .isEqualTo(expected);
  }

  /**
   * Gets group by path.
   */
  @Test
  void getGroupByPath() {
    String path = "/my-group";
    GroupRepresentation expected = mock(GroupRepresentation.class);
    Mono<GroupRepresentation> response = Mono.just(expected);
    doReturn(response)
        .when(adminApi)
        .adminRealmsRealmGroupByPathPathGet(REALM, path);
    GroupRepresentation actual = target.getGroupByPath(REALM, path).block();
    assertThat(actual)
        .isEqualTo(expected);
  }

  /**
   * Gets group members.
   */
  @Test
  void getGroupMembers() {
    UserRepresentation expected = mock(UserRepresentation.class);
    Flux<UserRepresentation> response = Flux.just(expected);
    doReturn(response)
        .when(adminApi)
        .adminRealmsRealmGroupsGroupIdMembersGet(eq(REALM), eq(GROUP_ID), any(), any(), any());
    UserRepresentation actual = target.getGroupMembers(REALM, GROUP_ID, null, null, null)
        .last()
        .block();
    assertThat(actual)
        .isEqualTo(expected);
  }

  /**
   * Create group.
   */
  @Test
  void createGroup() {
    GroupRepresentation expected = mock(GroupRepresentation.class);
    doReturn("my-group")
        .when(expected)
        .getName();
    doReturn(Mono.empty())
        .when(adminApi)
        .adminRealmsRealmGroupsPost(eq(REALM), any());
    doReturn(Mono.just(expected))
        .when(adminApi)
        .adminRealmsRealmGroupByPathPathGet(REALM, "/my-group");
    GroupRepresentation actual = target.saveGroup(REALM, expected).block();
    assertThat(actual)
        .isEqualTo(expected);
  }

  /**
   * Update group.
   */
  @Test
  void updateGroup() {
    GroupRepresentation expected = mock(GroupRepresentation.class);
    doReturn("my-group")
        .when(expected)
        .getName();
    doReturn(GROUP_ID)
        .when(expected)
        .getId();
    doReturn(Mono.empty())
        .when(adminApi)
        .adminRealmsRealmGroupsGroupIdPut(eq(REALM), eq(GROUP_ID), any());
    doReturn(Mono.just(expected))
        .when(adminApi)
        .adminRealmsRealmGroupsGroupIdGet(REALM, GROUP_ID);
    GroupRepresentation actual = target.saveGroup(REALM, expected).block();
    assertThat(actual)
        .isEqualTo(expected);
  }

  /**
   * Create sub group.
   */
  @Test
  void createSubGroup() {
    GroupRepresentation parentGroup = mock(GroupRepresentation.class);
    doReturn("/parent")
        .when(parentGroup)
        .getPath();
    doReturn(Mono.just(parentGroup))
        .when(adminApi)
        .adminRealmsRealmGroupsGroupIdGet(REALM, GROUP_ID);

    GroupRepresentation subGroup = mock(GroupRepresentation.class);
    doReturn("sub-group")
        .when(subGroup)
        .getName();
    doReturn(Mono.empty())
        .when(adminApi)
        .adminRealmsRealmGroupsGroupIdChildrenPost(eq(REALM), eq(GROUP_ID), any());
    doReturn(Mono.just(subGroup))
        .when(adminApi)
        .adminRealmsRealmGroupByPathPathGet(REALM, "/parent/sub-group");
    GroupRepresentation actual = target.createSubGroup(REALM, GROUP_ID, subGroup).block();
    assertThat(actual)
        .isEqualTo(subGroup);
  }

  /**
   * Gets subgroups.
   */
  @Test
  void getSubGroups() {
    GroupRepresentation subGroup = mock(GroupRepresentation.class);
    List<GroupRepresentation> expected = List.of(subGroup);
    doReturn(Flux.fromIterable(expected))
        .when(adminApi)
        .adminRealmsRealmGroupsGroupIdChildrenGet(
            eq(REALM), eq(GROUP_ID), any(), any(), any(), any(), any(), any());
    List<GroupRepresentation> actual = target.getSubGroups(REALM, GROUP_ID, null)
        .collectList().block();
    assertThat(actual)
        .isEqualTo(expected);
  }

  /**
   * Delete group.
   */
  @Test
  void deleteGroup() {
    doReturn(Mono.empty())
        .when(adminApi)
        .adminRealmsRealmGroupsGroupIdDelete(REALM, GROUP_ID);
    target.deleteGroup(REALM, GROUP_ID).block();
    verify(adminApi)
        .adminRealmsRealmGroupsGroupIdDelete(REALM, GROUP_ID);
  }
}
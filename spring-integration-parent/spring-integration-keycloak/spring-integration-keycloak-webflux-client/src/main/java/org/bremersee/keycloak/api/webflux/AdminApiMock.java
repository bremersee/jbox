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

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;
import org.bremersee.keycloak.api.model.AbstractPolicyRepresentation;
import org.bremersee.keycloak.api.model.AccessToken;
import org.bremersee.keycloak.api.model.AdminEventRepresentation;
import org.bremersee.keycloak.api.model.AuthenticationExecutionInfoRepresentation;
import org.bremersee.keycloak.api.model.AuthenticationExecutionRepresentation;
import org.bremersee.keycloak.api.model.AuthenticationFlowRepresentation;
import org.bremersee.keycloak.api.model.AuthenticatorConfigInfoRepresentation;
import org.bremersee.keycloak.api.model.AuthenticatorConfigRepresentation;
import org.bremersee.keycloak.api.model.CertificateRepresentation;
import org.bremersee.keycloak.api.model.ClientInitialAccessCreatePresentation;
import org.bremersee.keycloak.api.model.ClientInitialAccessPresentation;
import org.bremersee.keycloak.api.model.ClientPoliciesRepresentation;
import org.bremersee.keycloak.api.model.ClientProfilesRepresentation;
import org.bremersee.keycloak.api.model.ClientRepresentation;
import org.bremersee.keycloak.api.model.ClientScopeRepresentation;
import org.bremersee.keycloak.api.model.ClientTypesRepresentation;
import org.bremersee.keycloak.api.model.ComponentRepresentation;
import org.bremersee.keycloak.api.model.ComponentTypeRepresentation;
import org.bremersee.keycloak.api.model.ConfigPropertyRepresentation;
import org.bremersee.keycloak.api.model.CredentialRepresentation;
import org.bremersee.keycloak.api.model.EventRepresentation;
import org.bremersee.keycloak.api.model.FederatedIdentityRepresentation;
import org.bremersee.keycloak.api.model.GlobalRequestResult;
import org.bremersee.keycloak.api.model.GroupRepresentation;
import org.bremersee.keycloak.api.model.IDToken;
import org.bremersee.keycloak.api.model.IdentityProviderMapperRepresentation;
import org.bremersee.keycloak.api.model.IdentityProviderMapperTypeRepresentation;
import org.bremersee.keycloak.api.model.IdentityProviderRepresentation;
import org.bremersee.keycloak.api.model.KeyStoreConfig;
import org.bremersee.keycloak.api.model.KeysMetadataRepresentation;
import org.bremersee.keycloak.api.model.ManagementPermissionReference;
import org.bremersee.keycloak.api.model.MappingsRepresentation;
import org.bremersee.keycloak.api.model.MemberRepresentation;
import org.bremersee.keycloak.api.model.OrganizationInvitationRepresentation;
import org.bremersee.keycloak.api.model.OrganizationRepresentation;
import org.bremersee.keycloak.api.model.PolicyEvaluationRequest;
import org.bremersee.keycloak.api.model.PolicyEvaluationResponse;
import org.bremersee.keycloak.api.model.PolicyProviderRepresentation;
import org.bremersee.keycloak.api.model.PolicyRepresentation;
import org.bremersee.keycloak.api.model.ProtocolMapperEvaluationRepresentation;
import org.bremersee.keycloak.api.model.ProtocolMapperRepresentation;
import org.bremersee.keycloak.api.model.RealmEventsConfigRepresentation;
import org.bremersee.keycloak.api.model.RealmRepresentation;
import org.bremersee.keycloak.api.model.RequiredActionConfigInfoRepresentation;
import org.bremersee.keycloak.api.model.RequiredActionConfigRepresentation;
import org.bremersee.keycloak.api.model.RequiredActionProviderRepresentation;
import org.bremersee.keycloak.api.model.ResourceRepresentation;
import org.bremersee.keycloak.api.model.ResourceServerRepresentation;
import org.bremersee.keycloak.api.model.RoleRepresentation;
import org.bremersee.keycloak.api.model.SamlExampleResponse;
import org.bremersee.keycloak.api.model.ScopeRepresentation;
import org.bremersee.keycloak.api.model.UPConfig;
import org.bremersee.keycloak.api.model.UserProfileMetadata;
import org.bremersee.keycloak.api.model.UserRepresentation;
import org.bremersee.keycloak.api.model.UserSessionRepresentation;
import org.bremersee.keycloak.api.model.WorkflowRepresentation;
import org.jspecify.annotations.Nullable;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The admin api mock.
 *
 * @author Christian Bremer
 */
@Validated
public class AdminApiMock implements AdminApi {

  /**
   * Instantiates a new admin api mock.
   */
  public AdminApiMock() {
    super();
  }

  @Override
  public Flux<RealmRepresentation> adminRealmsGet(@Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsPost(@Valid Mono<Resource> body) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAdminEventsDelete(String realm) {
    return Mono.empty();
  }

  @Override
  public Flux<AdminEventRepresentation> adminRealmsRealmAdminEventsGet(String realm,
      @Nullable @Valid String authClient, @Nullable @Valid String authIpAddress,
      @Nullable @Valid String authRealm, @Nullable @Valid String authUser,
      @Nullable @Valid String dateFrom, @Nullable @Valid String dateTo,
      @Nullable @Valid String direction, @Nullable @Valid Integer first,
      @Nullable @Valid Integer max, @Nullable @Valid List<String> operationTypes,
      @Nullable @Valid String resourcePath, @Nullable @Valid List<String> resourceTypes) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAttackDetectionBruteForceUsersDelete(String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAttackDetectionBruteForceUsersUserIdDelete(String realm,
      String userId) {
    return Mono.empty();
  }

  @Override
  public Mono<Map<String, Object>> adminRealmsRealmAttackDetectionBruteForceUsersUserIdGet(
      String realm, String userId) {
    return Mono.empty();
  }

  @Override
  public Flux<Map<String, Object>> adminRealmsRealmAuthenticationAuthenticatorProvidersGet(
      String realm) {
    return Flux.empty();
  }

  @Override
  public Flux<Map<String, Object>> adminRealmsRealmAuthenticationClientAuthenticatorProvidersGet(
      String realm) {
    return Flux.empty();
  }

  @Override
  public Mono<AuthenticatorConfigInfoRepresentation> adminRealmsRealmAuthenticationConfigDescriptionProviderIdGet(
      String realm, String providerId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationConfigIdDelete(String realm, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<AuthenticatorConfigRepresentation> adminRealmsRealmAuthenticationConfigIdGet(
      String realm, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationConfigIdPut(String realm, String id,
      @Valid Mono<AuthenticatorConfigRepresentation> authenticatorConfigRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationConfigPost(String realm,
      @Valid Mono<AuthenticatorConfigRepresentation> authenticatorConfigRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<AuthenticatorConfigRepresentation> adminRealmsRealmAuthenticationExecutionsExecutionIdConfigIdGet(
      String realm, String executionId, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationExecutionsExecutionIdConfigPost(String realm,
      String executionId,
      @Valid Mono<AuthenticatorConfigRepresentation> authenticatorConfigRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationExecutionsExecutionIdDelete(String realm,
      String executionId) {
    return Mono.empty();
  }

  @Override
  public Mono<AuthenticationExecutionRepresentation> adminRealmsRealmAuthenticationExecutionsExecutionIdGet(
      String realm, String executionId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationExecutionsExecutionIdLowerPriorityPost(
      String realm, String executionId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationExecutionsExecutionIdRaisePriorityPost(
      String realm, String executionId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationExecutionsPost(String realm,
      @Valid Mono<AuthenticationExecutionRepresentation> authenticationExecutionRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationFlowsFlowAliasCopyPost(String realm,
      String flowAlias, @Valid Mono<Map<String, String>> requestBody) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationFlowsFlowAliasExecutionsExecutionPost(
      String realm, String flowAlias, @Valid Mono<Map<String, Object>> requestBody) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationFlowsFlowAliasExecutionsFlowPost(String realm,
      String flowAlias, @Valid Mono<Map<String, Object>> requestBody) {
    return Mono.empty();
  }

  @Override
  public Flux<AuthenticationExecutionInfoRepresentation> adminRealmsRealmAuthenticationFlowsFlowAliasExecutionsGet(
      String realm, String flowAlias) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationFlowsFlowAliasExecutionsPut(String realm,
      String flowAlias,
      @Valid Mono<AuthenticationExecutionInfoRepresentation> authenticationExecutionInfoRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<AuthenticationFlowRepresentation> adminRealmsRealmAuthenticationFlowsGet(
      String realm) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationFlowsIdDelete(String realm, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<AuthenticationFlowRepresentation> adminRealmsRealmAuthenticationFlowsIdGet(
      String realm, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationFlowsIdPut(String realm, String id,
      @Valid Mono<AuthenticationFlowRepresentation> authenticationFlowRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationFlowsPost(String realm,
      @Valid Mono<AuthenticationFlowRepresentation> authenticationFlowRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<Map<String, Object>> adminRealmsRealmAuthenticationFormActionProvidersGet(
      String realm) {
    return Flux.empty();
  }

  @Override
  public Flux<Map<String, Object>> adminRealmsRealmAuthenticationFormProvidersGet(String realm) {
    return Flux.empty();
  }

  @Override
  public Mono<Map<String, List<ConfigPropertyRepresentation>>> adminRealmsRealmAuthenticationPerClientConfigDescriptionGet(
      String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationRegisterRequiredActionPost(String realm,
      @Valid Mono<Map<String, String>> requestBody) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationRequiredActionsAliasConfigDelete(String realm,
      String alias) {
    return Mono.empty();
  }

  @Override
  public Mono<RequiredActionConfigInfoRepresentation> adminRealmsRealmAuthenticationRequiredActionsAliasConfigDescriptionGet(
      String realm, String alias) {
    return Mono.empty();
  }

  @Override
  public Mono<RequiredActionConfigRepresentation> adminRealmsRealmAuthenticationRequiredActionsAliasConfigGet(
      String realm, String alias) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationRequiredActionsAliasConfigPut(String realm,
      String alias,
      @Valid Mono<RequiredActionConfigRepresentation> requiredActionConfigRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationRequiredActionsAliasDelete(String realm,
      String alias) {
    return Mono.empty();
  }

  @Override
  public Mono<RequiredActionProviderRepresentation> adminRealmsRealmAuthenticationRequiredActionsAliasGet(
      String realm, String alias) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationRequiredActionsAliasLowerPriorityPost(
      String realm, String alias) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationRequiredActionsAliasPut(String realm,
      String alias,
      @Valid Mono<RequiredActionProviderRepresentation> requiredActionProviderRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmAuthenticationRequiredActionsAliasRaisePriorityPost(
      String realm, String alias) {
    return Mono.empty();
  }

  @Override
  public Flux<RequiredActionProviderRepresentation> adminRealmsRealmAuthenticationRequiredActionsGet(
      String realm) {
    return Flux.empty();
  }

  @Override
  public Flux<Map<String, String>> adminRealmsRealmAuthenticationUnregisteredRequiredActionsGet(
      String realm) {
    return Flux.empty();
  }

  @Override
  public Mono<ClientRepresentation> adminRealmsRealmClientDescriptionConverterPost(String realm,
      @Valid Mono<String> body) {
    return Mono.empty();
  }

  @Override
  public Mono<ClientPoliciesRepresentation> adminRealmsRealmClientPoliciesPoliciesGet(String realm,
      @Nullable @Valid Boolean includeGlobalPolicies) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientPoliciesPoliciesPut(String realm,
      @Valid Mono<ClientPoliciesRepresentation> clientPoliciesRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<ClientProfilesRepresentation> adminRealmsRealmClientPoliciesProfilesGet(String realm,
      @Nullable @Valid Boolean includeGlobalProfiles) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientPoliciesProfilesPut(String realm,
      @Valid Mono<ClientProfilesRepresentation> clientProfilesRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ComponentTypeRepresentation> adminRealmsRealmClientRegistrationPolicyProvidersGet(
      String realm) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientScopesClientScopeIdDelete(String realm,
      String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Mono<ClientScopeRepresentation> adminRealmsRealmClientScopesClientScopeIdGet(String realm,
      String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientScopesClientScopeIdProtocolMappersAddModelsPost(
      String realm, String clientScopeId,
      @Valid Flux<ProtocolMapperRepresentation> protocolMapperRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ProtocolMapperRepresentation> adminRealmsRealmClientScopesClientScopeIdProtocolMappersModelsGet(
      String realm, String clientScopeId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientScopesClientScopeIdProtocolMappersModelsIdDelete(
      String realm, String clientScopeId, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<ProtocolMapperRepresentation> adminRealmsRealmClientScopesClientScopeIdProtocolMappersModelsIdGet(
      String realm, String clientScopeId, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientScopesClientScopeIdProtocolMappersModelsIdPut(
      String realm, String clientScopeId, String id,
      @Valid Mono<ProtocolMapperRepresentation> protocolMapperRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientScopesClientScopeIdProtocolMappersModelsPost(String realm,
      String clientScopeId,
      @Valid Mono<ProtocolMapperRepresentation> protocolMapperRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ProtocolMapperRepresentation> adminRealmsRealmClientScopesClientScopeIdProtocolMappersProtocolProtocolGet(
      String realm, String clientScopeId, String protocol) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientScopesClientScopeIdPut(String realm, String clientScopeId,
      @Valid Mono<ClientScopeRepresentation> clientScopeRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientScopesClientScopeIdScopeMappingsClientsClientAvailableGet(
      String realm, String clientScopeId, String client) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientScopesClientScopeIdScopeMappingsClientsClientCompositeGet(
      String realm, String clientScopeId, String client, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientScopesClientScopeIdScopeMappingsClientsClientDelete(
      String realm, String clientScopeId, String client,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientScopesClientScopeIdScopeMappingsClientsClientGet(
      String realm, String clientScopeId, String client) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientScopesClientScopeIdScopeMappingsClientsClientPost(
      String realm, String clientScopeId, String client,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<MappingsRepresentation> adminRealmsRealmClientScopesClientScopeIdScopeMappingsGet(
      String realm, String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientScopesClientScopeIdScopeMappingsRealmAvailableGet(
      String realm, String clientScopeId) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientScopesClientScopeIdScopeMappingsRealmCompositeGet(
      String realm, String clientScopeId, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientScopesClientScopeIdScopeMappingsRealmDelete(String realm,
      String clientScopeId, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientScopesClientScopeIdScopeMappingsRealmGet(
      String realm, String clientScopeId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientScopesClientScopeIdScopeMappingsRealmPost(String realm,
      String clientScopeId, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ClientScopeRepresentation> adminRealmsRealmClientScopesGet(String realm) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientScopesPost(String realm,
      @Valid Mono<ClientScopeRepresentation> clientScopeRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<Map<String, String>> adminRealmsRealmClientSessionStatsGet(String realm) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientTemplatesClientScopeIdDelete(String realm,
      String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Mono<ClientScopeRepresentation> adminRealmsRealmClientTemplatesClientScopeIdGet(
      String realm, String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientTemplatesClientScopeIdProtocolMappersAddModelsPost(
      String realm, String clientScopeId,
      @Valid Flux<ProtocolMapperRepresentation> protocolMapperRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ProtocolMapperRepresentation> adminRealmsRealmClientTemplatesClientScopeIdProtocolMappersModelsGet(
      String realm, String clientScopeId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientTemplatesClientScopeIdProtocolMappersModelsIdDelete(
      String realm, String clientScopeId, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<ProtocolMapperRepresentation> adminRealmsRealmClientTemplatesClientScopeIdProtocolMappersModelsIdGet(
      String realm, String clientScopeId, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientTemplatesClientScopeIdProtocolMappersModelsIdPut(
      String realm, String clientScopeId, String id,
      @Valid Mono<ProtocolMapperRepresentation> protocolMapperRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientTemplatesClientScopeIdProtocolMappersModelsPost(
      String realm, String clientScopeId,
      @Valid Mono<ProtocolMapperRepresentation> protocolMapperRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ProtocolMapperRepresentation> adminRealmsRealmClientTemplatesClientScopeIdProtocolMappersProtocolProtocolGet(
      String realm, String clientScopeId, String protocol) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientTemplatesClientScopeIdPut(String realm,
      String clientScopeId, @Valid Mono<ClientScopeRepresentation> clientScopeRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientTemplatesClientScopeIdScopeMappingsClientsClientAvailableGet(
      String realm, String clientScopeId, String client) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientTemplatesClientScopeIdScopeMappingsClientsClientCompositeGet(
      String realm, String clientScopeId, String client, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientTemplatesClientScopeIdScopeMappingsClientsClientDelete(
      String realm, String clientScopeId, String client,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientTemplatesClientScopeIdScopeMappingsClientsClientGet(
      String realm, String clientScopeId, String client) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientTemplatesClientScopeIdScopeMappingsClientsClientPost(
      String realm, String clientScopeId, String client,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<MappingsRepresentation> adminRealmsRealmClientTemplatesClientScopeIdScopeMappingsGet(
      String realm, String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientTemplatesClientScopeIdScopeMappingsRealmAvailableGet(
      String realm, String clientScopeId) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientTemplatesClientScopeIdScopeMappingsRealmCompositeGet(
      String realm, String clientScopeId, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientTemplatesClientScopeIdScopeMappingsRealmDelete(
      String realm, String clientScopeId, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientTemplatesClientScopeIdScopeMappingsRealmGet(
      String realm, String clientScopeId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientTemplatesClientScopeIdScopeMappingsRealmPost(String realm,
      String clientScopeId, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ClientScopeRepresentation> adminRealmsRealmClientTemplatesGet(String realm) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientTemplatesPost(String realm,
      @Valid Mono<ClientScopeRepresentation> clientScopeRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<ClientTypesRepresentation> adminRealmsRealmClientTypesGet(String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientTypesPut(String realm,
      @Valid Mono<ClientTypesRepresentation> clientTypesRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<ResourceServerRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerGet(
      String realm, String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidAuthzResourceServerImportPost(String realm,
      String clientUuid, @Valid Mono<ResourceServerRepresentation> resourceServerRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<PolicyEvaluationResponse> adminRealmsRealmClientsClientUuidAuthzResourceServerPermissionEvaluatePost(
      String realm, String clientUuid,
      @Valid Mono<PolicyEvaluationRequest> policyEvaluationRequest) {
    return Mono.empty();
  }

  @Override
  public Flux<AbstractPolicyRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerPermissionGet(
      String realm, String clientUuid, @Nullable @Valid String fields,
      @Nullable @Valid Integer first, @Nullable @Valid Integer max, @Nullable @Valid String name,
      @Nullable @Valid String owner, @Nullable @Valid Boolean permission,
      @Nullable @Valid String policyId, @Nullable @Valid String resource,
      @Nullable @Valid String resourceType, @Nullable @Valid String scope,
      @Nullable @Valid String type) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidAuthzResourceServerPermissionPost(String realm,
      String clientUuid, @Valid Mono<String> body) {
    return Mono.empty();
  }

  @Override
  public Flux<PolicyProviderRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerPermissionProvidersGet(
      String realm, String clientUuid) {
    return Flux.empty();
  }

  @Override
  public Mono<AbstractPolicyRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerPermissionSearchGet(
      String realm, String clientUuid, @Nullable @Valid String fields,
      @Nullable @Valid String name) {
    return Mono.empty();
  }

  @Override
  public Mono<PolicyEvaluationResponse> adminRealmsRealmClientsClientUuidAuthzResourceServerPolicyEvaluatePost(
      String realm, String clientUuid,
      @Valid Mono<PolicyEvaluationRequest> policyEvaluationRequest) {
    return Mono.empty();
  }

  @Override
  public Flux<AbstractPolicyRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerPolicyGet(
      String realm, String clientUuid, @Nullable @Valid String fields,
      @Nullable @Valid Integer first, @Nullable @Valid Integer max, @Nullable @Valid String name,
      @Nullable @Valid String owner, @Nullable @Valid Boolean permission,
      @Nullable @Valid String policyId, @Nullable @Valid String resource,
      @Nullable @Valid String resourceType, @Nullable @Valid String scope,
      @Nullable @Valid String type) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidAuthzResourceServerPolicyPost(String realm,
      String clientUuid, @Valid Mono<String> body) {
    return Mono.empty();
  }

  @Override
  public Flux<PolicyProviderRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerPolicyProvidersGet(
      String realm, String clientUuid) {
    return Flux.empty();
  }

  @Override
  public Mono<AbstractPolicyRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerPolicySearchGet(
      String realm, String clientUuid, @Nullable @Valid String fields,
      @Nullable @Valid String name) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidAuthzResourceServerPut(String realm,
      String clientUuid, @Valid Mono<ResourceServerRepresentation> resourceServerRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ResourceRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerResourceGet(
      String realm, String clientUuid, @Nullable @Valid String id, @Nullable @Valid Boolean deep,
      @Nullable @Valid Boolean exactName, @Nullable @Valid Integer first,
      @Nullable @Valid Boolean matchingUri, @Nullable @Valid Integer max,
      @Nullable @Valid String name, @Nullable @Valid String owner, @Nullable @Valid String scope,
      @Nullable @Valid String type, @Nullable @Valid String uri) {
    return Flux.empty();
  }

  @Override
  public Mono<ResourceRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerResourcePost(
      String realm, String clientUuid, @Nullable @Valid String id, @Nullable @Valid Boolean deep,
      @Nullable @Valid Boolean exactName, @Nullable @Valid Integer first,
      @Nullable @Valid Boolean matchingUri, @Nullable @Valid Integer max,
      @Nullable @Valid String name, @Nullable @Valid String owner, @Nullable @Valid String scope,
      @Nullable @Valid String type, @Nullable @Valid String uri,
      @Valid Mono<ResourceRepresentation> resourceRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidAuthzResourceServerResourceResourceIdAttributesGet(
      String realm, String clientUuid, String resourceId, @Nullable @Valid String id,
      @Nullable @Valid Boolean deep, @Nullable @Valid Boolean exactName,
      @Nullable @Valid Integer first, @Nullable @Valid Boolean matchingUri,
      @Nullable @Valid Integer max, @Nullable @Valid String name, @Nullable @Valid String owner,
      @Nullable @Valid String scope, @Nullable @Valid String type, @Nullable @Valid String uri) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidAuthzResourceServerResourceResourceIdDelete(
      String realm, String clientUuid, String resourceId, @Nullable @Valid String id,
      @Nullable @Valid Boolean deep, @Nullable @Valid Boolean exactName,
      @Nullable @Valid Integer first, @Nullable @Valid Boolean matchingUri,
      @Nullable @Valid Integer max, @Nullable @Valid String name, @Nullable @Valid String owner,
      @Nullable @Valid String scope, @Nullable @Valid String type, @Nullable @Valid String uri) {
    return Mono.empty();
  }

  @Override
  public Mono<ResourceRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerResourceResourceIdGet(
      String realm, String clientUuid, String resourceId, @Nullable @Valid String id,
      @Nullable @Valid Boolean deep, @Nullable @Valid Boolean exactName,
      @Nullable @Valid Integer first, @Nullable @Valid Boolean matchingUri,
      @Nullable @Valid Integer max, @Nullable @Valid String name, @Nullable @Valid String owner,
      @Nullable @Valid String scope, @Nullable @Valid String type, @Nullable @Valid String uri) {
    return Mono.empty();
  }

  @Override
  public Flux<PolicyRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerResourceResourceIdPermissionsGet(
      String realm, String clientUuid, String resourceId, @Nullable @Valid String id,
      @Nullable @Valid Boolean deep, @Nullable @Valid Boolean exactName,
      @Nullable @Valid Integer first, @Nullable @Valid Boolean matchingUri,
      @Nullable @Valid Integer max, @Nullable @Valid String name, @Nullable @Valid String owner,
      @Nullable @Valid String scope, @Nullable @Valid String type, @Nullable @Valid String uri) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidAuthzResourceServerResourceResourceIdPut(
      String realm, String clientUuid, String resourceId, @Nullable @Valid String id,
      @Nullable @Valid Boolean deep, @Nullable @Valid Boolean exactName,
      @Nullable @Valid Integer first, @Nullable @Valid Boolean matchingUri,
      @Nullable @Valid Integer max, @Nullable @Valid String name, @Nullable @Valid String owner,
      @Nullable @Valid String scope, @Nullable @Valid String type, @Nullable @Valid String uri,
      @Valid Mono<ResourceRepresentation> resourceRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ScopeRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerResourceResourceIdScopesGet(
      String realm, String clientUuid, String resourceId, @Nullable @Valid String id,
      @Nullable @Valid Boolean deep, @Nullable @Valid Boolean exactName,
      @Nullable @Valid Integer first, @Nullable @Valid Boolean matchingUri,
      @Nullable @Valid Integer max, @Nullable @Valid String name, @Nullable @Valid String owner,
      @Nullable @Valid String scope, @Nullable @Valid String type, @Nullable @Valid String uri) {
    return Flux.empty();
  }

  @Override
  public Mono<ResourceRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerResourceSearchGet(
      String realm, String clientUuid, @Nullable @Valid String id, @Nullable @Valid Boolean deep,
      @Nullable @Valid Boolean exactName, @Nullable @Valid Integer first,
      @Nullable @Valid Boolean matchingUri, @Nullable @Valid Integer max,
      @Nullable @Valid String owner, @Nullable @Valid String scope, @Nullable @Valid String type,
      @Nullable @Valid String uri, @Nullable @Valid String name) {
    return Mono.empty();
  }

  @Override
  public Flux<ScopeRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerScopeGet(
      String realm, String clientUuid, @Nullable @Valid Integer first, @Nullable @Valid Integer max,
      @Nullable @Valid String name, @Nullable @Valid String scopeId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidAuthzResourceServerScopePost(String realm,
      String clientUuid, @Valid Mono<ScopeRepresentation> scopeRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidAuthzResourceServerScopeScopeIdDelete(
      String realm, String clientUuid, String scopeId) {
    return Mono.empty();
  }

  @Override
  public Mono<ScopeRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerScopeScopeIdGet(
      String realm, String clientUuid, String scopeId) {
    return Mono.empty();
  }

  @Override
  public Flux<PolicyRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerScopeScopeIdPermissionsGet(
      String realm, String clientUuid, String scopeId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidAuthzResourceServerScopeScopeIdPut(
      String realm, String clientUuid, String scopeId,
      @Valid Mono<ScopeRepresentation> scopeRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ResourceRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerScopeScopeIdResourcesGet(
      String realm, String clientUuid, String scopeId) {
    return Flux.empty();
  }

  @Override
  public Flux<ScopeRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerScopeSearchGet(
      String realm, String clientUuid, @Nullable @Valid String name) {
    return Flux.empty();
  }

  @Override
  public Mono<ResourceServerRepresentation> adminRealmsRealmClientsClientUuidAuthzResourceServerSettingsGet(
      String realm, String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Mono<Resource> adminRealmsRealmClientsClientUuidCertificatesAttrDownloadPost(String realm,
      String clientUuid, String attr, @Valid Mono<KeyStoreConfig> keyStoreConfig) {
    return Mono.empty();
  }

  @Override
  public Mono<Resource> adminRealmsRealmClientsClientUuidCertificatesAttrGenerateAndDownloadPost(
      String realm, String clientUuid, String attr, @Valid Mono<KeyStoreConfig> keyStoreConfig) {
    return Mono.empty();
  }

  @Override
  public Mono<CertificateRepresentation> adminRealmsRealmClientsClientUuidCertificatesAttrGeneratePost(
      String realm, String clientUuid, String attr) {
    return Mono.empty();
  }

  @Override
  public Mono<CertificateRepresentation> adminRealmsRealmClientsClientUuidCertificatesAttrGet(
      String realm, String clientUuid, String attr) {
    return Mono.empty();
  }

  @Override
  public Mono<CertificateRepresentation> adminRealmsRealmClientsClientUuidCertificatesAttrUploadCertificatePost(
      String realm, String clientUuid, String attr) {
    return Mono.empty();
  }

  @Override
  public Mono<CertificateRepresentation> adminRealmsRealmClientsClientUuidCertificatesAttrUploadPost(
      String realm, String clientUuid, String attr) {
    return Mono.empty();
  }

  @Override
  public Mono<CredentialRepresentation> adminRealmsRealmClientsClientUuidClientSecretGet(
      String realm, String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Mono<CredentialRepresentation> adminRealmsRealmClientsClientUuidClientSecretPost(
      String realm, String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidClientSecretRotatedDelete(String realm,
      String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Mono<CredentialRepresentation> adminRealmsRealmClientsClientUuidClientSecretRotatedGet(
      String realm, String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidDefaultClientScopesClientScopeIdDelete(
      String realm, String clientUuid, String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidDefaultClientScopesClientScopeIdPut(
      String realm, String clientUuid, String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Flux<ClientScopeRepresentation> adminRealmsRealmClientsClientUuidDefaultClientScopesGet(
      String realm, String clientUuid) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidDelete(String realm, String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Mono<AccessToken> adminRealmsRealmClientsClientUuidEvaluateScopesGenerateExampleAccessTokenGet(
      String realm, String clientUuid, @Nullable @Valid String audience,
      @Nullable @Valid String scope, @Nullable @Valid String userId) {
    return Mono.empty();
  }

  @Override
  public Mono<IDToken> adminRealmsRealmClientsClientUuidEvaluateScopesGenerateExampleIdTokenGet(
      String realm, String clientUuid, @Nullable @Valid String audience,
      @Nullable @Valid String scope, @Nullable @Valid String userId) {
    return Mono.empty();
  }

  @Override
  public Mono<SamlExampleResponse> adminRealmsRealmClientsClientUuidEvaluateScopesGenerateExampleSamlResponseGet(
      String realm, String clientUuid, @Nullable @Valid String audience,
      @Nullable @Valid String scope, @Nullable @Valid String userId) {
    return Mono.empty();
  }

  @Override
  public Mono<Object> adminRealmsRealmClientsClientUuidEvaluateScopesGenerateExampleUserinfoGet(
      String realm, String clientUuid, @Nullable @Valid String scope,
      @Nullable @Valid String userId) {
    return Mono.empty();
  }

  @Override
  public Flux<ProtocolMapperEvaluationRepresentation> adminRealmsRealmClientsClientUuidEvaluateScopesProtocolMappersGet(
      String realm, String clientUuid, @Nullable @Valid String scope) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientsClientUuidEvaluateScopesScopeMappingsRoleContainerIdGrantedGet(
      String realm, String clientUuid, String roleContainerId, @Nullable @Valid String scope) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientsClientUuidEvaluateScopesScopeMappingsRoleContainerIdNotGrantedGet(
      String realm, String clientUuid, String roleContainerId, @Nullable @Valid String scope) {
    return Flux.empty();
  }

  @Override
  public Mono<ClientRepresentation> adminRealmsRealmClientsClientUuidGet(String realm,
      String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidInstallationProvidersProviderIdGet(
      String realm, String clientUuid, String providerId) {
    return Mono.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmClientsClientUuidManagementPermissionsGet(
      String realm, String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmClientsClientUuidManagementPermissionsPut(
      String realm, String clientUuid,
      @Valid Mono<ManagementPermissionReference> managementPermissionReference) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidNodesNodeDelete(String realm,
      String clientUuid, String node) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidNodesPost(String realm, String clientUuid,
      @Valid Mono<Map<String, String>> requestBody) {
    return Mono.empty();
  }

  @Override
  public Mono<Map<String, Long>> adminRealmsRealmClientsClientUuidOfflineSessionCountGet(
      String realm, String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Flux<UserSessionRepresentation> adminRealmsRealmClientsClientUuidOfflineSessionsGet(
      String realm, String clientUuid, @Nullable @Valid Integer first,
      @Nullable @Valid Integer max) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidOptionalClientScopesClientScopeIdDelete(
      String realm, String clientUuid, String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidOptionalClientScopesClientScopeIdPut(
      String realm, String clientUuid, String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Flux<ClientScopeRepresentation> adminRealmsRealmClientsClientUuidOptionalClientScopesGet(
      String realm, String clientUuid) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidProtocolMappersAddModelsPost(String realm,
      String clientUuid, @Valid Flux<ProtocolMapperRepresentation> protocolMapperRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ProtocolMapperRepresentation> adminRealmsRealmClientsClientUuidProtocolMappersModelsGet(
      String realm, String clientUuid) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidProtocolMappersModelsIdDelete(String realm,
      String clientUuid, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<ProtocolMapperRepresentation> adminRealmsRealmClientsClientUuidProtocolMappersModelsIdGet(
      String realm, String clientUuid, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidProtocolMappersModelsIdPut(String realm,
      String clientUuid, String id,
      @Valid Mono<ProtocolMapperRepresentation> protocolMapperRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidProtocolMappersModelsPost(String realm,
      String clientUuid, @Valid Mono<ProtocolMapperRepresentation> protocolMapperRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ProtocolMapperRepresentation> adminRealmsRealmClientsClientUuidProtocolMappersProtocolProtocolGet(
      String realm, String clientUuid, String protocol) {
    return Flux.empty();
  }

  @Override
  public Mono<GlobalRequestResult> adminRealmsRealmClientsClientUuidPushRevocationPost(String realm,
      String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidPut(String realm, String clientUuid,
      @Valid Mono<ClientRepresentation> clientRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<ClientRepresentation> adminRealmsRealmClientsClientUuidRegistrationAccessTokenPost(
      String realm, String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientsClientUuidRolesGet(String realm,
      String clientUuid, @Valid Boolean briefRepresentation, @Nullable @Valid Integer first,
      @Nullable @Valid Integer max, @Valid String search) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidRolesPost(String realm, String clientUuid,
      @Valid Mono<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientsClientUuidRolesRoleNameCompositesClientsTargetClientUuidGet(
      String realm, String clientUuid, String roleName, String targetClientUuid) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidRolesRoleNameCompositesDelete(String realm,
      String clientUuid, String roleName, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientsClientUuidRolesRoleNameCompositesGet(
      String realm, String clientUuid, String roleName) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidRolesRoleNameCompositesPost(String realm,
      String clientUuid, String roleName, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientsClientUuidRolesRoleNameCompositesRealmGet(
      String realm, String clientUuid, String roleName) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidRolesRoleNameDelete(String realm,
      String clientUuid, String roleName) {
    return Mono.empty();
  }

  @Override
  public Mono<RoleRepresentation> adminRealmsRealmClientsClientUuidRolesRoleNameGet(String realm,
      String clientUuid, String roleName) {
    return Mono.empty();
  }

  @Override
  public Flux<UserRepresentation> adminRealmsRealmClientsClientUuidRolesRoleNameGroupsGet(
      String realm, String clientUuid, String roleName, @Nullable @Valid Integer max,
      @Valid Boolean briefRepresentation, @Nullable @Valid Integer first) {
    return Flux.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmClientsClientUuidRolesRoleNameManagementPermissionsGet(
      String realm, String clientUuid, String roleName) {
    return Mono.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmClientsClientUuidRolesRoleNameManagementPermissionsPut(
      String realm, String clientUuid, String roleName,
      @Valid Mono<ManagementPermissionReference> managementPermissionReference) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidRolesRoleNamePut(String realm,
      String clientUuid, String roleName, @Valid Mono<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<UserRepresentation> adminRealmsRealmClientsClientUuidRolesRoleNameUsersGet(
      String realm, String clientUuid, String roleName,
      @Nullable @Valid Boolean briefRepresentation, @Nullable @Valid Integer first,
      @Nullable @Valid Integer max) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientsClientUuidScopeMappingsClientsClientAvailableGet(
      String realm, String clientUuid, String client) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientsClientUuidScopeMappingsClientsClientCompositeGet(
      String realm, String clientUuid, String client, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidScopeMappingsClientsClientDelete(String realm,
      String clientUuid, String client, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientsClientUuidScopeMappingsClientsClientGet(
      String realm, String clientUuid, String client) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidScopeMappingsClientsClientPost(String realm,
      String clientUuid, String client, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<MappingsRepresentation> adminRealmsRealmClientsClientUuidScopeMappingsGet(
      String realm, String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientsClientUuidScopeMappingsRealmAvailableGet(
      String realm, String clientUuid) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientsClientUuidScopeMappingsRealmCompositeGet(
      String realm, String clientUuid, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidScopeMappingsRealmDelete(String realm,
      String clientUuid, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmClientsClientUuidScopeMappingsRealmGet(
      String realm, String clientUuid) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsClientUuidScopeMappingsRealmPost(String realm,
      String clientUuid, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<UserRepresentation> adminRealmsRealmClientsClientUuidServiceAccountUserGet(
      String realm, String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Mono<Map<String, Long>> adminRealmsRealmClientsClientUuidSessionCountGet(String realm,
      String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Mono<GlobalRequestResult> adminRealmsRealmClientsClientUuidTestNodesAvailableGet(
      String realm, String clientUuid) {
    return Mono.empty();
  }

  @Override
  public Flux<UserSessionRepresentation> adminRealmsRealmClientsClientUuidUserSessionsGet(
      String realm, String clientUuid, @Nullable @Valid Integer first,
      @Nullable @Valid Integer max) {
    return Flux.empty();
  }

  @Override
  public Flux<ClientRepresentation> adminRealmsRealmClientsGet(String realm,
      @Nullable @Valid String clientId, @Nullable @Valid Integer first,
      @Nullable @Valid Integer max, @Nullable @Valid String q, @Valid Boolean search,
      @Valid Boolean viewableOnly) {
    return Flux.empty();
  }

  @Override
  public Flux<ClientInitialAccessPresentation> adminRealmsRealmClientsInitialAccessGet(
      String realm) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsInitialAccessIdDelete(String realm, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<ClientInitialAccessCreatePresentation> adminRealmsRealmClientsInitialAccessPost(
      String realm,
      @Valid Mono<ClientInitialAccessCreatePresentation> clientInitialAccessCreatePresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmClientsPost(String realm,
      @Valid Mono<ClientRepresentation> clientRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ComponentRepresentation> adminRealmsRealmComponentsGet(String realm,
      @Nullable @Valid String name, @Nullable @Valid String parent,
      @Nullable @Valid String providerId, @Nullable @Valid String type) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmComponentsIdDelete(String realm, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<ComponentRepresentation> adminRealmsRealmComponentsIdGet(String realm, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmComponentsIdPut(String realm, String id,
      @Valid Mono<ComponentRepresentation> componentRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<ComponentTypeRepresentation> adminRealmsRealmComponentsIdSubComponentTypesGet(
      String realm, String id, @Nullable @Valid String type) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmComponentsPost(String realm,
      @Valid Mono<ComponentRepresentation> componentRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<List<String>> adminRealmsRealmCredentialRegistratorsGet(String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmDefaultDefaultClientScopesClientScopeIdDelete(String realm,
      String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmDefaultDefaultClientScopesClientScopeIdPut(String realm,
      String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Flux<ClientScopeRepresentation> adminRealmsRealmDefaultDefaultClientScopesGet(
      String realm) {
    return Flux.empty();
  }

  @Override
  public Flux<GroupRepresentation> adminRealmsRealmDefaultGroupsGet(String realm) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmDefaultGroupsGroupIdDelete(String realm, String groupId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmDefaultGroupsGroupIdPut(String realm, String groupId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmDefaultOptionalClientScopesClientScopeIdDelete(String realm,
      String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmDefaultOptionalClientScopesClientScopeIdPut(String realm,
      String clientScopeId) {
    return Mono.empty();
  }

  @Override
  public Flux<ClientScopeRepresentation> adminRealmsRealmDefaultOptionalClientScopesGet(
      String realm) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmDelete(String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<RealmEventsConfigRepresentation> adminRealmsRealmEventsConfigGet(String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmEventsConfigPut(String realm,
      @Valid Mono<RealmEventsConfigRepresentation> realmEventsConfigRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmEventsDelete(String realm) {
    return Mono.empty();
  }

  @Override
  public Flux<EventRepresentation> adminRealmsRealmEventsGet(String realm,
      @Nullable @Valid String client, @Nullable @Valid String dateFrom,
      @Nullable @Valid String dateTo, @Nullable @Valid String direction,
      @Nullable @Valid Integer first, @Nullable @Valid String ipAddress,
      @Nullable @Valid Integer max, @Nullable @Valid List<String> type,
      @Nullable @Valid String user) {
    return Flux.empty();
  }

  @Override
  public Mono<RealmRepresentation> adminRealmsRealmGet(String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<GroupRepresentation> adminRealmsRealmGroupByPathPathGet(String realm,
      @Pattern(regexp = ".*") String path) {
    return Mono.empty();
  }

  @Override
  public Mono<Map<String, Long>> adminRealmsRealmGroupsCountGet(String realm,
      @Nullable @Valid String search, @Valid Boolean top) {
    return Mono.empty();
  }

  @Override
  public Flux<GroupRepresentation> adminRealmsRealmGroupsGet(String realm,
      @Valid Boolean briefRepresentation, @Valid Boolean exact, @Nullable @Valid Integer first,
      @Nullable @Valid Integer max, @Valid Boolean populateHierarchy, @Nullable @Valid String q,
      @Nullable @Valid String search, @Valid Boolean subGroupsCount) {
    return Flux.empty();
  }

  @Override
  public Flux<GroupRepresentation> adminRealmsRealmGroupsGroupIdChildrenGet(String realm,
      String groupId, @Valid Boolean briefRepresentation, @Nullable @Valid Boolean exact,
      @Nullable @Valid Integer first, @Nullable @Valid Integer max, @Nullable @Valid String search,
      @Valid Boolean subGroupsCount) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmGroupsGroupIdChildrenPost(String realm, String groupId,
      @Valid Mono<GroupRepresentation> groupRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmGroupsGroupIdDelete(String realm, String groupId) {
    return Mono.empty();
  }

  @Override
  public Mono<GroupRepresentation> adminRealmsRealmGroupsGroupIdGet(String realm, String groupId) {
    return Mono.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmGroupsGroupIdManagementPermissionsGet(
      String realm, String groupId) {
    return Mono.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmGroupsGroupIdManagementPermissionsPut(
      String realm, String groupId,
      @Valid Mono<ManagementPermissionReference> managementPermissionReference) {
    return Mono.empty();
  }

  @Override
  public Flux<UserRepresentation> adminRealmsRealmGroupsGroupIdMembersGet(String realm,
      String groupId, @Nullable @Valid Boolean briefRepresentation, @Nullable @Valid Integer first,
      @Nullable @Valid Integer max) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmGroupsGroupIdPut(String realm, String groupId,
      @Valid Mono<GroupRepresentation> groupRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmGroupsGroupIdRoleMappingsClientsClientIdAvailableGet(
      String realm, String groupId, String clientId) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmGroupsGroupIdRoleMappingsClientsClientIdCompositeGet(
      String realm, String groupId, String clientId, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmGroupsGroupIdRoleMappingsClientsClientIdDelete(String realm,
      String groupId, String clientId, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmGroupsGroupIdRoleMappingsClientsClientIdGet(
      String realm, String groupId, String clientId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmGroupsGroupIdRoleMappingsClientsClientIdPost(String realm,
      String groupId, String clientId, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<MappingsRepresentation> adminRealmsRealmGroupsGroupIdRoleMappingsGet(String realm,
      String groupId) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmGroupsGroupIdRoleMappingsRealmAvailableGet(
      String realm, String groupId) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmGroupsGroupIdRoleMappingsRealmCompositeGet(
      String realm, String groupId, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmGroupsGroupIdRoleMappingsRealmDelete(String realm,
      String groupId, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmGroupsGroupIdRoleMappingsRealmGet(String realm,
      String groupId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmGroupsGroupIdRoleMappingsRealmPost(String realm, String groupId,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmGroupsPost(String realm,
      @Valid Mono<GroupRepresentation> groupRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Map<String, String>> adminRealmsRealmIdentityProviderImportConfigPost(String realm,
      @Valid Mono<Map<String, Object>> requestBody) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmIdentityProviderInstancesAliasDelete(String realm,
      String alias) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmIdentityProviderInstancesAliasExportGet(String realm,
      String alias, @Nullable @Valid String format) {
    return Mono.empty();
  }

  @Override
  public Mono<IdentityProviderRepresentation> adminRealmsRealmIdentityProviderInstancesAliasGet(
      String realm, String alias) {
    return Mono.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmIdentityProviderInstancesAliasManagementPermissionsGet(
      String realm, String alias) {
    return Mono.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmIdentityProviderInstancesAliasManagementPermissionsPut(
      String realm, String alias,
      @Valid Mono<ManagementPermissionReference> managementPermissionReference) {
    return Mono.empty();
  }

  @Override
  public Mono<Map<String, IdentityProviderMapperTypeRepresentation>> adminRealmsRealmIdentityProviderInstancesAliasMapperTypesGet(
      String realm, String alias) {
    return Mono.empty();
  }

  @Override
  public Flux<IdentityProviderMapperRepresentation> adminRealmsRealmIdentityProviderInstancesAliasMappersGet(
      String realm, String alias) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmIdentityProviderInstancesAliasMappersIdDelete(String realm,
      String alias, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<IdentityProviderMapperRepresentation> adminRealmsRealmIdentityProviderInstancesAliasMappersIdGet(
      String realm, String alias, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmIdentityProviderInstancesAliasMappersIdPut(String realm,
      String alias, String id,
      @Valid Mono<IdentityProviderMapperRepresentation> identityProviderMapperRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmIdentityProviderInstancesAliasMappersPost(String realm,
      String alias,
      @Valid Mono<IdentityProviderMapperRepresentation> identityProviderMapperRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmIdentityProviderInstancesAliasPut(String realm, String alias,
      @Valid Mono<IdentityProviderRepresentation> identityProviderRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Boolean> adminRealmsRealmIdentityProviderInstancesAliasReloadKeysGet(String realm,
      String alias) {
    return Mono.empty();
  }

  @Override
  public Flux<IdentityProviderRepresentation> adminRealmsRealmIdentityProviderInstancesGet(
      String realm, @Nullable @Valid Boolean briefRepresentation,
      @Nullable @Valid String capability, @Nullable @Valid Integer first,
      @Nullable @Valid Integer max, @Nullable @Valid Boolean realmOnly,
      @Nullable @Valid String search, @Nullable @Valid String type) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmIdentityProviderInstancesPost(String realm,
      @Valid Mono<IdentityProviderRepresentation> identityProviderRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Object> adminRealmsRealmIdentityProviderProvidersProviderIdGet(String realm,
      String providerId) {
    return Mono.empty();
  }

  @Override
  public Mono<CertificateRepresentation> adminRealmsRealmIdentityProviderUploadCertificatePost(
      String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<KeysMetadataRepresentation> adminRealmsRealmKeysGet(String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<List<String>> adminRealmsRealmLocalizationGet(String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmLocalizationLocaleDelete(String realm, String locale) {
    return Mono.empty();
  }

  @Override
  public Mono<Map<String, String>> adminRealmsRealmLocalizationLocaleGet(String realm,
      String locale, @Nullable @Valid Boolean useRealmDefaultLocaleFallback) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmLocalizationLocaleKeyDelete(String realm, String key,
      String locale) {
    return Mono.empty();
  }

  @Override
  public Mono<String> adminRealmsRealmLocalizationLocaleKeyGet(String realm, String key,
      String locale) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmLocalizationLocaleKeyPut(String realm, String key,
      String locale, @Valid Mono<String> body) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmLocalizationLocalePost(String realm, String locale,
      @Valid Mono<Map<String, String>> requestBody) {
    return Mono.empty();
  }

  @Override
  public Mono<GlobalRequestResult> adminRealmsRealmLogoutAllPost(String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<Long> adminRealmsRealmOrganizationsCountGet(String realm,
      @Nullable @Valid Boolean exact, @Nullable @Valid String q, @Nullable @Valid String search) {
    return Mono.empty();
  }

  @Override
  public Flux<OrganizationRepresentation> adminRealmsRealmOrganizationsGet(String realm,
      @Valid Boolean briefRepresentation, @Nullable @Valid Boolean exact,
      @Nullable @Valid Integer first, @Nullable @Valid Integer max, @Nullable @Valid String q,
      @Nullable @Valid String search) {
    return Flux.empty();
  }

  @Override
  public Flux<OrganizationRepresentation> adminRealmsRealmOrganizationsMembersMemberIdOrganizationsGet(
      String realm, String memberId, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdDelete(String realm, String orgId) {
    return Mono.empty();
  }

  @Override
  public Mono<OrganizationRepresentation> adminRealmsRealmOrganizationsOrgIdGet(String realm,
      String orgId) {
    return Mono.empty();
  }

  @Override
  public Flux<GroupRepresentation> adminRealmsRealmOrganizationsOrgIdGroupsGet(String realm,
      String orgId, @Valid Boolean briefRepresentation, @Valid Boolean exact,
      @Nullable @Valid Integer first, @Nullable @Valid Integer max,
      @Valid Boolean populateHierarchy, @Nullable @Valid String q, @Nullable @Valid String search,
      @Valid Boolean subGroupsCount) {
    return Flux.empty();
  }

  @Override
  public Mono<GroupRepresentation> adminRealmsRealmOrganizationsOrgIdGroupsGroupByPathPathGet(
      String realm, String orgId, @Pattern(regexp = ".*") String path,
      @Valid Boolean subGroupsCount) {
    return Mono.empty();
  }

  @Override
  public Flux<GroupRepresentation> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdChildrenGet(
      String realm, String orgId, String groupId, @Nullable @Valid Boolean exact,
      @Nullable @Valid Integer first, @Nullable @Valid Integer max, @Nullable @Valid String search,
      @Nullable @Valid Boolean subGroupsCount) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdChildrenPost(String realm,
      String orgId, String groupId, @Valid Mono<GroupRepresentation> groupRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdDelete(String realm,
      String orgId, String groupId) {
    return Mono.empty();
  }

  @Override
  public Mono<GroupRepresentation> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdGet(String realm,
      String orgId, String groupId, @Valid Boolean subGroupsCount) {
    return Mono.empty();
  }

  @Override
  public Flux<MemberRepresentation> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdMembersGet(
      String realm, String orgId, String groupId, @Nullable @Valid Boolean briefRepresentation,
      @Nullable @Valid Integer first, @Nullable @Valid Integer max) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdMembersUserIdDelete(String realm,
      String orgId, String groupId, String userId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdMembersUserIdPut(String realm,
      String orgId, String groupId, String userId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdPut(String realm, String orgId,
      String groupId, @Valid Mono<GroupRepresentation> groupRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdRoleMappingsClientsClientIdAvailableGet(
      String realm, String orgId, String groupId, String clientId) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdRoleMappingsClientsClientIdCompositeGet(
      String realm, String orgId, String groupId, String clientId,
      @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdRoleMappingsClientsClientIdDelete(
      String realm, String orgId, String groupId, String clientId,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdRoleMappingsClientsClientIdGet(
      String realm, String orgId, String groupId, String clientId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdRoleMappingsClientsClientIdPost(
      String realm, String orgId, String groupId, String clientId,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<MappingsRepresentation> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdRoleMappingsGet(
      String realm, String orgId, String groupId) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdRoleMappingsRealmAvailableGet(
      String realm, String orgId, String groupId) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdRoleMappingsRealmCompositeGet(
      String realm, String orgId, String groupId, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdRoleMappingsRealmDelete(
      String realm, String orgId, String groupId,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdRoleMappingsRealmGet(
      String realm, String orgId, String groupId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdGroupsGroupIdRoleMappingsRealmPost(
      String realm, String orgId, String groupId,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdGroupsPost(String realm, String orgId,
      @Valid Mono<GroupRepresentation> groupRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdIdentityProvidersAliasDelete(String realm,
      String orgId, String alias) {
    return Mono.empty();
  }

  @Override
  public Mono<IdentityProviderRepresentation> adminRealmsRealmOrganizationsOrgIdIdentityProvidersAliasGet(
      String realm, String orgId, String alias) {
    return Mono.empty();
  }

  @Override
  public Flux<GroupRepresentation> adminRealmsRealmOrganizationsOrgIdIdentityProvidersAliasGroupsGet(
      String realm, String orgId, String alias, @Valid Boolean briefRepresentation,
      @Valid Boolean exact, @Nullable @Valid Integer first, @Nullable @Valid Integer max,
      @Nullable @Valid String q, @Nullable @Valid String search, @Valid Boolean subGroupsCount) {
    return Flux.empty();
  }

  @Override
  public Flux<IdentityProviderRepresentation> adminRealmsRealmOrganizationsOrgIdIdentityProvidersGet(
      String realm, String orgId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdIdentityProvidersPost(String realm,
      String orgId, @Valid Mono<String> body) {
    return Mono.empty();
  }

  @Override
  public Flux<OrganizationInvitationRepresentation> adminRealmsRealmOrganizationsOrgIdInvitationsGet(
      String realm, String orgId, @Nullable @Valid String email, @Nullable @Valid Integer first,
      @Nullable @Valid String firstName, @Nullable @Valid String lastName,
      @Nullable @Valid Integer max, @Nullable @Valid String search,
      @Nullable @Valid String status) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdInvitationsIdDelete(String realm,
      String orgId, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<OrganizationInvitationRepresentation> adminRealmsRealmOrganizationsOrgIdInvitationsIdGet(
      String realm, String orgId, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdInvitationsIdResendPost(String realm,
      String orgId, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<Long> adminRealmsRealmOrganizationsOrgIdMembersCountGet(String realm, String orgId) {
    return Mono.empty();
  }

  @Override
  public Flux<MemberRepresentation> adminRealmsRealmOrganizationsOrgIdMembersGet(String realm,
      String orgId, @Valid Boolean briefRepresentation, @Nullable @Valid Boolean exact,
      @Nullable @Valid Integer first, @Nullable @Valid Integer max,
      @Nullable @Valid String membershipType, @Nullable @Valid String search) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdMembersInviteExistingUserPost(String realm,
      String orgId, @Valid String id) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdMembersInviteUserPost(String realm,
      String orgId, @Valid String email, @Valid String firstName, @Valid String lastName) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdMembersMemberIdDelete(String realm,
      String orgId, String memberId) {
    return Mono.empty();
  }

  @Override
  public Mono<MemberRepresentation> adminRealmsRealmOrganizationsOrgIdMembersMemberIdGet(
      String realm, String orgId, String memberId) {
    return Mono.empty();
  }

  @Override
  public Flux<GroupRepresentation> adminRealmsRealmOrganizationsOrgIdMembersMemberIdGroupsGet(
      String realm, String orgId, String memberId, @Valid Boolean briefRepresentation,
      @Nullable @Valid Integer first, @Nullable @Valid Integer max,
      @Nullable @Valid String search) {
    return Flux.empty();
  }

  @Override
  public Flux<OrganizationRepresentation> adminRealmsRealmOrganizationsOrgIdMembersMemberIdOrganizationsGet(
      String realm, String orgId, String memberId, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdMembersPost(String realm, String orgId,
      @Valid Mono<String> body) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsOrgIdPut(String realm, String orgId,
      @Valid Mono<OrganizationRepresentation> organizationRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmOrganizationsPost(String realm,
      @Valid Mono<OrganizationRepresentation> organizationRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<RealmRepresentation> adminRealmsRealmPartialExportPost(String realm,
      @Nullable @Valid Boolean exportClients, @Nullable @Valid Boolean exportGroupsAndRoles) {
    return Mono.empty();
  }

  @Override
  public Mono<Object> adminRealmsRealmPartialImportPost(String realm, @Valid Mono<Resource> body) {
    return Mono.empty();
  }

  @Override
  public Mono<GlobalRequestResult> adminRealmsRealmPushRevocationPost(String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmPut(String realm,
      @Valid Mono<RealmRepresentation> realmRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmRolesByIdRoleIdCompositesClientsClientUuidGet(
      String realm, String clientUuid, String roleId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmRolesByIdRoleIdCompositesDelete(String realm, String roleId,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmRolesByIdRoleIdCompositesGet(String realm,
      String roleId, @Nullable @Valid Integer first, @Nullable @Valid Integer max,
      @Nullable @Valid String search) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmRolesByIdRoleIdCompositesPost(String realm, String roleId,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmRolesByIdRoleIdCompositesRealmGet(String realm,
      String roleId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmRolesByIdRoleIdDelete(String realm, String roleId) {
    return Mono.empty();
  }

  @Override
  public Mono<RoleRepresentation> adminRealmsRealmRolesByIdRoleIdGet(String realm, String roleId) {
    return Mono.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmRolesByIdRoleIdManagementPermissionsGet(
      String realm, String roleId) {
    return Mono.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmRolesByIdRoleIdManagementPermissionsPut(
      String realm, String roleId,
      @Valid Mono<ManagementPermissionReference> managementPermissionReference) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmRolesByIdRoleIdPut(String realm, String roleId,
      @Valid Mono<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmRolesGet(String realm,
      @Valid Boolean briefRepresentation, @Nullable @Valid Integer first,
      @Nullable @Valid Integer max, @Valid String search) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmRolesPost(String realm,
      @Valid Mono<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmRolesRoleNameCompositesClientsTargetClientUuidGet(
      String realm, String roleName, String targetClientUuid) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmRolesRoleNameCompositesDelete(String realm, String roleName,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmRolesRoleNameCompositesGet(String realm,
      String roleName) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmRolesRoleNameCompositesPost(String realm, String roleName,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmRolesRoleNameCompositesRealmGet(String realm,
      String roleName) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmRolesRoleNameDelete(String realm, String roleName) {
    return Mono.empty();
  }

  @Override
  public Mono<RoleRepresentation> adminRealmsRealmRolesRoleNameGet(String realm, String roleName) {
    return Mono.empty();
  }

  @Override
  public Flux<UserRepresentation> adminRealmsRealmRolesRoleNameGroupsGet(String realm,
      String roleName, @Nullable @Valid Integer max, @Valid Boolean briefRepresentation,
      @Nullable @Valid Integer first) {
    return Flux.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmRolesRoleNameManagementPermissionsGet(
      String realm, String roleName) {
    return Mono.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmRolesRoleNameManagementPermissionsPut(
      String realm, String roleName,
      @Valid Mono<ManagementPermissionReference> managementPermissionReference) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmRolesRoleNamePut(String realm, String roleName,
      @Valid Mono<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<UserRepresentation> adminRealmsRealmRolesRoleNameUsersGet(String realm,
      String roleName, @Nullable @Valid Boolean briefRepresentation, @Nullable @Valid Integer first,
      @Nullable @Valid Integer max) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmSessionsSessionDelete(String realm, String session,
      @Valid Boolean isOffline) {
    return Mono.empty();
  }

  @Override
  public Mono<Integer> adminRealmsRealmUsersCountGet(String realm,
      @Nullable @Valid String createdAfter, @Nullable @Valid String createdBefore,
      @Nullable @Valid String email, @Nullable @Valid Boolean emailVerified,
      @Nullable @Valid Boolean enabled, @Nullable @Valid Boolean exact,
      @Nullable @Valid String firstName, @Nullable @Valid String idpAlias,
      @Nullable @Valid String idpUserId, @Nullable @Valid String lastName,
      @Nullable @Valid String q, @Nullable @Valid String search, @Nullable @Valid String username) {
    return Mono.empty();
  }

  @Override
  public Flux<UserRepresentation> adminRealmsRealmUsersGet(String realm,
      @Nullable @Valid Boolean briefRepresentation, @Nullable @Valid String createdAfter,
      @Nullable @Valid String createdBefore, @Nullable @Valid String email,
      @Nullable @Valid Boolean emailVerified, @Nullable @Valid Boolean enabled,
      @Nullable @Valid Boolean exact, @Nullable @Valid Integer first,
      @Nullable @Valid String firstName, @Nullable @Valid String idpAlias,
      @Nullable @Valid String idpUserId, @Nullable @Valid String lastName,
      @Nullable @Valid Integer max, @Nullable @Valid String q, @Nullable @Valid String search,
      @Nullable @Valid String username) {
    return Flux.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmUsersManagementPermissionsGet(
      String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<ManagementPermissionReference> adminRealmsRealmUsersManagementPermissionsPut(
      String realm, @Valid Mono<ManagementPermissionReference> managementPermissionReference) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersPost(String realm,
      @Valid Mono<UserRepresentation> userRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<UPConfig> adminRealmsRealmUsersProfileGet(String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<UserProfileMetadata> adminRealmsRealmUsersProfileMetadataGet(String realm) {
    return Mono.empty();
  }

  @Override
  public Mono<UPConfig> adminRealmsRealmUsersProfilePut(String realm,
      @Valid Mono<UPConfig> upConfig) {
    return Mono.empty();
  }

  @Override
  public Mono<List<String>> adminRealmsRealmUsersUserIdConfiguredUserStorageCredentialTypesGet(
      String realm, String userId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdConsentsClientDelete(String realm, String userId,
      String client) {
    return Mono.empty();
  }

  @Override
  public Flux<Map<String, Object>> adminRealmsRealmUsersUserIdConsentsGet(String realm,
      String userId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdCredentialsCredentialIdDelete(String realm,
      String userId, String credentialId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdCredentialsCredentialIdMoveAfterNewPreviousCredentialIdPost(
      String realm, String userId, String credentialId, String newPreviousCredentialId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdCredentialsCredentialIdMoveToFirstPost(String realm,
      String userId, String credentialId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdCredentialsCredentialIdUserLabelPut(String realm,
      String userId, String credentialId, @Valid Mono<String> body) {
    return Mono.empty();
  }

  @Override
  public Flux<CredentialRepresentation> adminRealmsRealmUsersUserIdCredentialsGet(String realm,
      String userId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdDelete(String realm, String userId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdDisableCredentialTypesPut(String realm,
      String userId, @Valid Flux<String> requestBody) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdExecuteActionsEmailPut(String realm, String userId,
      @Nullable @Valid String clientId, @Nullable @Valid Integer lifespan,
      @Nullable @Valid String redirectUri, @Valid Flux<String> requestBody) {
    return Mono.empty();
  }

  @Override
  public Flux<FederatedIdentityRepresentation> adminRealmsRealmUsersUserIdFederatedIdentityGet(
      String realm, String userId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdFederatedIdentityProviderDelete(String realm,
      String userId, String provider) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdFederatedIdentityProviderPost(String realm,
      String userId, String provider,
      @Valid Mono<FederatedIdentityRepresentation> federatedIdentityRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<UserRepresentation> adminRealmsRealmUsersUserIdGet(String realm, String userId,
      @Nullable @Valid Boolean userProfileMetadata) {
    return Mono.empty();
  }

  @Override
  public Mono<Map<String, Long>> adminRealmsRealmUsersUserIdGroupsCountGet(String realm,
      String userId, @Nullable @Valid String search) {
    return Mono.empty();
  }

  @Override
  public Flux<GroupRepresentation> adminRealmsRealmUsersUserIdGroupsGet(String realm, String userId,
      @Valid Boolean briefRepresentation, @Nullable @Valid Integer first,
      @Nullable @Valid Integer max, @Nullable @Valid String search) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdGroupsGroupIdDelete(String realm, String userId,
      String groupId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdGroupsGroupIdPut(String realm, String userId,
      String groupId) {
    return Mono.empty();
  }

  @Override
  public Mono<Map<String, Object>> adminRealmsRealmUsersUserIdImpersonationPost(String realm,
      String userId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdLogoutPost(String realm, String userId) {
    return Mono.empty();
  }

  @Override
  public Flux<UserSessionRepresentation> adminRealmsRealmUsersUserIdOfflineSessionsClientUuidGet(
      String realm, String userId, String clientUuid) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdPut(String realm, String userId,
      @Valid Mono<UserRepresentation> userRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdResetPasswordEmailPut(String realm, String userId,
      @Nullable @Valid String clientId, @Nullable @Valid String redirectUri) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdResetPasswordPut(String realm, String userId,
      @Valid Mono<CredentialRepresentation> credentialRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmUsersUserIdRoleMappingsClientsClientIdAvailableGet(
      String realm, String userId, String clientId) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmUsersUserIdRoleMappingsClientsClientIdCompositeGet(
      String realm, String userId, String clientId, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdRoleMappingsClientsClientIdDelete(String realm,
      String userId, String clientId, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmUsersUserIdRoleMappingsClientsClientIdGet(
      String realm, String userId, String clientId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdRoleMappingsClientsClientIdPost(String realm,
      String userId, String clientId, @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<MappingsRepresentation> adminRealmsRealmUsersUserIdRoleMappingsGet(String realm,
      String userId) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmUsersUserIdRoleMappingsRealmAvailableGet(
      String realm, String userId) {
    return Flux.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmUsersUserIdRoleMappingsRealmCompositeGet(
      String realm, String userId, @Valid Boolean briefRepresentation) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdRoleMappingsRealmDelete(String realm, String userId,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Flux<RoleRepresentation> adminRealmsRealmUsersUserIdRoleMappingsRealmGet(String realm,
      String userId) {
    return Flux.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdRoleMappingsRealmPost(String realm, String userId,
      @Valid Flux<RoleRepresentation> roleRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmUsersUserIdSendVerifyEmailPut(String realm, String userId,
      @Nullable @Valid String clientId, @Nullable @Valid Integer lifespan,
      @Nullable @Valid String redirectUri) {
    return Mono.empty();
  }

  @Override
  public Flux<UserSessionRepresentation> adminRealmsRealmUsersUserIdSessionsGet(String realm,
      String userId) {
    return Flux.empty();
  }

  @Override
  public Mono<Map<String, List<String>>> adminRealmsRealmUsersUserIdUnmanagedAttributesGet(
      String realm, String userId) {
    return Mono.empty();
  }

  @Override
  public Mono<WorkflowRepresentation> adminRealmsRealmWorkflowsGet(String realm,
      @Nullable @Valid Boolean exact, @Nullable @Valid Integer first, @Nullable @Valid Integer max,
      @Nullable @Valid String search) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmWorkflowsIdActivateTypeResourceIdPost(String realm, String id,
      String resourceId, Object type, @Nullable @Valid String notBefore) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmWorkflowsIdDeactivateTypeResourceIdPost(String realm, String id,
      String resourceId, Object type) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmWorkflowsIdDelete(String realm, String id) {
    return Mono.empty();
  }

  @Override
  public Mono<WorkflowRepresentation> adminRealmsRealmWorkflowsIdGet(String realm, String id,
      @Nullable @Valid Boolean includeId) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmWorkflowsIdPut(String realm, String id,
      @Valid Mono<WorkflowRepresentation> workflowRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmWorkflowsMigratePost(String realm, @Nullable @Valid String from,
      @Nullable @Valid String to) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> adminRealmsRealmWorkflowsPost(String realm,
      @Valid Mono<WorkflowRepresentation> workflowRepresentation) {
    return Mono.empty();
  }

  @Override
  public Mono<WorkflowRepresentation> adminRealmsRealmWorkflowsScheduledResourceIdGet(String realm,
      String resourceId) {
    return Mono.empty();
  }
}

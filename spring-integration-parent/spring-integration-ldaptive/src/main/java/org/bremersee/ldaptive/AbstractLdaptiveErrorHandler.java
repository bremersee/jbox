/*
 * Copyright 2019 the original author or authors.
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

package org.bremersee.ldaptive;

import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ldaptive.LdapException;

/**
 * The abstract ldaptive error handler.
 *
 * @author Christian Bremer
 */
public abstract class AbstractLdaptiveErrorHandler implements LdaptiveErrorHandler {

  private static final Log log = LogFactory.getLog(AbstractLdaptiveErrorHandler.class);

  /**
   * Instantiates a new abstract ldaptive error handler.
   */
  protected AbstractLdaptiveErrorHandler() {
    super();
  }

  @Override
  public void handleError(Throwable t) {
    final LdaptiveException ldaptiveException;
    if (t instanceof LdaptiveException le) {
      ldaptiveException = le;
    } else if (t instanceof LdapException le) {
      ldaptiveException = map(le);
    } else {
      ldaptiveException = LdaptiveException.builder()
          .reason(Optional.ofNullable(t)
              .filter(Exception.class::isInstance)
              .map(Throwable::getMessage)
              .orElse("Unknown"))
          .cause(t)
          .build();
    }
    log.error("LDAP operation failed.", ldaptiveException);
    throw ldaptiveException;
  }

}

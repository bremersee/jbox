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

package org.bremersee.spring.security.test.context.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * The with normalized user annotation.
 *
 * @author Christian Bremer
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithNormalizedUserSecurityContextFactory.class)
public @interface WithNormalizedUser {

  /**
   * Name string.
   *
   * @return the string
   */
  String name() default "junit";

  /**
   * First name string.
   *
   * @return the string
   */
  String firstName() default "Test";

  /**
   * Last name string.
   *
   * @return the string
   */
  String lastName() default "User";

  /**
   * Email string.
   *
   * @return the string
   */
  String email() default "junit@localhost";

  /**
   * Password string.
   *
   * @return the string
   */
  String password() default "secret";

  /**
   * Authorities string [ ].
   *
   * @return the string [ ]
   */
  String[] authorities() default {};

  /**
   * Groups string [ ].
   *
   * @return the string [ ]
   */
  String[] groups() default {};

}

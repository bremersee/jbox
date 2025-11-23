/*
 * Copyright 2019-2022 the original author or authors.
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

package org.bremersee.kml.v22;

import static org.bremersee.xml.JaxbContextMember.byPackage;

import java.util.Collection;
import java.util.List;
import org.bremersee.xml.JaxbContextDataProvider;
import org.bremersee.xml.JaxbContextMember;

/**
 * KML jaxb context data provider.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("unused")
public class KmlJaxbContextDataProvider implements JaxbContextDataProvider {

  /**
   * The KML XML name space.
   */
  public static final String KML_NS = "http://www.opengis.net/kml/2.2";

  /**
   * The name space of google's kml extension.
   */
  public static final String KML_GX_NS = "http://www.google.com/kml/ext/2.2";

  /**
   * Name space of xAL: eXtensible Address Language
   */
  public static final String XAL_NS = "urn:oasis:names:tc:ciq:xsdschema:xAL:2.0";

  /**
   * Name space of w3.org Atom
   */
  public static final String ATOM_NS = "http://www.w3.org/2005/Atom";

  @Override
  public Collection<JaxbContextMember> getJaxbContextData() {
    return List.of(
        byPackage(org.bremersee.opengis.kml.v22.ObjectFactory.class.getPackage())
            .schemaLocation("https://bremersee.github.io/xmlschemas/kml/ogckml22.xsd")
            // original: .schemaLocation("http://schemas.opengis.net/kml/2.2.0/ogckml22.xsd")
            .build(),
        byPackage(org.bremersee.google.kml.v22.ext.ObjectFactory.class.getPackage())
            .schemaLocation("https://bremersee.github.io/xmlschemas/kml/kml22gx.xsd")
            // original: .schemaLocation("http://developers.google.com/kml/schema/kml22gx.xsd")
            .build(),
        byPackage(org.bremersee.oasis.xal.ObjectFactory.class.getPackage())
            .schemaLocation("https://bremersee.github.io/xmlschemas/kml/xAL.xsd")
            // original: .schemaLocation("http://docs.oasis-open.org/election/external/xAL.xsd")
            .build(),
        byPackage(org.bremersee.w3c.atom.ObjectFactory.class.getPackage())
            .schemaLocation("https://bremersee.github.io/xmlschemas/kml/atom-author-link.xsd")
            // original: .schemaLocation("http://schemas.opengis.net/kml/2.2.0/atom-author-link.xsd")
            .build()
    );
  }

}

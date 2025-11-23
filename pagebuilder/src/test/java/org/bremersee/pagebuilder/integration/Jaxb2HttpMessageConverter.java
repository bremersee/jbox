/*
 * Copyright 2020-2022 the original author or authors.
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

package org.bremersee.pagebuilder.integration;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.MarshalException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.PropertyException;
import jakarta.xml.bind.UnmarshalException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import org.bremersee.xml.JaxbContextBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * The jaxb 2 http message converter.
 *
 * @author Arjen Poutsma, Juergen Hoeller, Rossen Stoyanchev. Christian Bremer
 * @see org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter
 */
public class Jaxb2HttpMessageConverter extends AbstractXmlHttpMessageConverter<Object> {

  private final JaxbContextBuilder jaxbContextBuilder;

  /**
   * Instantiates a new Jaxb 2 http message converter.
   *
   * @param jaxbContextBuilder the jaxb context builder
   */
  public Jaxb2HttpMessageConverter(JaxbContextBuilder jaxbContextBuilder) {
    this.jaxbContextBuilder = jaxbContextBuilder;
  }

  @Override
  public boolean canRead(@NonNull Class<?> clazz, @Nullable MediaType mediaType) {
    return jaxbContextBuilder.canUnmarshal(clazz) && this.canRead(mediaType);
  }

  @Override
  public boolean canWrite(@NonNull Class<?> clazz, @Nullable MediaType mediaType) {
    return jaxbContextBuilder.canMarshal(clazz) && this.canWrite(mediaType);
  }

  protected boolean supports(@NonNull Class<?> clazz) {
    throw new UnsupportedOperationException();
  }

  @NonNull
  @Override
  protected Object readFromSource(@NonNull Class<?> clazz, @NonNull HttpHeaders headers,
      @NonNull Source source)
      throws Exception {
    try {
      Unmarshaller unmarshaller = jaxbContextBuilder.buildUnmarshaller(clazz);
      if (clazz.isAnnotationPresent(XmlRootElement.class)) {
        return unmarshaller.unmarshal(source);
      } else {
        JAXBElement<?> jaxbElement = unmarshaller.unmarshal(source, clazz);
        return jaxbElement.getValue();
      }
    } catch (UnmarshalException var7) {
      throw var7;
    } catch (JAXBException var8) {
      throw new HttpMessageConversionException("Invalid JAXB setup: " + var8.getMessage(), var8);
    }
  }

  protected void writeToResult(@NonNull Object o, HttpHeaders headers, @NonNull Result result)
      throws Exception {
    try {
      Marshaller marshaller = jaxbContextBuilder.buildMarshaller(o);
      this.setCharset(headers.getContentType(), marshaller);
      marshaller.marshal(o, result);
    } catch (MarshalException var6) {
      throw var6;
    } catch (JAXBException var7) {
      throw new HttpMessageConversionException("Invalid JAXB setup: " + var7.getMessage(), var7);
    }
  }

  private void setCharset(@Nullable MediaType contentType, Marshaller marshaller)
      throws PropertyException {
    if (contentType != null && contentType.getCharset() != null) {
      marshaller.setProperty("jaxb.encoding", contentType.getCharset().name());
    }
  }

}

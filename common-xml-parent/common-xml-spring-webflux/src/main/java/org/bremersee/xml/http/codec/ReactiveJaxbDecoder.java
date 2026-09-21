/*
* Copyright 2019-2026 the original author or authors.
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

package org.bremersee.xml.http.codec;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.UnmarshalException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.bremersee.xml.JaxbContextBuilder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.AbstractDecoder;
import org.springframework.core.codec.CodecException;
import org.springframework.core.codec.DecodingException;
import org.springframework.core.codec.Hints;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferLimitException;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.log.LogFormatUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.xml.XmlEventDecoder;
import org.springframework.http.codec.xml.XmlEventDecoder.ReceivedByteTracker;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.xml.StaxUtils;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

/**
 * Decode from a bytes stream containing XML elements to a stream of {@code Object}s (POJOs).
 *
 * <p>The decoding parts are taken from {@link org.springframework.http.codec.xml.Jaxb2XmlDecoder}.
 *
 * @author Sebastien Deleuze
 * @author Arjen Poutsma
 * @author Christian Bremer
 */
public class ReactiveJaxbDecoder extends AbstractDecoder<Object> {

  /**
   * The default value for JAXB annotations.
   *
   * @see XmlRootElement#name()
   * @see XmlRootElement#namespace()
   * @see XmlType#name()
   * @see XmlType#namespace()
   */
  private static final String JAXB_DEFAULT_ANNOTATION_VALUE = "##default";

  private static final XMLInputFactory inputFactory = StaxUtils.createDefensiveInputFactory();

  private final XmlEventDecoder xmlEventDecoder = new XmlEventDecoder();

  private final JaxbContextBuilder jaxbContextBuilder;

  private final Set<Class<?>> ignoreReadingClasses;

  private int maxInMemorySize = 256 * 1024;

  /**
   * Instantiates a new reactive jaxb decoder.
   *
   * @param jaxbContextBuilder the jaxb context builder
   */
  public ReactiveJaxbDecoder(JaxbContextBuilder jaxbContextBuilder) {
    this(jaxbContextBuilder, null);
  }

  /**
   * Instantiates a new reactive jaxb decoder.
   *
   * @param jaxbContextBuilder the jaxb context builder
   * @param ignoreReadingClasses the ignore reading classes
   */
  public ReactiveJaxbDecoder(
      JaxbContextBuilder jaxbContextBuilder,
      Set<Class<?>> ignoreReadingClasses) {

    super(MimeTypeUtils.APPLICATION_XML, MimeTypeUtils.TEXT_XML,
        new MediaType("application", "*+xml"));
    Assert.notNull(jaxbContextBuilder, "JaxbContextBuilder must be present.");
    this.jaxbContextBuilder = jaxbContextBuilder;
    this.ignoreReadingClasses = isNull(ignoreReadingClasses) ? Set.of() : ignoreReadingClasses;
  }

  /**
   * Set the max number of bytes that can be buffered by this decoder. This is either the size of
   * the entire input when decoding as a whole, or when using async parsing with Aalto XML, it is
   * the size of one top-level XML tree. When the limit is exceeded,
   * {@link org.springframework.core.io.buffer.DataBufferLimitException} is raised.
   *
   * <p>By default, this is set to 256K.
   *
   * @param byteCount the max number of bytes to buffer, or -1 for unlimited
   */
  public void setMaxInMemorySize(int byteCount) {
    this.maxInMemorySize = byteCount;
    this.xmlEventDecoder.setMaxInMemorySize(byteCount);
  }

  /**
   * Return the {@link #setMaxInMemorySize configured} byte count limit.
   *
   * @return the max in memory size
   */
  public int getMaxInMemorySize() {
    return this.maxInMemorySize;
  }

  @Override
  public boolean canDecode(
      @NonNull ResolvableType elementType,
      @Nullable MimeType mimeType) {

    if (super.canDecode(elementType, mimeType)) {
      final Class<?> outputClass = elementType.getRawClass();
      return !ignoreReadingClasses.contains(outputClass)
          && jaxbContextBuilder.canUnmarshal(outputClass);
    } else {
      return false;
    }
  }

  @NonNull
  @Override
  public Flux<Object> decode(
      @NonNull Publisher<DataBuffer> inputStream,
      ResolvableType elementType,
      @Nullable MimeType mimeType,
      @Nullable Map<String, Object> hints) {

    ReceivedByteTracker byteTracker = new ReceivedByteTracker(this.maxInMemorySize);

    Flux<XMLEvent> xmlEventFlux = this.xmlEventDecoder.decode(
        inputStream, ResolvableType.forClass(XMLEvent.class), mimeType, hints);

    Class<?> outputClass = elementType.toClass();
    Set<QName> names = toQualifiedNames(outputClass);
    Flux<List<XMLEvent>> splitEvents = split(xmlEventFlux, names, byteTracker);

    return splitEvents.map(events -> {
      Object value = unmarshal(events, outputClass);
      LogFormatUtils.traceDebug(logger, traceOn -> {
        String formatted = LogFormatUtils.formatValue(value, !traceOn);
        return Hints.getLogPrefix(hints) + "Decoded [" + formatted + "]";
      });
      return value;
    });
  }

  @NonNull
  @Override
  @SuppressWarnings({"rawtypes", "unchecked", "cast"})
  // XMLEventReader is Iterator<Object> on JDK 9
  public Object decode(
      DataBuffer dataBuffer,
      ResolvableType targetType,
      @Nullable MimeType mimeType,
      @Nullable Map<String, Object> hints) throws DecodingException {

    try {
      Iterator eventReader = inputFactory.createXMLEventReader(dataBuffer.asInputStream());
      List<XMLEvent> events = new ArrayList<>();
      eventReader.forEachRemaining(event -> events.add((XMLEvent) event));
      return unmarshal(events, targetType.toClass());
    } catch (XMLStreamException ex) {
      throw Exceptions.propagate(ex);
    } finally {
      DataBufferUtils.release(dataBuffer);
    }
  }

  @NonNull
  @Override
  public Mono<Object> decodeToMono(
      @NonNull Publisher<DataBuffer> input,
      @NonNull ResolvableType elementType,
      @Nullable MimeType mimeType,
      @Nullable Map<String, Object> hints) {

    return DataBufferUtils.join(input, this.maxInMemorySize)
        .map(dataBuffer -> decode(dataBuffer, elementType, mimeType, hints));
  }

  private Object unmarshal(List<XMLEvent> events, Class<?> outputClass) {
    try {
      Unmarshaller unmarshaller = jaxbContextBuilder.buildUnmarshaller(outputClass);
      XMLEventReader eventReader = StaxUtils.createXMLEventReader(events);
      if (outputClass.isAnnotationPresent(XmlRootElement.class)) {
        return unmarshaller.unmarshal(eventReader);
      } else {
        JAXBElement<?> jaxbElement = unmarshaller.unmarshal(eventReader, outputClass);
        return jaxbElement.getValue();
      }
    } catch (UnmarshalException ex) {
      throw new DecodingException("Could not unmarshal XML to " + outputClass, ex);
    } catch (JAXBException ex) {
      throw new CodecException("Invalid JAXB configuration", ex);
    }
  }

  /**
   * Split a flux of {@link XMLEvent XMLEvents} into a flux of XMLEvent lists, one list for each
   * branch of the tree that starts with the given qualified name. That is, given the XMLEvents
   * shown {@linkplain XmlEventDecoder here}, and the {@code desiredName} "{@code child}", this
   * method returns a flux of two lists, each of which containing the events of a particular branch
   * of the tree that starts with "{@code child}".
   * <ol>
   * <li>The first list, dealing with the first branch of the tree:
   * <ol>
   * <li>{@link javax.xml.stream.events.StartElement} {@code child}</li>
   * <li>{@link javax.xml.stream.events.Characters} {@code foo}</li>
   * <li>{@link javax.xml.stream.events.EndElement} {@code child}</li>
   * </ol>
   * <li>The second list, dealing with the second branch of the tree:
   * <ol>
   * <li>{@link javax.xml.stream.events.StartElement} {@code child}</li>
   * <li>{@link javax.xml.stream.events.Characters} {@code bar}</li>
   * <li>{@link javax.xml.stream.events.EndElement} {@code child}</li>
   * </ol>
   * </li>
   * </ol>
   *
   * @param xmlEventFlux the xml event as flux
   * @param desiredNames the desired names
   * @param byteTracker the byte tracker
   * @return the list of xml events as flux
   */
  private static Flux<List<XMLEvent>> split(
      Flux<XMLEvent> xmlEventFlux,
      Set<QName> desiredNames,
      XmlEventDecoder.ReceivedByteTracker byteTracker) {
    return xmlEventFlux.handle(new SplitHandler(desiredNames, byteTracker));
  }

  private static Set<QName> toQualifiedNames(Class<?> outputClass) {
    Set<QName> result = HashSet.newHashSet(1);
    findQNames(outputClass, result, new HashSet<>());
    return result;
  }

  private static void findQNames(Class<?> clazz, Set<QName> qNames, Set<Class<?>> completedClasses) {
    // safety against circular XmlSeeAlso references
    if (completedClasses.contains(clazz)) {
      return;
    }
    if (clazz.isAnnotationPresent(XmlRootElement.class)) {
      XmlRootElement annotation = clazz.getAnnotation(XmlRootElement.class);
      qNames.add(new QName(namespace(annotation.namespace(), clazz),
          localPart(annotation.name(), clazz)));
    }
    else if (clazz.isAnnotationPresent(XmlType.class)) {
      XmlType annotation = clazz.getAnnotation(XmlType.class);
      qNames.add(new QName(namespace(annotation.namespace(), clazz),
          localPart(annotation.name(), clazz)));
    }
    else {
      throw new IllegalArgumentException("Output class [" + clazz.getName() +
          "] is neither annotated with @XmlRootElement nor @XmlType");
    }
    completedClasses.add(clazz);
    if (clazz.isAnnotationPresent(XmlSeeAlso.class)) {
      XmlSeeAlso annotation = clazz.getAnnotation(XmlSeeAlso.class);
      for (Class<?> seeAlso : annotation.value()) {
        findQNames(seeAlso, qNames, completedClasses);
      }
    }
  }

  private static String localPart(String value, Class<?> outputClass) {
    if (JAXB_DEFAULT_ANNOTATION_VALUE.equals(value)) {
      return ClassUtils.getShortNameAsProperty(outputClass);
    }
    else {
      return value;
    }
  }

  private static String namespace(String value, Class<?> outputClass) {
    if (JAXB_DEFAULT_ANNOTATION_VALUE.equals(value)) {
      Package outputClassPackage = outputClass.getPackage();
      if (nonNull(outputClassPackage) && outputClassPackage.isAnnotationPresent(XmlSchema.class)) {
        XmlSchema annotation = outputClassPackage.getAnnotation(XmlSchema.class);
        return annotation.namespace();
      }
      else {
        return XMLConstants.NULL_NS_URI;
      }
    }
    else {
      return value;
    }
  }

  private static class SplitHandler implements
      BiConsumer<XMLEvent, SynchronousSink<List<XMLEvent>>> {

    private final Set<QName> names;

    private final ReceivedByteTracker byteTracker;

    private List<XMLEvent> events;

    private int elementDepth = 0;

    private int barrier = Integer.MAX_VALUE;

    /**
     * Instantiates a new split handler.
     *
     * @param names the names
     * @param byteTracker the byte tracker
     */
    public SplitHandler(Set<QName> names, ReceivedByteTracker byteTracker) {
      this.names = names;
      this.byteTracker = Optional.ofNullable(byteTracker).orElse(ReceivedByteTracker.NO_OP);
    }

    @Override
    public void accept(XMLEvent event, SynchronousSink<List<XMLEvent>> sink) {
      if (event.isStartElement()) {
        if (this.barrier == Integer.MAX_VALUE) {
          QName startElementName = event.asStartElement().getName();
          if (this.names.contains(startElementName)) {
            this.events = new ArrayList<>();
            this.barrier = this.elementDepth;
          }
        }
        this.elementDepth++;
      }
      if (this.elementDepth > this.barrier) {
        Assert.state(this.events != null, "No XMLEvent List");
        this.events.add(event);
      }
      if (event.isEndElement()) {
        this.elementDepth--;
        if (this.elementDepth == this.barrier) {
          Assert.state(this.events != null, "No XMLEvent List");
          sink.next(this.events);
          this.barrier = Integer.MAX_VALUE;
          this.events = null;
        }
      }
      if (isNull(this.events)) {
        this.byteTracker.reset();
      } else if (this.byteTracker.isMaxInMemorySizeExceeded()) {
        throw new DataBufferLimitException(
            "Exceeded limit on max bytes per XML node: " + this.byteTracker.getMaxInMemorySize());
      }
    }
  }

}

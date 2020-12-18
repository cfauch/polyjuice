/*
 * Copyright 2020 Claire Fauch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * <p>
 * This package provides factories to build parameters and custom object from YAML files.
 * </p>
 * <h3>Build a single Parameter</h3>
 * Here is an example of how to build one parameter from YAML file.
 * <p>
 * The YAML file:
 * <pre>
 * !!com.code.fauch.polyjuice.mapping.ParameterFactory
 * name: msgSize
 * type: INT
 * value: 13
 * </pre>
 * The first line indicates which factory to use: <code>!!com.code.fauch.polyjuice.mapping.ParameterFactory</code>
 * </p>
 * <p>
 * Use the following instructions to load the YAML file and get a build Parameter.
 * <pre>
 *      try (InputStream in = getClass().getResourceAsStream("/simple-parameter-template.yml")) {
 *          final ParameterFactory payload = new Yaml().load(in);
 *          final Parameter<?> param = payload.build(Parameter.class);
 *          Assert.assertEquals(13, param.getValue());
 *      }
 * </pre>
 * </p>
 * <h3>Build a custom object</h3>
 * Here is a complete example of how you can build a custom object from YAML file.
 * <p>
 * Your custom object looks like this:
 * <pre>
 * public class SimpleContent extends AbsContent {
 *  
 *  private Parameter&lt;Integer&gt; msgSize;
 *  private Parameter&lt;String&gt; msg;
 *
 *  public Parameter&lt;Integer&gt; getMsgSize() {
 *      return msgSize;
 *  }
 *
 *  public void setMsgSize(Parameter&lt;Integer&gt; msgSize) {
 *      this.msgSize = msgSize;
 *  }
 *
 *  public Parameter&lt;String&gt; getMsg() {
 *      return msg;
 *  }
 *
 *  public void setMsg(Parameter&lt;String&gt; msg) {
 *      this.msg = msg;
 *  }
 *  
 * }
 * </pre>
 * Notice that tour object should extend the <code>AbsContent</code> class. 
 * It is also necessary to define getters and setters on each Parameter.
 * </p>
 * <p>
 * The corresponding YAML file:
 * <pre>
 * !!com.code.fauch.polyjuice.mapping.ObjectFactory
 * contents:
 * -  !!com.code.fauch.polyjuice.mapping.ParameterFactory
 *    name: msgSize
 *    type: INT
 *    value: 13
 * 
 * -  !!com.code.fauch.polyjuice.mapping.ParameterFactory
 *    name: msg
 *    type: STRING
 *    value: HELLO WORLD !
 * </pre>
 * The first line is the name of the factory to use to build custom object:
 * <code>!!com.code.fauch.polyjuice.mapping.ObjectFactory</code>.
 * Under <code>content</code> section, define each parameters of your custom object.
 * <ul>
 *  <li>The first one <code>msgSize</code> will be mapped to <code>private Parameter&lt;Integer&gt; msgSize</code></li>
 *  <li>The second one <code>msg</code> will be mapped to <code>private Parameter&lt;String&gt; msg</code></li>
 * </ul>
 * </p>
 * <h3>Build a composite custom object</h3>
 * It is possible to compose custom object with other custom object.
 * <p>
 * In this example <code>CompositeContent</code> define a composition with our <code>SimpleContent</code>:
 * <pre>
 * public class CompositeContent extends AbsContent {
 *  private Parameter<OffsetClock> clock;
 *  private Parameter<Integer> size;
 *  private SimpleContent subContent;
 *
 *  public Parameter<OffsetClock> getClock() {
 *      return clock;
 *  }
 *
 *  public void setClock(Parameter<OffsetClock> clock) {
 *      this.clock = clock;
 *  }
 *
 *  public Parameter<Integer> getSize() {
 *      return size;
 *  }
 *
 *  public void setSize(Parameter<Integer> size) {
 *      this.size = size;
 *  }
 *
 *  public SimpleContent getSubContent() {
 *      return subContent;
 *  }
 *
 *  public void setSubContent(SimpleContent subContent) {
 *      this.subContent = subContent;
 *  }    
 * }
 * </pre>
 * Next, here is how the YAML file looks like:
 * <pre>
 * !!com.code.fauch.polyjuice.mapping.ObjectFactory
 * contents:
 * -  !!com.code.fauch.polyjuice.mapping.ParameterFactory
 *    name: clock
 *    type: OFFSET_CLOCK
 *    value: PT-12H
 * 
 * -  !!com.code.fauch.polyjuice.mapping.ParameterFactory
 *    name: size
 *    type: INT
 *    
 * -  !!com.code.fauch.polyjuice.mapping.ObjectFactory
 *    name: subContent
 *    contents:
 *    -  !!com.code.fauch.polyjuice.mapping.ParameterFactory
 *       name: msgSize
 *       type: INT
 *       value: 13
 *    
 *    -  !!com.code.fauch.polyjuice.mapping.ParameterFactory
 *       name: msg
 *       type: STRING
 *       value: HELLO WORLD !
 * </pre>
 * Now, you can load it, like this:
 * <pre>
 *      try (InputStream in = getClass().getResourceAsStream("/composite-content-template.yml")) {
 *          final ObjectFactory factory = new Yaml().load(in);
 *          final CompositeContent composite = factory.build(CompositeContent.class);
 *          final SimpleContent simple = composite.getSubContent();
 *          Assert.assertEquals("HELLO WORLD !", simple.getMsg().getValue());
 *      }
 * </pre>
 * </p>
 */
package com.code.fauch.polyjuice.mapping;


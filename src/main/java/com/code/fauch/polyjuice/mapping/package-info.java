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
 * This package provides factories to build sequence of parameters.
 * Provided factories can be used to build sequence of parameters from YAML files.
 * </p>
 * <h3>Example of YAML file</h3>
 * <pre>
 * parameters:
 * -  &magical_number
 *    name: magicalNumber
 *    type: INT
 *    value: 42
 *    readonly: true
 *   
 *  -  name: msgSize
 *     type: INT
 *    value: 13
 *   
 * -  name: msg
 *    type: STRING
 *    value: HELLO WORLD !
 *   
 * -  name: clock
 *    type: OFFSET_CLOCK
 *    value: PT-12H
 *
 * -  *magical_number
 * </pre>
 * <h3>Example of code to read this file </h3>
 * <pre>
 *      try (InputStream in = getClass().getResourceAsStream("/truc-template.yml")) {
 *          final ObjectFactory payload = new Yaml(new Constructor(ObjectFactory.class)).load(in);
 *          final Truc truc = payload.build(Truc.class);
 *          truc.getMagicalNumber().getValue();
 *          truc.getMsg().setValue("MERCY");
 *      }
 * </pre>
 * <p>
 * The factory <code>ObjectFactory</code> is provided by this package. You just have to create the bean <code>truc</code> and then
 * call the getter to retrieve a parameter in order to obtain its value or set a new value. 
 * </p>
 * <h3>Example of expected bean </h3
 * <p> Your bean should implements <code>IObject</code>. It may also extends <code>AbsContent</code> to benefit from management
 * of value change listeners and predefined encoding of parameters. Your bean should have getter/setter for each expected parameters 
 * in the YAML file.
 * </p>
 * <pre>
 * public final class Truc extends AbsContent implements IObject {
 * 
 *     private Parameter&lt;Integer&gt; magicalNumber;
 *     
 *     private Parameter&lt;String&gt; msg;
 *      
 *     public Parameter&lt;Integer&gt; getMagicalNumber() {
 *         return magicalNumber;
 *     }
 * 
 *     public void setMagicalNumber(Parameter&lt;Integer&gt; magicalNumber) {
 *         this.magicalNumber = magicalNumber;
 *     }
 * 
 *     public Parameter&lt;String&gt; getMsg() {
 *         return msg;
 *     }
 * 
 *     public void setMsg(Parameter&lt;String&gt; msg) {
 *         this.msg = msg;
 *     }
 * 
 *     public void addOrderedParameters(final List&lt;Parameter&lt;?&gt;&gt; orderedParameters) {
 *         getOrderedParameters().addAll(orderedParameters);
 *     }
 *     
 * }
 * </pre>
 * 
 * @author c.fauch
 *
 */
package com.code.fauch.polyjuice.mapping;

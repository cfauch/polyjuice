/*
 * Copyright 2019 Claire Fauch
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
 * This package provides objects (<code>Parameter</code> and <code>Sequence</code>) to easily encode a parameter 
 * or a sequence of parameters.
 * </p>
 * <h3>Parameter encoding</h3>
 * <p>
 * Here is an example to encode a parameter by specifying its type.
 * </p>
 * <pre>
 * final byte[] buff = Parameter.newConstant("p", StdType.INT, 42).getBytes();
 * </pre>
 * <p>
 * The predefined types are present in <code>StdType</code>. It is however possible to define new ones using the Service Provider Interface.
 * </p>
 * <h3>Encoding of a sequence of parameters</h3>
 * <p>
 * You may also specify or not an expected size in bytes of the result. If a size is specified the encoding result is truncated or padded with
 * zeros if necessary. Here after is an example without size (<code>null</code>).
 * </p> 
 * <pre>
 *      final Parameter<Integer> p1 = Parameter.newConstant("p1", StdType.INT, 42);
 *      final Parameter<Integer> p2 = Parameter.newConstant("p2", StdType.INT, 421);
 *      final byte[] buff = new Sequence(null, Arrays.asList(p1, p2)).getBytes();
 * </pre>
 * 
 * @author c.fauch
 *
 */
package com.code.fauch.polyjuice;

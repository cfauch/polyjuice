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
 * This package provides objects to easily encode a parameter or a sequence of parameters.
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
 * <h3>Dynamic array</h3>
 * <p>
 * Here is how you encode an array of parameters that can grow up over the time.
 * </p> 
 * <pre>
 *      final DynamicArray&lt;Parameter&lt;?&gt;&gt; array = new DynamicArray&lt;&gt;();
 *      array.add(Parameter.newParameter("size", StdType.SHORT, (short)5, false));
 *      array.add(Parameter.newParameter("msg", StdType.STRING, "Hello", false));
 *      array.getBytes();
 * </pre>
 * <h3> Custom object encoding</h3>
 * <p>
 * It is possible to encode custom objects. All you have to do is to extend the 
 * <code>AbsContent</code> class.
 * </p>
 * <p>
 * Here is an example of such custom object: 
 * </p>
 * <pre>
 *  public final class ContentImpl extends AbsContent {
 *      public static final class Item extends AbsContent {
 *          
 *          private final Parameter&lt;Short&gt; size;
 *          private final Parameter&lt;String&gt; msg;
 *
 *          private Item() {
 *              this.size = Parameter.newParameter("size", StdType.SHORT, null, false);
 *              this.msg = Parameter.newParameter("msg", StdType.STRING, null, false);
 *              this.addOrderedContents(Arrays.asList(this.size, this.msg));
 *          }
 *          
 *          public Item message(final String msg) {
 *              this.msg.setValue(msg);
 *              this.size.setValue((short)msg.length());
 *              return this;
 *          }
 *      }
 *      
 *      private final Parameter&lt;Short&gt; nb;
 *      private final DynamicArray&lt;Item&gt; items; 
 *      
 *      public ContentImpl() {
 *          this.nb = Parameter.newParameter("nb", StdType.SHORT, (short)0, false);
 *          this.items = new DynamicArray&lt;&gt;();
 *          this.addOrderedContents(Arrays.asList(this.nb, this.items));
 *      }
 *      
 *      public Item newItem() {
 *          final Item item = new Item();
 *          this.items.add(item);
 *          this.nb.setValue((short) (this.nb.getValue() + 1));
 *          return item;
 *      }
 *  }
 *
 * </pre>
 * <p>
 * Now, you can encode it, like this:
 * </p>
 * <pre>
 *      final ContentImpl content = new ContentImpl();
 *      content.newItem().message("red");
 *      content.newItem().message("green");
 *      content.newItem().message("blue");
 *      content.getBytes();
 * </pre>
 * @author c.fauch
 *
 */
package com.code.fauch.polyjuice;


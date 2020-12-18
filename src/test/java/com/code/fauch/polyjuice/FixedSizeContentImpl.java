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
package com.code.fauch.polyjuice;

import java.util.Arrays;

/**
 * @author c.fauch
 *
 */
public class FixedSizeContentImpl extends AbsFixedSizeContent {

    /** 
     * Size in bytes of the message.
     */
    private final Parameter<Short> size;
    
    /**
     * The message.
     */
    private final Parameter<String> msg;
    
    /**
     * Set a new message value. The size is automatically updated.
     * @param msg the new message
     * @return this item
     */
    public FixedSizeContentImpl message(final String msg) {
        this.msg.setValue(msg);
        this.size.setValue((short)msg.length());
        return this;
    }
    
    /**
     * Construct a new content without values.
     */
    public FixedSizeContentImpl() {
        this.size = Parameter.newParameter("size", StdType.SHORT, (short)2, false);
        this.msg = Parameter.newParameter("msg", StdType.STRING, "no", false);
        addOrderedContents(Arrays.asList(this.size, this.msg));
    }
}

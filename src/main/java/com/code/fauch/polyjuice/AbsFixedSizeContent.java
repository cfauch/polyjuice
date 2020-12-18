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
import java.util.List;

import com.code.fauch.polyjuice.mapping.ISizedObject;

/**
 * Partial implementation of abstract content with the capability of specified a fixed size in bytes
 * of the result.
 * 
 * @author c.fauch
 *
 */
public abstract class AbsFixedSizeContent extends AbsContent implements ISizedObject {

    /**
     * The expected size of the encoded sequence (in bytes).
     * Null means auto-adjust
     */
    private Integer expectedSize;
    
    /**
     * Constructor
     * 
     * @param ordered content
     * @param expected size (in bytes)
     */
    public AbsFixedSizeContent(final List<IContent> contents, final Integer expectedSize) {
        super(contents);
        this.expectedSize = expectedSize;
    }
    
    /**
     * Constructor
     */
    public AbsFixedSizeContent() {
        super();
    }

    /**
     * Adapt the result if necessary to match the expected size if it is defined.
     */
    @Override
    public final byte[] getBytes() {
        return getExpectedSize() == null ? super.getBytes() : Arrays.copyOf(super.getBytes(), getExpectedSize());
    }

    /**
     * Returns the expected size of the encoded content.
     * If not null the result is truncated and padded with zeros to match the expected size.
     * 
     * @return null by default.
     */
    public final Integer getExpectedSize() {
        return this.expectedSize;
    }

    /**
     * The expected size in bytes of the encoded result
     * 
     * @param size the expected size by be null.
     */
    @Override
    public final void setExpectedSize(final Integer size) {
        this.expectedSize = size;
    }
}

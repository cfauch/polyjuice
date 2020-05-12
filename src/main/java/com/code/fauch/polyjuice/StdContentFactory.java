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

import java.util.Objects;

import com.code.fauch.polyjuice.spi.IContentFactoryProvider;

/**
 * Standard content factory provider.
 * 
 * @author c.fauch
 *
 */
public final class StdContentFactory implements IContentFactoryProvider {

    /**
     * A single parameter.
     */
    public static final IContentFactory PARAMETER = new IContentFactory() {

        @Override
        public String getName() {
            return "PARAMETER";
        }

        @Override
        public IContent newContent(final ContentBuilder payload) {
            //TODO check
            return Objects.requireNonNull(payload.getParameter(), "missing parameter");
        }
        
    };
    
    /**
     * A Sequence of parameters.
     */
    public static final IContentFactory SEQUENCE = new IContentFactory() {
        
        @Override
        public String getName() {
            return "SEQUENCE";
        }

        @Override
        public IContent newContent(final ContentBuilder payload) {
            return new Sequence(payload.getSize(), Objects.requireNonNull(payload.getItems(), "missing items"));
        }
        
    };
    
    @Override
    public IContentFactory getInstance(String name) {
        if (name == null) {
            return null;
        }
        if (name.equals(PARAMETER.getName())) {
            return PARAMETER;
        }
        if (name.equals(SEQUENCE.getName())) {
            return SEQUENCE;
        }
        return null;
    }
    
}

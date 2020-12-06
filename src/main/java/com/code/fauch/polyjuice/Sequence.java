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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Definition of a sequence of item with a fixed sized result.
 * It is possible for client code to subscribe on changes on all parameters.
 * 
 * @author c.fauch
 *
 */
public class Sequence extends AbsFixedSizeContent {
    
    /**
     * Constructor.
     * 
     * @param size the expected size in bytes of the encoded content (may be null).
     * @param parameters the ordered list of parameters (list to copy, not null may be empty)
     */
    public Sequence(final Integer size, final List<Parameter<?>> parameters) {
        super(Collections.unmodifiableList(new ArrayList<>(parameters)), size);
    }
        
    @SuppressWarnings("unchecked")
    public <U> Parameter<U> getParameter(String name) {
        for (Parameter<?> p: getOrderedParameters()) {
            if (p != null) {
                if (p.getLabel().equals(name)) {
                    return (Parameter<U>) p;
                }
            }
        }
        return null;
    }
    
}

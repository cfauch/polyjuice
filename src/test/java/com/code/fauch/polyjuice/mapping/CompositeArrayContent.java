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
package com.code.fauch.polyjuice.mapping;

import com.code.fauch.polyjuice.AbsContent;
import com.code.fauch.polyjuice.DynamicArray;
import com.code.fauch.polyjuice.Parameter;

/**
 * @author c.fauch
 *
 */
public class CompositeArrayContent extends AbsContent {

    /**
     * Total number of items
     */
    private Parameter<Short> nb;
    
    /**
     * List of items
     */
    private DynamicArray<SimpleContent> elts;

    /**
     * @return the nb
     */
    public Parameter<Short> getNb() {
        return nb;
    }

    /**
     * @param nb the nb to set
     */
    public void setNb(Parameter<Short> nb) {
        this.nb = nb;
    }

    /**
     * @return the elts
     */
    public DynamicArray<SimpleContent> getElts() {
        return elts;
    }

    /**
     * @param elts the elts to set
     */
    public void setElts(DynamicArray<SimpleContent> elts) {
        this.elts = elts;
    }
    
}

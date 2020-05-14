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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.code.fauch.polyjuice.Parameter;
import com.code.fauch.polyjuice.Sequence;

/**
 * Factory used to build instance of Sequence.
 * 
 * @author c.fauch
 *
 */
public final class SequenceFactory {
    
    /**
     * The ordered list of parameters.
     */
    private List<ParameterFactory> parameters;
    
    /**
     * The expected size of the encoded sequence.
     */
    private Integer size;

    /**
     * Returns the ordered list of parameters.
     * 
     * @return the parameters
     */
    public List<ParameterFactory> getParameters() {
        return parameters;
    }

    /**
     * Specify the ordered list of parameters.
     * 
     * @param parameters the parameters to set
     */
    public void setParameters(List<ParameterFactory> parameters) {
        this.parameters = parameters;
    }
    
    /**
     * Returns the expected size of encoded sequence.
     * 
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Specify the expected size of the encoded sequence.
     * 
     * @param size the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Build the sequence.
     * 
     * @return the corresponding sequence.
     */
    public Sequence build() {
        final ArrayList<Parameter<?>> order = new ArrayList<>(Objects.requireNonNull(this.parameters, "missing 'parameters'").size());
        for (final ParameterFactory pf : this.parameters) {
            order.add(pf.build());
        }
        return new Sequence(this.size, order);
    }
    
}

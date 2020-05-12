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
package com.code.fauch.polyjuice.dto;

import java.util.List;

import com.code.fauch.polyjuice.IContent;
import com.code.fauch.polyjuice.ContentBuilder;
import com.code.fauch.polyjuice.spi.Providers;

/**
 * Describes a payload in YAML format.
 * 
 * @author c.fauch
 *
 */
public final class DTOPayload {

    /**
     * type
     */
    private String type;

    /**
     * optional size
     */
    private Integer size;

    /**
     * List of parameters.
     */
    private List<DTOParameter> parameters;
    
    /**
     * One parameter.
     */
    private DTOParameter parameter;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * @return the parameters
     */
    public List<DTOParameter> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(List<DTOParameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * @return the parameter
     */
    public DTOParameter getParameter() {
        return parameter;
    }

    /**
     * @param parameter the parameter to set
     */
    public void setParameter(DTOParameter parameter) {
        this.parameter = parameter;
    }

    public IContent build() {
        final ContentBuilder payload = new ContentBuilder(Providers.getContentTypeInstance(this.type)).size(this.size);
        if (this.parameters != null) {
            payload.list(this.parameters.size());
            for (DTOParameter p : this.parameters) {
                payload.addItem(p.build());
            }
        }
        if (this.parameter != null) {
            payload.parameter(this.parameter.build());
        }
        return payload.build();
    }
}

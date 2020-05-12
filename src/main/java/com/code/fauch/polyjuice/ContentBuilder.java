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
import java.util.List;
import java.util.Objects;

/**
 * Definition of a content builder.
 * A builder that help factories to create content.
 * 
 * @author c.fauch
 *
 */
public final class ContentBuilder {

    /**
     * The factory to use to create the content (not null)
     */
    private final IContentFactory factory;
    
    /**
     * The optional parameter.
     */
    private Parameter<?> parameter;
    
    /**
     * The optional size
     */
    private Integer size;
    
    /**
     * The optional list of items.
     */
    private List<IContent> items;

    /**
     * Constructor.
     * 
     * @param factory the content factory to use (not null)
     */
    public ContentBuilder(final IContentFactory factory) {
        this.factory = Objects.requireNonNull(factory, "factory is missing");
    }
    
    /**
     * Specify a parameter.
     * 
     * @param parameter the parameter to set
     * @return this builder
     */
    public ContentBuilder parameter(final Parameter<?> parameter) {
        this.parameter = parameter;
        return this;
    }
    
    /**
     * Specify a size
     * 
     * @param size the size
     * @return this builder
     */
    public ContentBuilder size(final Integer size) {
        this.size = size;
        return this;
    }

    /**
     * Add an item
     * 
     * @param item the item to add
     * @return this builder
     */
    public ContentBuilder addItem(final IContent item) {
        if (this.items == null) {
            this.items = new ArrayList<IContent>();
        }
        this.items.add(item);
        return this;
    }

    /**
     * returns the optional parameter
     * @return the parameter
     */
    public Parameter<?> getParameter() {
        return this.parameter;
    }

    /**
     * Returns the optional size
     * @return the size
     */
    public Integer getSize() {
        return this.size;
    }

    /**
     * Returns the optional items
     * @return the list of items
     */
    public List<IContent> getItems() {
        return this.items;
    }
    
    /**
     * Build e new content.
     * @return the corresponding content
     */
    public IContent build() {
        return this.factory.newContent(this);
    }

    /**
     * @param size2
     */
    public ContentBuilder list(int size) {
        this.items = new ArrayList<IContent>(size);
        return this;
    }

}

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

import java.lang.reflect.Type;

import com.code.fauch.polyjuice.IContent;

/**
 * Interface describing each content factory to use to build content
 * from yaml.
 * 
 * @author c.fauch
 *
 */
public interface IContentFactory <T extends IContent> {

    /**
     * The mapping name.
     * Used to map the content with a field of the returned object.
     * 
     * @return the mapping field name
     */
    String getName();

    /**
     * Build a new instance of an object
     * 
     * @param clss the class of the object to build.
     * @return the new instance where all field a mapped by content name
     */
    public <U extends T> U build(Class<U> clss, Type... genericTypes) throws Exception;

}

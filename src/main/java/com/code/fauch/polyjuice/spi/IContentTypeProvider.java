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
package com.code.fauch.polyjuice.spi;

import com.code.fauch.polyjuice.IContent;
import com.code.fauch.polyjuice.IContentType;

/**
 * Describes a provider of content-types.
 * It is used to search content-type from text thanks to SPI.
 * 
 * @author c.fauch
 *
 */
public interface IContentTypeProvider {

    /**
     * Research the content-type corresponding to the given name
     * 
     * @param name the name of the content-type to research
     * @return the corresponding content-type or null if not found.
     */
    IContentType<? extends IContent> getInstance(String name);
    
}

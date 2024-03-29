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
import com.code.fauch.polyjuice.OffsetClock;
import com.code.fauch.polyjuice.Parameter;

/**
 * @author c.fauch
 *
 */
public class CompositeContent extends AbsContent {
    
    private Parameter<OffsetClock> clock;
    
    private Parameter<Integer> size;
    
    private SimpleContent subContent;

    /**
     * @return the clock
     */
    public Parameter<OffsetClock> getClock() {
        return clock;
    }

    /**
     * @param clock the clock to set
     */
    public void setClock(Parameter<OffsetClock> clock) {
        this.clock = clock;
    }

    /**
     * @return the size
     */
    public Parameter<Integer> getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Parameter<Integer> size) {
        this.size = size;
    }

    /**
     * @return the subContent
     */
    public SimpleContent getSubContent() {
        return subContent;
    }

    /**
     * @param subContent the subContent to set
     */
    public void setSubContent(SimpleContent subContent) {
        this.subContent = subContent;
    }
    
}

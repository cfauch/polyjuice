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

/**
 * @author c.fauch
 *
 */
public final class Frame {
    
    private String dst;
    
    private Integer rate;
    
    private ObjectFactory payload;

    /**
     * @return the dst
     */
    public String getDst() {
        return dst;
    }

    /**
     * @param dst the dst to set
     */
    public void setDst(String dst) {
        this.dst = dst;
    }

    /**
     * @return the rate
     */
    public Integer getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(Integer rate) {
        this.rate = rate;
    }

    /**
     * @return the payload
     */
    public ObjectFactory getPayload() {
        return payload;
    }

    /**
     * @param payload the payload to set
     */
    public void setPayload(ObjectFactory payload) {
        this.payload = payload;
    }

}

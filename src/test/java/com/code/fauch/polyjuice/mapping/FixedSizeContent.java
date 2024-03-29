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

import com.code.fauch.polyjuice.AbsFixedSizeContent;
import com.code.fauch.polyjuice.Parameter;

/**
 * @author c.fauch
 *
 */
public class FixedSizeContent extends AbsFixedSizeContent {
    
    private Parameter<Integer> msgSize;
    
    private Parameter<String> msg;
 
    /**
     * @return the msgSize
     */
    public Parameter<Integer> getMsgSize() {
        return msgSize;
    }

    /**
     * @param msgSize the msgSize to set
     */
    public void setMsgSize(Parameter<Integer> msgSize) {
        this.msgSize = msgSize;
    }

    /**
     * @return the msg
     */
    public Parameter<String> getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(Parameter<String> msg) {
        this.msg = msg;
    }
    
}

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
import java.util.Collections;
import java.util.List;

import com.code.fauch.polyjuice.AbsContent;
import com.code.fauch.polyjuice.OffsetClock;
import com.code.fauch.polyjuice.Parameter;

/**
 * @author c.fauch
 *
 */
public final class ImmuableTruc extends AbsContent {

    private final Parameter<Integer> magicalNumber;
    
    private final Parameter<Integer> msgSize;
    
    private final Parameter<String> msg;
    
    private final Parameter<OffsetClock> clock;

    public static final class Builder extends AbsContent implements IObject {
        
        private Parameter<Integer> magicalNumber;
        
        private Parameter<Integer> msgSize;
        
        private Parameter<String> msg;
        
        private Parameter<OffsetClock> clock;
        
        /**
         * @return the magicalNumber
         */
        public Parameter<Integer> getMagicalNumber() {
            return magicalNumber;
        }

        /**
         * @param magicalNumber the magicalNumber to set
         */
        public void setMagicalNumber(Parameter<Integer> magicalNumber) {
            this.magicalNumber = magicalNumber;
        }

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
         * @param orderedParameters the orderedParameters to set
         */
        public void addOrderedParameters(final List<Parameter<?>> orderedParameters) {
            getOrderedParameters().addAll(orderedParameters);
        }
        
        public ImmuableTruc build() {
            return new ImmuableTruc(this);
        }
    }
    
    private ImmuableTruc(final Builder builder) {
        super(Collections.unmodifiableList(new ArrayList<>(builder.getOrderedParameters())));
        this.clock = builder.clock;
        this.magicalNumber = builder.magicalNumber;
        this.msg = builder.msg;
        this.msgSize = builder.msgSize;
    }

    /**
     * @return the magicalNumber
     */
    public Parameter<Integer> getMagicalNumber() {
        return magicalNumber;
    }

    /**
     * @return the msgSize
     */
    public Parameter<Integer> getMsgSize() {
        return msgSize;
    }

    /**
     * @return the msg
     */
    public Parameter<String> getMsg() {
        return msg;
    }

    /**
     * @return the clock
     */
    public Parameter<OffsetClock> getClock() {
        return clock;
    }

}

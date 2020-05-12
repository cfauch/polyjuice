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

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;

import com.code.fauch.polyjuice.FixedClock;
import com.code.fauch.polyjuice.IType;
import com.code.fauch.polyjuice.OffsetClock;
import com.code.fauch.polyjuice.Parameter;
import com.code.fauch.polyjuice.spi.Providers;

/**
 * @author c.fauch
 *
 */
public class DTOParameter {

    /**
     * name
     */
    private String name;

    /**
     * type
     */
    private String type;
    
    /**
     * Optional value
     */
    private Object value;

    /**
     * Indicates whether this parameter is read only or not.
     */
    private boolean readonly;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

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
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the readonly
     */
    public boolean isReadonly() {
        return readonly;
    }

    /**
     * @param readonly the readonly to set
     */
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
   
    /**
     * Creates and returns the corresponding payload parameter.
     * @return the corresponding payload parameter
     */
    public Parameter<?> build() {
        return from(
                this.name, 
                Objects.requireNonNull(Providers.getTypeInstance(this.type), "unknown type: " + this.type), 
                this.value,
                this.readonly
        );
    }
    
    /**
     * Builds and returns parameter with the given type and value
     * @param <U>
     * @param label the label of the parameter
     * @param type the expected type
     * @param value the value
     * @param readOnly true for a constant
     * @return the just built parameter
     */
    private static <U> Parameter<U> from(
            final String label, final IType<U> type, final Object value, final boolean readOnly) {
        return Parameter.newParameter(
                label,
                type,
                convert(value, type.getValueClass()),
                readOnly
        );
    }
    
    /**
     * Convert a value.
     * 
     * @param value the value to convert
     * @param type the expected class
     * @return the corresponding value
     */
    public static <U> U convert(final Object value, final Class<U> type) {
        if (value == null) {
            return null;
        }
        if (type.isEnum()) {
            @SuppressWarnings("unchecked")
            Object obj = Enum.valueOf(type.asSubclass(Enum.class), (String)value);
            return type.cast(obj);
        }
        if (LocalDate.class.isAssignableFrom(type)) {
            return type.cast(LocalDate.parse((String)value));
        }
        if (LocalTime.class.isAssignableFrom(type)) {
            return type.cast(LocalTime.parse((String)value));
        }
        if (OffsetClock.class.isAssignableFrom(type)) {
            return type.cast(new OffsetClock(Duration.parse((String) value)));
        }
        if (FixedClock.class.isAssignableFrom(type)) {
            return type.cast(new FixedClock(ZoneId.systemDefault(), Instant.parse((String) value)));
        }
        return type.cast(value);
    }
    
}

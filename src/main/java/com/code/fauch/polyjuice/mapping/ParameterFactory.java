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
 * Factory used to build new instance of Parameter.
 * 
 * @author c.fauch
 *
 */
public final class ParameterFactory implements IContentFactory<Parameter<?>> {
    
    /**
     * name of the parameter
     */
    private String name;

    /**
     * type of the parameter
     */
    private String type;
    
    /**
     * value of the value
     */
    private Object value;

    /**
     * Indicates whether this parameter is read only or not.
     */
    private boolean readonly;

    /**
     * Returns the name of the parameter
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Specify the name of the parameter
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the type of the parameter.
     * 
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Specify the type of the parameter.
     * 
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the value of the parameter.
     * 
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Specify the value of the parameter.
     * 
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Returns true if the parameter is read only.
     * @return the readonly
     */
    public boolean isReadonly() {
        return readonly;
    }

    /**
     * Specify whether the parameter is read only or not
     * 
     * @param readonly true for a read only parameter
     */
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
   
    /**
     * Creates and returns the corresponding parameter.
     * 
     * @return the corresponding parameter
     */
    public <U> Parameter<U> build() {
        return from(
                Objects.requireNonNull(this.name, "missing 'name'"), 
                Objects.requireNonNull(Providers.getTypeInstance(Objects.requireNonNull(this.type, "missing 'type'")), "unknown type: " + this.type), 
                this.value,
                this.readonly
        );
    }
    
    /**
     * Builds and returns parameter with the given type and value
     * @param <U>
     * @param label the label of the parameter (not null)
     * @param type the expected type (not null)
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

    @Override
    public <U extends Parameter<?>> U build(Class<U> clss, Type... genericTypes) throws Exception {
        return (U) build();
    }

}

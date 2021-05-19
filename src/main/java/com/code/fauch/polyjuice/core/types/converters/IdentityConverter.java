package com.code.fauch.polyjuice.core.types.converters;

import com.code.fauch.polyjuice.api.types.converters.IConverter;

public final class IdentityConverter<T> implements IConverter<T, T> {

    @Override
    public T toEntity(T rawData) {
        return rawData;
    }

    @Override
    public T toRawData(T entity) {
        return entity;
    }

}

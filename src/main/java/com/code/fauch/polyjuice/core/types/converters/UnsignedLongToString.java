package com.code.fauch.polyjuice.core.types.converters;

import com.code.fauch.polyjuice.api.types.converters.IConverter;

final class UnsignedLongToString implements IConverter<Long, String> {

    @Override
    public String toEntity(Long rawData) {
        return Long.toUnsignedString(rawData);
    }

    @Override
    public Long toRawData(String entity) {
        return Long.parseUnsignedLong(entity);
    }

}

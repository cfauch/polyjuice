package com.code.fauch.polyjuice.core.types.converters;

import com.code.fauch.polyjuice.api.types.converters.IConverter;

final class UnsignedShortToInt implements IConverter<Short, Integer> {

    @Override
    public Integer toEntity(final Short rawData) {
        return (rawData & 0xffff);
    }

    @Override
    public Short toRawData(final Integer entity) {
        return (short) (entity & 0xffff);
    }

}

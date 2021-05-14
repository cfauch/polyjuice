package com.code.fauch.polyjuice.core.types.converters;

import com.code.fauch.polyjuice.api.types.converters.IConverter;

final class UnsignedIntToLong implements IConverter<Integer, Long> {

    @Override
    public Long toEntity(final Integer rawData) {
        return ((long) (rawData)) & 0xffffffffL;
    }

    @Override
    public Integer toRawData(Long entity) {
        return (int) (entity & 0xffffffffL);
    }

}

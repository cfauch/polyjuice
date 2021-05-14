package com.code.fauch.polyjuice.core.types.converters;

import com.code.fauch.polyjuice.api.types.converters.IConverter;

final class UnsignedByteToShort implements IConverter<Byte, Short> {

    @Override
    public Short toEntity(final Byte rawData) {
        return (short) ((short)rawData & 0xff);
    }

    @Override
    public Byte toRawData(final Short entity) {
        return (byte) (entity & 0xff);
    }

}

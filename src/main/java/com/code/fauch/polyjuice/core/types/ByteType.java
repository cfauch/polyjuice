package com.code.fauch.polyjuice.core.types;

import java.nio.ByteBuffer;

import com.code.fauch.polyjuice.api.types.NumberType;

final class ByteType extends NumberType<Byte> {

    protected ByteType() {
        super(1);
    }

    @Override
    protected Byte read(final ByteBuffer buffer) {
        return buffer.get();
    }

    @Override
    protected ByteBuffer write(final ByteBuffer buffer, final Byte value) {
        return buffer.put(value);
    }

}

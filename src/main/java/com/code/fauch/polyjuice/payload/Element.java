package com.code.fauch.polyjuice.payload;

import java.nio.ByteOrder;

import com.code.fauch.polyjuice.core.bin.Bytes;

public abstract class Element {

    private final byte[] payload;
    private final int offset;
    private final int size;

    protected Element(final byte[] payload, final int offset, final int size) {
        this.payload = payload;
        this.size = size;
        this.offset = offset;
    }
    
    protected byte[] read(final ByteOrder byteOrder) {
        return Bytes.wrap(this.payload, this.offset, byteOrder).get(this.size);
    }

    protected void write(final byte[] buff, final ByteOrder byteOrder) {
        Bytes.wrap(this.payload, this.offset, byteOrder).set(buff, this.size);
    }
    
}

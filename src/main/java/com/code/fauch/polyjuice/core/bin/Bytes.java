package com.code.fauch.polyjuice.core.bin;

import java.nio.ByteOrder;

public final class Bytes {

    public Bytes() {
        // Nothing to do
    }

    public static IByteArray wrap(final byte[] buff, final int offset, final ByteOrder order) {
        return order == ByteOrder.LITTLE_ENDIAN ? new ByteArrayLE(buff, offset) : new ByteArrayBE(buff, offset);
    }
    
    public static int extract(final int b, final int length) {
        return extract(b, 0, length);
    }

    public static int extract(final int b, final int start, final int length) {
        if (start < 0) {
            throw new IndexOutOfBoundsException(String.format("start (%d) should be positive.", start));
        }
        if (length < 0) {
            throw new IndexOutOfBoundsException(String.format("length (%d) should be positive.", length));
        }
        final int shift = 8 - start - length;
        if (shift < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("length (%d) is too large according to start position (%d). Should be lesser then %d",
                            length, start, 8 - start));
        }
        return (b & (mask(length) << shift)) >> shift;
    }

    public static int replace(int b, int length, int v) {
        if (length < 0) {
            throw new IndexOutOfBoundsException(String.format("length (%d) should be positive.", length));
        }
        if (length > 8) {
            throw new IndexOutOfBoundsException(String.format("length (%d) should be lesser than 8.", length));
        }
        return replace(b, 0, length, v);
    }

    public static int replace(int b, int start, int length, int v) {
        if (length < 0) {
            throw new IndexOutOfBoundsException(String.format("length (%d) should be positive.", length));
        }
        if (length > 8) {
            throw new IndexOutOfBoundsException(String.format("length (%d) should be lesser than 8.", length));
        }
        final int mask = mask(length);
        final int retainMask = ~(mask << 8 - length - start) & 0xff;
        final int retain = (b & retainMask);
        final int value = (v & mask) << 8 - length - start;
        return retain | value;
    }

    public static int mask(final int size) {
        short buff = 0;
        for (int i = size - 1; i >= 0; i--) {
            buff |= 1 << i;
        }
        return buff;
    }

}

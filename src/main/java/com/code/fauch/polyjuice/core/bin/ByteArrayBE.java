package com.code.fauch.polyjuice.core.bin;

public final class ByteArrayBE {

    private int nbBytes;
    private int nbBits;
    private final byte[] buff;

    public static ByteArrayBE wrap(final byte[] buff, final int offset) { // offset = bytes + bits from the left
        final int index = offset / 8;
        final int pos = offset - (index * 8);
        return new ByteArrayBE(buff, index, pos);
    }

    private ByteArrayBE(final byte[] buff, final int index, final int position) {
        this.nbBytes = index;
        this.nbBits = position;
        this.buff = buff;
    }

    public byte[] get(final int length) {
        byte[] buff = null;
        final int n = length / 8;
        int j = 0;
        final short r = (short) (length - (n * 8));
        if (r != 0) {
            buff = new byte[n + 1];
            buff[j++] = getByte(r);
        } else {
            buff = new byte[n];
        }
        for (int i = 1; i <= n; i++) {
            buff[j++] = getByte(8);
        }
        return buff;
    }

    public byte[] getBytes() {
        return this.buff;
    }

    public void set(final byte[] value, final int length) {
        final int n = length / 8;
        final short r = (short) (length - (n * 8));
        int j = 0;
        if (r != 0) {
            setByte(value[j++], r);
        }
        for (int i = 1; i <= n; i++) {
            setByte(value[j++], 8);
        }
    }

    void setByte(byte value, int length) {
        int replacement = value << (8 - length);
        while (length > 0) {
            int r = Math.min(length, 8 - nbBits);
            this.buff[this.nbBytes] = (byte) Bytes.replace(this.buff[this.nbBytes], this.nbBits, r,
                    Bytes.extract(replacement, 0, r));
            this.nbBits += r;
            this.nbBytes += this.nbBits / 8;
            this.nbBits = this.nbBits % 8;
            length -= r;
            replacement <<= r;
        }
    }

    byte getByte(int length) {
        int b = 0;
        while (length > 0) {
            int r = Math.min(length, 8 - nbBits);
            b = b << r;
            b |= Bytes.extract(this.buff[nbBytes], this.nbBits, r);
            this.nbBits += r;
            this.nbBytes += this.nbBits / 8;
            this.nbBits = this.nbBits % 8;
            length -= r;
        }
        return (byte) b;
    }

}

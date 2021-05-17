package com.code.fauch.polyjuice.core.bin;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.Assert;
import org.junit.Test;

public class BytesTest {
    
    @Test
    public void testMask() {
            Assert.assertEquals(0b111, Bytes.mask(3));
            Assert.assertEquals(3, Bytes.mask(2));
            Assert.assertEquals(1, Bytes.mask(1));
    }
    
    @Test
    public void testReplace( ) {
            Assert.assertEquals(0b01001011, Bytes.replace(0b11001011, 2, 0b01));
            Assert.assertEquals(0b10101011, Bytes.replace(0b11001011, 3, 0b101));
            Assert.assertEquals(0b11011011, Bytes.replace(0b11001011, 4, 0b1101));
            Assert.assertEquals(0b11011011, Bytes.replace(0b11001011, 4, 0b11101));
            Assert.assertEquals(0b10111010, Bytes.replace(0b11011010, 3, 0b101));
    }

    @Test
    public void testReplaceMax( ) {
            Assert.assertEquals(0b11101010, Bytes.replace(0b11001010, 2, 3, 0b101));
    }

    @Test
    public void testExtract() {
            Assert.assertEquals(0b11, Bytes.extract(0b11001011, 2));
            Assert.assertEquals(0b110, Bytes.extract(0b11001011, 3));
            Assert.assertEquals(0b1100, Bytes.extract(0b11001011, 4));
            Assert.assertEquals(0b11001, Bytes.extract(0b11001011, 5));
            Assert.assertEquals(0b110010, Bytes.extract(0b11001011, 6));
    }

    @Test
    public void testExtractMax() {
            Assert.assertEquals(0b001, Bytes.extract(0b11001011, 2, 3));
            Assert.assertEquals(0b0101, Bytes.extract(0b11001011, 3, 4));
            Assert.assertEquals(0b1, Bytes.extract(0b11001011, 4, 1));
            Assert.assertEquals(0b1011, Bytes.extract(0b11001011, 4, 4));
            Assert.assertEquals(0b0, Bytes.extract(0b11001011, 4, 0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExtractLengthOufOfRange() {
            Assert.assertEquals(0b1011, Bytes.extract(0b11001011, 4, 5));
    }

    @Test
    public void testLE() {
        final byte[] buff = new byte[]{(byte)0b00000011 , (byte) 0b11111111};
        final Short be = ByteBuffer.wrap(buff).order(ByteOrder.BIG_ENDIAN).getShort();
        Assert.assertEquals(1023, be.shortValue());
        final byte[] le = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short)1023).array();
        Assert.assertArrayEquals(new byte[] {(byte) 0b11111111, (byte)0b00000011}, le);
    }

    @Test
    public void testLEA() {
        final byte[] buff = new byte[]{(byte)0b00000011 , (byte) 0b11110111};
        final Short be = ByteBuffer.wrap(buff).order(ByteOrder.BIG_ENDIAN).getShort();
        Assert.assertEquals(1015, be.shortValue());
        final byte[] le = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short)1015).array();
        Assert.assertArrayEquals(new byte[] {(byte) 0b11110111, (byte)0b00000011}, le);
    }

}

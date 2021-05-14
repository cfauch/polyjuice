package com.code.fauch.polyjuice.core.types;

import java.nio.ByteOrder;

import org.junit.Assert;
import org.junit.Test;

public class StandardTypesTest {

    @Test
    public void testByteToBytes() {
        final byte[] buff = StandardTypes.BYTE.toBytes((byte) -1, ByteOrder.BIG_ENDIAN);
        Assert.assertArrayEquals(new byte[] {(byte) 0b11111111}, buff);
    }

    @Test
    public void testByteFromBytes() {
        final byte value = StandardTypes.BYTE.fromBytes(new byte[] {(byte) 0b10000000}, ByteOrder.BIG_ENDIAN);
        Assert.assertEquals(-128, value);
    }

    @Test
    public void testShortToBytes() {
        final byte[] buff = StandardTypes.SHORT.toBytes((short)258, ByteOrder.BIG_ENDIAN);
        Assert.assertArrayEquals(new byte[] {(byte) 0b00000001, (byte) 0b00000010}, buff);
    }

    @Test
    public void testShortToBytesLE() {
        final byte[] buff = StandardTypes.SHORT.toBytes((short)258, ByteOrder.LITTLE_ENDIAN);
        Assert.assertArrayEquals(new byte[] {(byte) 0b00000010, (byte) 0b00000001}, buff);
    }

    @Test
    public void testShortFromBytes() {
        final short value = StandardTypes.SHORT.fromBytes(new byte[] {(byte) 0b00000001, (byte) 0b00000010}, ByteOrder.BIG_ENDIAN);
        Assert.assertEquals(258, value);
    }

    @Test
    public void testShortFromBytesLE() {
        final short value = StandardTypes.SHORT.fromBytes(new byte[] {(byte) 0b00000010, (byte) 0b00000001}, ByteOrder.LITTLE_ENDIAN);
        Assert.assertEquals(258, value);
    }

    @Test
    public void testIntegerToBytes() {
        final byte[] buff = StandardTypes.INTEGER.toBytes(66289, ByteOrder.BIG_ENDIAN);
        Assert.assertArrayEquals(new byte[] {(byte) 0b00000000, (byte) 0b00000001, (byte) 0b00000010, (byte) 0b11110001}, buff);
    }

    @Test
    public void testIntegerToBytesLE() {
        final byte[] buff = StandardTypes.INTEGER.toBytes(66289, ByteOrder.LITTLE_ENDIAN);
        Assert.assertArrayEquals(new byte[] {(byte) 0b11110001, (byte) 0b00000010, (byte) 0b00000001, (byte) 0b00000000}, buff);
    }

    @Test
    public void testIntegerFromBytes() {
        final int value = StandardTypes.INTEGER.fromBytes(new byte[] {(byte) 0b00000000, (byte) 0b00000001, (byte) 0b00000010, (byte) 0b11110001}, ByteOrder.BIG_ENDIAN);
        Assert.assertEquals(66289, value);
    }

    @Test
    public void testIntegerFromBytesLE() {
        final int value = StandardTypes.INTEGER.fromBytes(new byte[] {(byte) 0b11110001, (byte) 0b00000010, (byte) 0b00000001, (byte) 0b00000000}, ByteOrder.LITTLE_ENDIAN);
        Assert.assertEquals(66289, value);
    }

    @Test
    public void testLongToBytes() {
        final byte[] buff = StandardTypes.LONG.toBytes(4295033585L, ByteOrder.BIG_ENDIAN);
        Assert.assertArrayEquals(new byte[] {
                    (byte) 0b00000000, (byte) 0b00000000, (byte) 0b00000000, (byte) 0b00000001,
                    (byte) 0b00000000, (byte) 0b00000001, (byte) 0b00000010, (byte) 0b11110001
                }, buff);
    }

    @Test
    public void testLongToBytesLE() {
        final byte[] buff = StandardTypes.LONG.toBytes(4295033585L, ByteOrder.LITTLE_ENDIAN);
        Assert.assertArrayEquals(new byte[] {
                    (byte) 0b11110001, (byte) 0b00000010, (byte) 0b00000001, (byte) 0b00000000,
                    (byte) 0b00000001, (byte) 0b00000000, (byte) 0b00000000, (byte) 0b00000000
                }, buff);
    }

    @Test
    public void testLongFromBytes() {
        long value = StandardTypes.LONG.fromBytes(
                new byte[] {
                        (byte) 0b00000000, (byte) 0b00000000, (byte) 0b00000000, (byte) 0b00000001,
                        (byte) 0b00000000, (byte) 0b00000001, (byte) 0b00000010, (byte) 0b11110001
                    }, 
                ByteOrder.BIG_ENDIAN);
        Assert.assertEquals(4295033585L, value);
    }

    @Test
    public void testLongFromBytesLE() {
        long value = StandardTypes.LONG.fromBytes(
                new byte[] {
                        (byte) 0b11110001, (byte) 0b00000010, (byte) 0b00000001, (byte) 0b00000000,
                        (byte) 0b00000001, (byte) 0b00000000, (byte) 0b00000000, (byte) 0b00000000
                    }, 
                ByteOrder.LITTLE_ENDIAN);
        Assert.assertEquals(4295033585L, value);
    }

}

package com.code.fauch.polyjuice.core.bin;

import org.junit.Assert;
import org.junit.Test;

public class ByteArrayBETest {

    @Test
    public void testGetByte() {
            final ByteArrayBE ba = ByteArrayBE.wrap(new byte[] {(byte) 0b11011010, (byte) 0b10011101}, 5);
            Assert.assertEquals(0b01010, ba.getByte(5));
            Assert.assertEquals(0b0, ba.getByte(1));
            Assert.assertEquals(0b111, ba.getByte(3));
            Assert.assertEquals(0b01, ba.getByte(2));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetByteOutOfRange() {
            final ByteArrayBE ba = ByteArrayBE.wrap(new byte[] {(byte) 0b11011010}, 5);
            Assert.assertEquals(0b01010, ba.getByte(5));
    }
    
    @Test
    public void testGetOneByte() {
            final ByteArrayBE ba = ByteArrayBE.wrap(new byte[] {(byte) 0b11011010, (byte) 0b10011101}, 5);
            Assert.assertArrayEquals(new byte[] {0b01010}, ba.get(5));
    }

    @Test
    public void testGet() {
            final ByteArrayBE ba = ByteArrayBE.wrap(new byte[] {(byte) 0b11011010, (byte) 0b10011101, (byte) 0b01110010}, 5);
            Assert.assertArrayEquals(new byte[] {(byte) 0b00101001, (byte) 0b11010111}, ba.get(15));
    }
    
    @Test
    public void testGetSeveralBytes() {
            final ByteArrayBE ba = ByteArrayBE.wrap(new byte[] {(byte) 0b11011010, (byte) 0b10011101, (byte) 0b01110010, (byte) 0b00001111}, 5);
            Assert.assertArrayEquals(new byte[] {(byte) 0b00000101, (byte) 0b00111010, (byte) 0b11100100}, ba.get(20));
    }

    @Test
    public void testGetJustBytes() {
            final ByteArrayBE ba = ByteArrayBE.wrap(new byte[] {(byte) 0b11011010, (byte) 0b10011101, (byte) 0b01110010, (byte) 0b00001111}, 5);
            Assert.assertArrayEquals(new byte[] {(byte) 0b01010011, (byte) 0b10101110}, ba.get(16));
    }

    @Test
    public void testSet() {
            final ByteArrayBE ba = ByteArrayBE.wrap(new byte[] {(byte) 0b11011010, (byte) 0b10011101, (byte) 0b00010100, (byte) 0b00100011}, 5);
            ba.set(new byte[] {(byte) 0b101000, (byte) 0b01110001, (byte) 0b11011010}, 22);
            Assert.assertArrayEquals(new byte[] {(byte) 0b11011101, (byte) 0b00001110, (byte) 0b00111011, (byte) 0b01000011}, ba.getBytes());
    }

    @Test
    public void testSetOneByte() {
            final ByteArrayBE ba = ByteArrayBE.wrap(new byte[] {(byte) 0b11011010, (byte) 0b10011101}, 5);
            ba.setByte((byte) 0b10101, 5);
            Assert.assertArrayEquals(new byte[] {(byte) 0b11011101, (byte) 0b01011101}, ba.getBytes());
    }

    @Test
    public void testGetOffset0() {
            final ByteArrayBE ba = ByteArrayBE.wrap(new byte[] {(byte) 0b11111101, (byte) 0b11000001}, 0);
            Assert.assertArrayEquals(new byte[] {(byte) 0b00000011, (byte) 0b11110111}, ba.get(10));
    }

}

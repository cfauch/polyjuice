package com.code.fauch.polyjuice.core.bin;

import org.junit.Assert;
import org.junit.Test;

public class ByteArrayLETest {

    @Test
    public void testGetByte() {
            final ByteArrayLE ba = new ByteArrayLE(new byte[] {(byte) 0b10011101, (byte) 0b11011010}, 5);
            Assert.assertEquals(0b01010, ba.getByte(5));
            Assert.assertEquals(0b0, ba.getByte(1));
            Assert.assertEquals(0b111, ba.getByte(3));
            Assert.assertEquals(0b01, ba.getByte(2));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetByteOutOfRange() {
            final ByteArrayLE ba = new ByteArrayLE(new byte[] {(byte) 0b11011010}, 5);
            Assert.assertEquals(0b01010, ba.getByte(5));
    }

    @Test
    public void testGetOneByte() {
            final ByteArrayLE ba = new ByteArrayLE(new byte[] {(byte) 0b10011101, (byte) 0b11011010}, 5);
            Assert.assertArrayEquals(new byte[] {0b01010}, ba.get(5));
    }

    @Test
    public void testGet() {
            final ByteArrayLE ba = new ByteArrayLE(new byte[] {(byte) 0b01110010, (byte) 0b10011101, (byte) 0b11011010}, 5);
            Assert.assertArrayEquals(new byte[] {(byte) 0b11010111, (byte) 0b00101001}, ba.get(15));
    }

    @Test
    public void testGetSeveralBytes() {
            final ByteArrayLE ba = new ByteArrayLE(new byte[] {(byte) 0b00001111, (byte) 0b01110010, (byte) 0b10011101, (byte) 0b11011010}, 5);
            Assert.assertArrayEquals(new byte[] {(byte) 0b11100100, (byte) 0b00111010, (byte) 0b00000101}, ba.get(20));
    }

    @Test
    public void testGetSeveralBytesB() {
            final ByteArrayLE ba = new ByteArrayLE(new byte[] {(byte) 0b11011010, (byte) 0b10011101, (byte) 0b01110010, (byte) 0b00001111}, 4);
            Assert.assertArrayEquals(new byte[] {(byte) 0b01111011}, ba.get(7));
    }

    @Test
    public void testGetSeveralBytesD() {
            final ByteArrayLE ba = new ByteArrayLE(new byte[] {(byte) 0b11011010, (byte) 0b10011101, (byte) 0b01110010, (byte) 0b00001111}, 16);
            Assert.assertArrayEquals(new byte[] {(byte) 0b11011010, (byte) 0b10011101}, ba.get(16));
    }

    @Test
    public void testGetJustBytes() {
            final ByteArrayLE ba = new ByteArrayLE(new byte[] {(byte) 0b00001111, (byte) 0b01110010, (byte) 0b10011101, (byte) 0b11011010}, 5);
            Assert.assertArrayEquals(new byte[] {(byte) 0b10101110, (byte) 0b01010011}, ba.get(16));
    }

    @Test
    public void testSet() {
            final ByteArrayLE ba = new ByteArrayLE(new byte[] {(byte) 0b00100011, (byte) 0b00010100, (byte) 0b10011101, (byte) 0b11011010}, 5);
            ba.set(new byte[] {(byte) 0b11011010, (byte) 0b01110001, (byte) 0b101000}, 22);
            Assert.assertArrayEquals(new byte[] {(byte) 0b01000011, (byte) 0b00111011, (byte) 0b00001110, (byte) 0b11011101}, ba.array());
    }

    @Test
    public void testSetOneByte() {
            final ByteArrayLE ba = new ByteArrayLE(new byte[] {(byte) 0b10011101, (byte) 0b11011010}, 5);
            ba.setByte((byte) 0b10101, 5);
            Assert.assertArrayEquals(new byte[] {(byte) 0b01011101, (byte) 0b11011101}, ba.array());
    }

    @Test
    public void testGetOffset0() {
            final ByteArrayLE ba = new ByteArrayLE(new byte[] {(byte) 0b11000001, (byte) 0b11111101}, 0);
            Assert.assertArrayEquals(new byte[] {(byte) 0b11110111, (byte) 0b00000011}, ba.get(10));
    }

}

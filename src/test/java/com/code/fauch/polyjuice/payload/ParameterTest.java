package com.code.fauch.polyjuice.payload;

import java.nio.ByteOrder;

import org.junit.Assert;
import org.junit.Test;

import com.code.fauch.polyjuice.core.types.StandardTypes;
import com.code.fauch.polyjuice.core.types.converters.UnsignedConverters;

public class ParameterTest {
    
    @Test 
    public void testParameter() {
        final byte[] copy = new byte[] {(byte) 0b00001111, (byte) 0b01110010, (byte) 0b11011010, (byte) 0b10011101};
        final Parameter<Short, Integer> param = Parameter.newParameter(copy, 16, 16,ByteOrder.BIG_ENDIAN, StandardTypes.SHORT, UnsignedConverters.USHORT_TO_INT);
        Assert.assertEquals(55965, param.get().intValue());
        param.set(875);
        Assert.assertArrayEquals(new byte[] {(byte) 0b00001111, (byte) 0b01110010, (byte) 0b00000011, (byte) 0b01101011}, copy);
        Assert.assertEquals(875, param.get().intValue());
    }

    @Test
    public void testParameterLE() {
        final byte[] copy = new byte[] {(byte) 0b10011101, (byte) 0b11011010, (byte) 0b01110010, (byte) 0b00001111};
        final Parameter<Short, Integer> param = Parameter.newParameter(copy, 16, 16, ByteOrder.LITTLE_ENDIAN, StandardTypes.SHORT, UnsignedConverters.USHORT_TO_INT);
        Assert.assertEquals(55965, param.get().intValue());
        param.set(875);
        Assert.assertArrayEquals(new byte[] {(byte) 0b01101011, (byte) 0b00000011, (byte) 0b01110010, (byte) 0b00001111}, copy);
        Assert.assertEquals(875, param.get().intValue());
    }

    @Test 
    public void testParameterOffset() {
        final byte[] copy = new byte[] {(byte) 0b00001111, (byte) 0b01110010, (byte) 0b10011101, (byte) 0b11011010};
        final Parameter<Byte, Short> param = Parameter.newParameter(copy, 4, 7, ByteOrder.BIG_ENDIAN, StandardTypes.BYTE, UnsignedConverters.UBYTE_TO_SHORT);
        Assert.assertEquals((short)123, param.get().shortValue());
        param.set((short)127);
        Assert.assertArrayEquals(new byte[] {(byte) 0b00001111, (byte) 0b11110010, (byte) 0b10011101, (byte) 0b11011010}, copy);
        Assert.assertEquals((short)127, param.get().shortValue());
    }

    @Test
    public void testParameterOffsetLE() {
        final byte[] copy = new byte[] {(byte) 0b10011101, (byte) 0b11011010, (byte) 0b01110010, (byte) 0b00001111};
        final Parameter<Byte, Short> param = Parameter.newParameter(copy, 4, 7, ByteOrder.LITTLE_ENDIAN, StandardTypes.BYTE, UnsignedConverters.UBYTE_TO_SHORT);
        Assert.assertEquals((short)123, param.get().shortValue());
        param.set((short)127);
        Assert.assertArrayEquals(new byte[] {(byte) 0b10011101, (byte) 0b11011010, (byte) 0b11110010, (byte) 0b00001111}, copy);
        Assert.assertEquals((short)127, param.get().shortValue());
    }

}

package com.code.fauch.polyjuice.core.types.converters;

import org.junit.Assert;
import org.junit.Test;

public class UnsignedConvertersTest {

    @Test
    public void testUByteToShort() {
        short result = UnsignedConverters.UBYTE_TO_SHORT.toEntity((byte) 0b10000000);
        Assert.assertEquals(128, result);
    }
    
    @Test
    public void testShortToUByte() {
        byte result = UnsignedConverters.UBYTE_TO_SHORT.toRawData((short) 255);
        Assert.assertEquals(-1, result);
    }
    
    @Test
    public void testUShortToInt() {
        int result = UnsignedConverters.USHORT_TO_INT.toEntity((short) 0b1000000000000000);
        Assert.assertEquals(32768, result);
    }

    @Test
    public void testIntToUShort() {
        short result = UnsignedConverters.USHORT_TO_INT.toRawData(65535);
        Assert.assertEquals(-1, result);
    }
    
    @Test
    public void testUIntToLong() {
        long result = UnsignedConverters.UINT_TO_LONG.toEntity(-2147483648);
        Assert.assertEquals(2147483648L, result);
    }
    
    @Test
    public void testLongToUInt() {
        int value = UnsignedConverters.UINT_TO_LONG.toRawData(2147483648L);
        Assert.assertEquals(-2147483648, value);
    }
    
    @Test
    public void testULongToString() {
        String result = UnsignedConverters.ULONG_TO_STRING.toEntity(-9223372036854775808L);
        Assert.assertEquals("9223372036854775808", result);
    }

    @Test
    public void testStringToULong() {
        long result = UnsignedConverters.ULONG_TO_STRING.toRawData("9223372036854775808");
        Assert.assertEquals(-9223372036854775808L, result);
    }

}

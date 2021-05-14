package com.code.fauch.polyjuice.core.types.converters;

import com.code.fauch.polyjuice.api.types.converters.IConverter;
import com.code.fauch.polyjuice.api.types.converters.IConverterProvider;

public final class UnsignedConverters implements IConverterProvider {

    public static final IConverter<Byte, Short> UBYTE_TO_SHORT = new UnsignedByteToShort();
    public static final IConverter<Short, Integer> USHORT_TO_INT = new UnsignedShortToInt();
    public static final IConverter<Integer, Long> UINT_TO_LONG = new UnsignedIntToLong();
    public static final IConverter<Long, String> ULONG_TO_STRING = new UnsignedLongToString();
    
    @SuppressWarnings("unchecked")
    @Override
    public <R, E> IConverter<R, E> forClasses(final Class<R> rawDataCls, final Class<E> entityCls) {
        if (rawDataCls == Byte.class && entityCls == Short.class) {
            return (IConverter<R, E>) UBYTE_TO_SHORT;
        }
        if (rawDataCls == Short.class && entityCls == Integer.class) {
            return (IConverter<R, E>) USHORT_TO_INT;
        }
        if (rawDataCls == Integer.class && entityCls == Long.class) {
            return (IConverter<R, E>) UINT_TO_LONG;
        }        
        if (rawDataCls == Long.class && entityCls == String.class) {
            return (IConverter<R, E>) ULONG_TO_STRING;
        }        
        return null;
    }

}

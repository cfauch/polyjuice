package com.code.fauch.polyjuice.core.types;

import com.code.fauch.polyjuice.api.types.IType;
import com.code.fauch.polyjuice.api.types.ITypeProvider;

public final class StandardTypes implements ITypeProvider {

    public static final IType<Byte> BYTE = new ByteType();
    public static final IType<Short> SHORT = new ShortType();
    public static final IType<Integer> INTEGER = new IntegerType();
    public static final IType<Long> LONG = new LongType();

    @SuppressWarnings("unchecked")
    @Override
    public <U> IType<U> forClass(final Class<U> cls) {
        if (cls == Byte.class) {
            return (IType<U>) BYTE;
        }
        if (cls == Short.class) {
            return (IType<U>) SHORT;
        }
        if (cls == Integer.class) {
            return (IType<U>) INTEGER;
        }
        if (cls == Long.class) {
            return (IType<U>) LONG;
        }
        return null;
    }

}

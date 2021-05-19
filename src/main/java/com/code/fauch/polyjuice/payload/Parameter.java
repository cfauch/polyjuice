package com.code.fauch.polyjuice.payload;

import java.nio.ByteOrder;

import com.code.fauch.polyjuice.api.types.IType;
import com.code.fauch.polyjuice.api.types.converters.IConverter;
import com.code.fauch.polyjuice.core.types.converters.IdentityConverter;

public final class Parameter<R, E> extends Element {

    private final ByteOrder byteOrder;
    private final IType<R> rawType;
    private final IConverter<R, E> converter;

    private Parameter(final byte[] payload, final int offset, final int size, final ByteOrder byteOrder,
            final IType<R> rawType, final IConverter<R, E> converter) {
        super(payload, offset, size);
        this.rawType = rawType;
        this.converter = converter;
        this.byteOrder = byteOrder;
    }

    public static <T> Parameter<T, T> newParameter(final byte[] payload, final int offset, final int size, final ByteOrder byteOrder,
            final IType<T> rawType) {
        return new Parameter<>(payload, offset, size, byteOrder, rawType, new IdentityConverter<T>());
    }

    public static <U, V> Parameter<U, V> newParameter(final byte[] payload, final int offset, final int size, final ByteOrder byteOrder,
            final IType<U> rawType, final IConverter<U, V> converter) {
        return new Parameter<>(payload, offset, size, byteOrder, rawType, converter);
    }

    public E get() {
        final byte[] buff = read(this.byteOrder);
        final R rawData = this.rawType.fromBytes(buff, this.byteOrder);
        return this.converter.toEntity(rawData);
    }

    public void set(final E value) {
        final R rawData = this.converter.toRawData(value);
        final byte[] buff = this.rawType.toBytes(rawData, this.byteOrder);
        write(buff, this.byteOrder);
    }

}

package com.code.fauch.polyjuice.api.types;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class NumberType<N extends Number> implements IType<N> {

	private final int size;
	
	protected NumberType(final int size) {
		this.size = size;
	}
	
	@Override
	public final byte[] toBytes(N value, ByteOrder order) {
		return write(ByteBuffer.allocate(this.size).order(order), value).array();
	}
	
	@Override
	public final N fromBytes(byte[] bytes, ByteOrder order) {
		return read(ByteBuffer.wrap(bytes).order(order));
	}

	protected abstract N read(ByteBuffer buffer);

	protected abstract ByteBuffer write(ByteBuffer buffer, N value);
	
}

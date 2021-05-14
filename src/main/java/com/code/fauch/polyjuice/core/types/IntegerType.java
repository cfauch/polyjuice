package com.code.fauch.polyjuice.core.types;

import java.nio.ByteBuffer;

import com.code.fauch.polyjuice.api.types.NumberType;

final class IntegerType extends NumberType<Integer> {

	protected IntegerType() {
		super(4);
	}

	@Override
	protected Integer read(final ByteBuffer buffer) {
		return buffer.getInt();
	}

	@Override
	protected ByteBuffer write(final ByteBuffer buffer, final Integer value) {
		return buffer.putInt(value);
	}

}

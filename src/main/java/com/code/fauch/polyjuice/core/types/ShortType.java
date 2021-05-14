package com.code.fauch.polyjuice.core.types;

import java.nio.ByteBuffer;

import com.code.fauch.polyjuice.api.types.NumberType;

final class ShortType extends NumberType<Short> {

	protected ShortType() {
		super(2);
	}

	@Override
	protected Short read(final ByteBuffer buffer) {
		return buffer.getShort();
	}

	@Override
	protected ByteBuffer write(final ByteBuffer buffer, final Short value) {
		return buffer.putShort(value);
	}

}

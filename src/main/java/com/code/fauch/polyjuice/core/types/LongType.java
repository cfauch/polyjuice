package com.code.fauch.polyjuice.core.types;

import java.nio.ByteBuffer;

import com.code.fauch.polyjuice.api.types.NumberType;

final class LongType extends NumberType<Long> {

	protected LongType() {
		super(8);
	}

	@Override
	protected Long read(final ByteBuffer buffer) {
		return buffer.getLong();
	}

	@Override
	protected ByteBuffer write(final ByteBuffer buffer, final Long value) {
		return buffer.putLong(value);
	}

}

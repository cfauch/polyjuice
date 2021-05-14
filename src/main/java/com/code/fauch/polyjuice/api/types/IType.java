package com.code.fauch.polyjuice.api.types;

import java.nio.ByteOrder;

public interface IType <T> {

	byte[] toBytes(T value, ByteOrder order);
	
	T fromBytes(byte[] bytes, ByteOrder order);
	
}

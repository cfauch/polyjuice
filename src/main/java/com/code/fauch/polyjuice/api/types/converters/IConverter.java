package com.code.fauch.polyjuice.api.types.converters;

public interface IConverter <R, E> {

	E toEntity(R rawData);
	
	R toRawData(E entity);
	
}

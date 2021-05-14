package com.code.fauch.polyjuice.api.types.converters;

public interface IConverterProvider {

	<R, E> IConverter<R, E> forClasses(Class<R> rawDataCls, Class<E> entityCls);
	
}

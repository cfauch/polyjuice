package com.code.fauch.polyjuice.api.types;

public interface ITypeProvider {

	<U> IType<U> forClass(Class<U> cls);

}

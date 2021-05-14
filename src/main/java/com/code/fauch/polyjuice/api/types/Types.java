package com.code.fauch.polyjuice.api.types;

import java.util.HashMap;
import java.util.ServiceLoader;

public final class Types {

	private final HashMap<Class<?>, IType<?>> cache;
	private final ServiceLoader<ITypeProvider> providers;
	
	public Types() {
		this.cache = new HashMap<>();
		this.providers = ServiceLoader.load(ITypeProvider.class);
	}
	
	public <U> IType<U> forClass(final Class<U> cls) throws ClassNotFoundException {
		@SuppressWarnings("unchecked")
		final IType<U> type = (IType<U>) this.cache.computeIfAbsent(cls, this::lookup);
		if (type == null) {
			throw new UnsupportedTypeException(cls);
		}
		return type;
	}
	
	private <U> IType<U> lookup(final Class<U> cls) {
		for (ITypeProvider provider : this.providers) {
			final IType<U> type = provider.forClass(cls);
			if (type != null) {
				return type;
			}
		}
		return null;
	}
}

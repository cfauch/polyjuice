package com.code.fauch.polyjuice.api.types.converters;

import java.util.HashMap;
import java.util.ServiceLoader;

public final class Converters {

	private final HashMap<Classes<?, ?>, IConverter<?, ?>> cache;
	private final ServiceLoader<IConverterProvider> providers;

	private static class Classes<R, E> {
		
		private final Class<R> rawDataCls;
		private final Class<E> entityCls;
		
		private Classes(final Class<R> rawData, final Class<E> entity) {
			this.rawDataCls = rawData;
			this.entityCls = entity;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((entityCls == null) ? 0 : entityCls.hashCode());
			result = prime * result + ((rawDataCls == null) ? 0 : rawDataCls.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Classes<?, ?> other = (Classes<?, ?>) obj;
			if (entityCls == null) {
				if (other.entityCls != null)
					return false;
			} else if (!entityCls.equals(other.entityCls))
				return false;
			if (rawDataCls == null) {
				if (other.rawDataCls != null)
					return false;
			} else if (!rawDataCls.equals(other.rawDataCls))
				return false;
			return true;
		}

	}
	
	public Converters() {
		this.cache = new HashMap<Converters.Classes<?,?>, IConverter<?,?>>();
		this.providers = ServiceLoader.load(IConverterProvider.class);
	}
	
	public <R, E> IConverter<R, E> forClasses(final Class<R> rawDataCls, final Class<E> entityCls) {
		final Classes<R, E> key = new Classes<>(rawDataCls, entityCls);
		@SuppressWarnings("unchecked")
		final IConverter<R, E> converter =  (IConverter<R, E>) this.cache.computeIfAbsent(key, this::lookup);
		if (converter == null) {
			throw new UnsupportedConverterException(rawDataCls, entityCls);
		}
		return converter;
	}
	
	private <R, E> IConverter<R, E> lookup(final Classes<R, E> classes) {
		for (IConverterProvider provider : this.providers) {
			final IConverter<R, E> converter = provider.forClasses(classes.rawDataCls, classes.entityCls);
			if (converter != null) {
				return converter;
			}
		}
		return null;
	}
	
}

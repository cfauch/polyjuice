package com.code.fauch.polyjuice.api.types.converters;

public class UnsupportedConverterException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	private Class<?> rawDataClass;
	private Class<?> entityClass;
	
    /**
     * Constructs an instance of this class.
     *
     * @param rawDataCls The class of the raw data class
     * @param entityCls The class of the entity class
     */
    public UnsupportedConverterException(Class<?> rawDataCls, Class<?> entityCls) {
        super(String.format("raw data: %s, entity: %s", rawDataCls, entityCls));
        this.rawDataClass = rawDataCls;
        this.entityClass = entityCls;
    }

	/**
     * Retrieves the class of the raw data.
     *
     * @return  The class of the raw data
     */
    public Class<?> getRawDataClass() {
        return this.rawDataClass;
    }

	/**
     * Retrieves the class of the entity.
     *
     * @return  The class of the entity
     */
    public Class<?> getEntityClass() {
        return this.entityClass;
    }

}

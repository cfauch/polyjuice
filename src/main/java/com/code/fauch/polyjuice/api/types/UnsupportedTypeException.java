package com.code.fauch.polyjuice.api.types;

public final class UnsupportedTypeException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	private Class<?> typeClass;
	
    /**
     * Constructs an instance of this class.
     *
     * @param  cls
     *         The class of the unsupported type
     */
    public UnsupportedTypeException(Class<?> cls) {
        super(String.valueOf(cls));
        this.typeClass = cls;
    }

	/**
     * Retrieves the class of the unsupported type.
     *
     * @return  The class of the unsupported type
     */
    public Class<?> getTypeClass() {
        return typeClass;
    }

}

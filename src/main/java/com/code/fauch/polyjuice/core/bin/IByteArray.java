package com.code.fauch.polyjuice.core.bin;

public interface IByteArray {

    byte[] get(int length);

    void set(byte[] value, int length);
    
    byte[] array();
    
}

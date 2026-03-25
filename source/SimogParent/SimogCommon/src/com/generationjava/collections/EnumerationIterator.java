package com.generationjava.collections;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * A class which allows an Enumeration to be turned into an Iterator.
 *
 * @date 2000-12-11
 */
public class EnumerationIterator implements Iterator {

    private Enumeration wrappedEnum;

    public EnumerationIterator(Enumeration enumm) {
        wrappedEnum = enumm;
    } 

    public Object next() {
        return wrappedEnum.nextElement();
    }
    
    public boolean hasNext() {
        return wrappedEnum.hasMoreElements();
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }

}

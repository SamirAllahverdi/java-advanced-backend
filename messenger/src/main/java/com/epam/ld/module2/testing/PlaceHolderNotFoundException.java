package com.epam.ld.module2.testing;

public class PlaceHolderNotFoundException extends RuntimeException {



    public PlaceHolderNotFoundException(String placeholder) {
        super(String.format("Placeholder with '%s' was not found!", placeholder));
    }
}

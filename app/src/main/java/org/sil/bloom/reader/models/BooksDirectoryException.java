package org.sil.bloom.reader.models;

import java.io.IOException;

public class BooksDirectoryException extends IOException {
    private BooksDirectory.Accessiblity externalMediaAccessibility;

    public BooksDirectoryException(BooksDirectory.Accessiblity accessiblity){
        super("Unable to mount external books directory for writing.");
        externalMediaAccessibility = accessiblity;
    }

    public BooksDirectory.Accessiblity getExternalMediaAccessibility(){
        return externalMediaAccessibility;
    }
}


package org.sil.bloom.reader.models;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class BooksDirectory {
    public enum Accessiblity{
        ACCESSIBLE, SHARED, INACCESSIBLE
    }
    private static String EXTERNAL_DIRECTORY = "Bloom";
    private static String INTERNAL_DIRECTORY = "Library";

    private Context context;
    private boolean preferExternal = false;
    private boolean preferInternal = false;

    public BooksDirectory(Context context){
        this.context = context;
    }

    public BooksDirectory(Context context, boolean usingExternalStorage){
        this(context);
        preferExternal = usingExternalStorage;
        preferInternal = !usingExternalStorage;
    }

    public Accessiblity getAccessibility(){
        String externalMediaState = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(externalMediaState))
            return Accessiblity.ACCESSIBLE;
        else if(Environment.MEDIA_SHARED.equals(externalMediaState))
            return Accessiblity.SHARED;
        else
            return Accessiblity.INACCESSIBLE;
    }

    //Returns the directory or null for failure
    public File directory() throws BooksDirectoryException{
        if(preferInternal)
            return internalDirectory();
        Accessiblity accessibility = getAccessibility();
        if(accessibility == Accessiblity.ACCESSIBLE)
            return externalDirectory();
        if(!preferExternal && accessibility == Accessiblity.INACCESSIBLE)
            return internalDirectory();

        throw new BooksDirectoryException(accessibility);
    }

    private File externalDirectory(){
        //TODO create if it doesn't exist
        return Environment.getExternalStoragePublicDirectory(EXTERNAL_DIRECTORY);
    }

    private File internalDirectory(){
        //TODO create if it doesn't exist
        return context.getDir(INTERNAL_DIRECTORY, 0);
    }
}
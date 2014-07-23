package com.hansonchris.android.storage;

import android.content.Context;

import java.util.Map;

public class StorageFactory
{
    public static final String PARAM_FILENAME = "param_filename";
    public static final String PARAM_EXTENSION = "param_extension";
    public static final String PARAM_DIRECTORY = "param_directory";

    static public StorageInterface getStorage(
        Context context,
        StorageFactory.Type type
    ) {
        return getStorage(context, type, null);
    }

    static public StorageInterface getStorage(
        Context context,
        StorageFactory.Type type,
        Map<String, String> params
    ) {
        StorageInterface storage = null;
        String directory;
        String filename;
        String extension;
        switch (type) {
            case File:
                if (params != null) {
                    filename = getFilename(params);
                    extension = getExtension(params);
                    directory = getDirectory(params);
                    storage = new StorageFile(context, directory, filename, extension);
                }
                break;
            case Memcache:
                storage = new StorageMemcache();
                break;
            case MemcacheFile:
                if (params != null) {
                    filename = getFilename(params);
                    extension = getExtension(params);
                    directory = getDirectory(params);
                    storage = new StorageMemcacheFile(context, directory, filename, extension);
                }
                break;
        }

        return storage;
    }

    static protected String getFilename(Map<String, String> params)
    {
        return params.get(PARAM_FILENAME);
    }

    static protected String getExtension(Map<String, String> params)
    {
        String extension = "txt";
        if (params.containsKey(PARAM_EXTENSION)) {
            extension = params.get(PARAM_EXTENSION);
        }

        return extension;
    }

    static protected String getDirectory(Map<String, String> params)
    {
        String directory = "/";
        if (params.containsKey(PARAM_DIRECTORY)) {
            directory = params.get(PARAM_DIRECTORY);
        }

        return directory;
    }

    public enum Type
    {
        File,
        Memcache,
        MemcacheFile,
    }
}
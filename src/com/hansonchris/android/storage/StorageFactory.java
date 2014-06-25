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
                    filename = params.get(PARAM_FILENAME);
                    extension = params.get(PARAM_EXTENSION);
                    directory = params.get(PARAM_DIRECTORY);
                    storage = new StorageFile(context, directory, filename, extension);
                }
                break;
            case Memcache:
                storage = new StorageMemcache();
                break;
            case MemcacheFile:
                if (params != null) {
                    filename = params.get(PARAM_FILENAME);
                    extension = params.get(PARAM_EXTENSION);
                    directory = params.get(PARAM_DIRECTORY);
                    storage = new StorageMemcacheFile(context, directory, filename, extension);
                }
                break;
        }

        return storage;
    }

    public enum Type
    {
        File,
        Memcache,
        MemcacheFile,
    }
}
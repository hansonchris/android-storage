package com.hansonchris.android.storage;

import android.content.Context;

public class StorageFactory
{
    static public StorageInterface getStorage(
        Context context,
        StorageFactory.Type type
    ) {
        return getStorage(context, type, new String[] {});
    }

    static public StorageInterface getStorage(
        Context context,
        StorageFactory.Type type,
        String... params
    ) {
        StorageInterface storage = null;
        String directory;
        String filename;
        String extension;
        switch (type) {
            case File:
                if (params.length == 2) {
                    filename = params[0];
                    extension = params[1];
                    storage = new StorageFile(context, filename, extension);
                } else if (params.length >= 3) {
                    directory = params[0];
                    filename = params[1];
                    extension = params[2];
                    storage = new StorageFile(context, directory, filename, extension);
                }
                break;
            case Memcache:
                storage = new StorageMemcache();
                break;
            case MemcacheFile:
                if (params.length == 2) {
                    filename = params[0];
                    extension = params[1];
                    storage = new StorageMemcacheFile(context, filename, extension);
                } else if (params.length >= 3) {
                    directory = params[0];
                    filename = params[1];
                    extension = params[2];
                    storage =
                        new StorageMemcacheFile(context, directory, filename, extension);
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
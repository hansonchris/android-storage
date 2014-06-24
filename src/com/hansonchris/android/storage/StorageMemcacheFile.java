package com.hansonchris.android.storage;

import android.content.Context;

public class StorageMemcacheFile implements StorageInterface
{
    protected StorageMemcache memcache;
    protected StorageFile file;
    protected Context context;

    public StorageMemcacheFile(Context context, String filename, String extension)
    {
        this.context = context;
        file = getFileStorage(filename, extension);
        memcache = getMemcacheStorage();
    }

    public StorageMemcacheFile(
        Context context,
        String directory,
        String filename,
        String extension
    ) {
        file = getFileStorage(context, directory, filename, extension);
        memcache = getMemcacheStorage();
    }

    public void clearAll()
    {
        memcache.clearAll();
        file.clearAll();
    }

    protected StorageFile getFileStorage(String filename, String extension)
    {
        return new StorageFile(context, filename, extension);
    }

    protected StorageFile getFileStorage(
        Context context,
        String directory,
        String filename,String extension
    ) {
        return new StorageFile(context, directory, filename, extension);
    }

    protected StorageMemcache getMemcacheStorage()
    {
        return new StorageMemcache();
    }

    public boolean containsKey(String key)
    {
        return (memcache.containsKey(key) || file.containsKey(key));
    }

    public Object get(String key)
    {
        Object object = null;
        if (memcache.containsKey(key)) {
            object = memcache.get(key);
        } else if (file.containsKey(key)) {
            object = file.get(key);
        }

        return object;
    }

    public boolean getBoolean(String key)
    {
        boolean value = false;
        if (memcache.containsKey(key)) {
            value = memcache.getBoolean(key);
        } else if (file.containsKey(key)) {
            value = file.getBoolean(key);
            memcache.put(key, value);
        }

        return value;
    }

    public byte getByte(String key)
    {
        byte value = 0;
        if (memcache.containsKey(key)) {
            value = memcache.getByte(key);
        } else if (file.containsKey(key)) {
            value = file.getByte(key);
            memcache.put(key, value);
        }

        return value;
    }

    public double getDouble(String key)
    {
        double value = 0;
        if (memcache.containsKey(key)) {
            value = memcache.getDouble(key);
        } else if (file.containsKey(key)) {
            value = file.getDouble(key);
            memcache.put(key, value);
        }

        return value;
    }

    public float getFloat(String key)
    {
        float value = 0;
        if (memcache.containsKey(key)) {
            value = memcache.getFloat(key);
        } else if (file.containsKey(key)) {
            value = file.getFloat(key);
            memcache.put(key, value);
        }

        return value;
    }

    public int getInt(String key)
    {
        int value = 0;
        if (memcache.containsKey(key)) {
            value = memcache.getInt(key);
        } else if (file.containsKey(key)) {
            value = file.getInt(key);
            memcache.put(key, value);
        }

        return value;
    }

    public long getLong(String key)
    {
        long value = 0;
        if (memcache.containsKey(key)) {
            value = memcache.getLong(key);
        } else if (file.containsKey(key)) {
            value = file.getLong(key);
            memcache.put(key, value);
        }

        return value;
    }

    public String getString(String key)
    {
        String value = null;
        if (memcache.containsKey(key)) {
            value = memcache.getString(key);
        } else if (file.containsKey(key)) {
            value = file.getString(key);
            memcache.put(key, value);
        }

        return value;
    }

    public void put(String key, byte value)
    {
        memcache.put(key, value);
        file.put(key, value);
    }

    public void put(String key, int value)
    {
        memcache.put(key, value);
        file.put(key, value);
    }

    public void put(String key, long value)
    {
        memcache.put(key, value);
        file.put(key, value);
    }

    public void put(String key, boolean value)
    {
        memcache.put(key, value);
        file.put(key, value);
    }

    public void put(String key, float value)
    {
        memcache.put(key, value);
        file.put(key, value);
    }

    public void put(String key, double value)
    {
        memcache.put(key, value);
        file.put(key, value);
    }

    public void put(String key, String value)
    {
        memcache.put(key, value);
        file.put(key, value);
    }

    public long getLastModified()
    {
        long lastModified;
        long memcacheLastModified = memcache.getLastModified();
        if (memcacheLastModified > 0) {
            lastModified = memcacheLastModified;
        } else {
            lastModified = file.getLastModified();
        }

        return lastModified;
    }
}
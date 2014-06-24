package com.hansonchris.android.storage;

public interface StorageInterface
{
    public boolean containsKey(String key);

    public void clearAll();

    public void put(String key, byte value);

    public void put(String key, int value);

    public void put(String key, long value);

    public void put(String key, boolean value);

    public void put(String key, float value);

    public void put(String key, double value);

    public void put(String key, String value);

    public Object get(String key);

    public byte getByte(String key);

    public int getInt(String key);

    public long getLong(String key);

    public boolean getBoolean(String key);

    public float getFloat(String key);

    public double getDouble(String key);

    public String getString(String key);

    public long getLastModified();
}
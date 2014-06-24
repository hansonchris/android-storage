package com.hansonchris.android.storage;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StorageMemcache implements StorageInterface
{
    volatile protected Map<String, Object> map;
    volatile protected long lastModified;

    public StorageMemcache()
    {
        map = getMap();
        lastModified = 0;
    }

    protected Map<String, Object> getMap()
    {
        return new HashMap<String, Object>();
    }

    synchronized public boolean containsKey(String key)
    {
        return (map.containsKey(key));
    }

    synchronized public void clearAll()
    {
        map = null;
        map = getMap();
        updateLastModified();
    }

    synchronized public Object get(String key)
    {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return null;
        }
    }

    synchronized public boolean getBoolean(String key)
    {
        if (map.containsKey(key)) {
            Object value = map.get(key);
            try {
                return (Boolean)value;
            } catch (Exception e) {}
        }

        return false;
    }

    synchronized public byte getByte(String key)
    {
        if (map.containsKey(key)) {
            Object value = map.get(key);
            try {
                return (Byte)value;
            } catch (Exception e) {}
        }

        return 0;
    }

    synchronized public double getDouble(String key)
    {
        if (map.containsKey(key)) {
            Object value = map.get(key);
            try {
                return (Double)value;
            } catch (Exception e) {}
        }

        return 0;
    }

    synchronized public float getFloat(String key)
    {
        if (map.containsKey(key)) {
            Object value = map.get(key);
            try {
                return (Float)value;
            } catch (Exception e) {}
        }

        return 0;
    }

    synchronized public int getInt(String key)
    {
        if (map.containsKey(key)) {
            Object value = map.get(key);
            try {
                return (Integer)value;
            } catch (Exception e) {}
        }

        return 0;
    }

    synchronized public long getLong(String key)
    {
        if (map.containsKey(key)) {
            Object value = map.get(key);
            try {
                return (Long)value;
            } catch (Exception e) {}
        }

        return 0;
    }

    synchronized public String getString(String key)
    {
        if (map.containsKey(key)) {
            Object value = map.get(key);
            try {
                return (String)value;
            } catch (Exception e) {}
        }

        return null;
    }

    synchronized public void put(String key, byte value)
    {
        map.put(key, value);
        updateLastModified();
    }

    synchronized public void put(String key, int value)
    {
        map.put(key, value);
        updateLastModified();
    }

    synchronized public void put(String key, long value)
    {
        map.put(key, value);
        updateLastModified();
    }

    synchronized public void put(String key, boolean value)
    {
        map.put(key, value);
        updateLastModified();
    }

    synchronized public void put(String key, float value)
    {
        map.put(key, value);
        updateLastModified();
    }

    synchronized public void put(String key, double value)
    {
        map.put(key, value);
        updateLastModified();
    }

    synchronized public void put(String key, String value)
    {
        map.put(key, value);
        updateLastModified();
    }

    protected void updateLastModified()
    {
        lastModified = Calendar.getInstance().getTimeInMillis();
    }

    synchronized public long getLastModified()
    {
        return lastModified;
    }
}
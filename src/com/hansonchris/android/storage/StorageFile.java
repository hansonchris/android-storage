package com.hansonchris.android.storage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StorageFile implements StorageInterface
{
    protected String filename;
    protected String extension;
    protected String directory;
    volatile protected String lastKeyRead;
    volatile protected String lastValueRead;
    volatile protected long lastModified;
    protected int bufferSize;
    protected Context context;
    protected static int DEFAULT_BUFFER_SIZE = 8 * 1024;
    protected static final String DELIMITER = "\t";

    public StorageFile(Context context, String directory, String filename, String extension)
    {
        this.filename = filename;
        this.extension = extension;
        this.context = context;
        this.directory = directory;
        setUpPersistentStorageFile();
    }

    synchronized public void setBufferSize(int bytes)
    {
        if (bytes > 0) {
            bufferSize = bytes;
        }
    }

    protected void setUpPersistentStorageFile()
    {
        File file = getFile();
        lastModified = file.lastModified();
        bufferSize = DEFAULT_BUFFER_SIZE;
    }

    synchronized public boolean containsKey(String key)
    {
        if (key.equals(lastKeyRead)) {
            return true;
        } else {
            String value = read(key);

            return (value != null);
        }
    }

    synchronized public void clearAll()
    {
        File file = getFile();
        if (file != null && file.exists()) {
            file.delete();
            lastModified = Calendar.getInstance().getTimeInMillis();
        }
    }

    synchronized public void clear(String key)
    {
        deleteLinesWithKey(key);
    }

    synchronized public Object get(String key)
    {
        return read(key);
    }

    synchronized public boolean getBoolean(String key)
    {
        boolean value = false;
        if (containsKey(key)) {
            if (key.equals(lastKeyRead)) {
                value = Boolean.parseBoolean(lastValueRead);
            } else {
                String tempValue = read(key);
                value = Boolean.parseBoolean(tempValue);
            }
        }

        return value;
    }

    synchronized public double getDouble(String key)
    {
        double value = 0;
        if (containsKey(key)) {
            if (key.equals(lastKeyRead)) {
                value = Double.parseDouble(lastValueRead);
            } else {
                String tempValue = read(key);
                value = Double.parseDouble(tempValue);
            }
        }

        return value;
    }

    synchronized public float getFloat(String key)
    {
        float value = 0;
        if (containsKey(key)) {
            if (key.equals(lastKeyRead)) {
                value = Float.parseFloat(lastValueRead);
            } else {
                String tempValue = read(key);
                value = Float.parseFloat(tempValue);
            }
        }

        return value;
    }

    synchronized public byte getByte(String key)
    {
        byte value = 0;
        if (containsKey(key)) {
            if (key.equals(lastKeyRead)) {
                value = Byte.parseByte(lastValueRead);
            } else {
                String tempValue = read(key);
                value = Byte.parseByte(tempValue);
            }
        }

        return value;
    }

    synchronized public int getInt(String key)
    {
        int value = 0;
        if (containsKey(key)) {
            if (key.equals(lastKeyRead)) {
                value = Integer.parseInt(lastValueRead);
            } else {
                String tempValue = read(key);
                value = Integer.parseInt(tempValue);
            }
        }

        return value;
    }

    synchronized public long getLong(String key)
    {
        long value = 0;
        if (containsKey(key)) {
            if (key.equals(lastKeyRead)) {
                value = Long.parseLong(lastValueRead);
            } else {
                String tempValue = read(key);
                value = Long.parseLong(tempValue);
            }
        }

        return value;
    }

    synchronized public String getString(String key)
    {
        String value = null;
        if (containsKey(key)) {
            if (key.equals(lastKeyRead)) {
                value = lastValueRead;
            } else {
                value = read(key);
            }
        }

        return value;
    }

    synchronized public void put(String key, byte value)
    {
        write(key, value);
    }

    synchronized public void put(String key, int value)
    {
        write(key, value);
    }

    synchronized public void put(String key, long value)
    {
        write(key, value);
    }

    synchronized public void put(String key, boolean value)
    {
        write(key, value);
    }

    synchronized public void put(String key, float value)
    {
        write(key, value);
    }

    synchronized public void put(String key, double value)
    {
        write(key, value);
    }

    synchronized public void put(String key, String value)
    {
        write(key, value);
    }

    protected String read(String key)
    {
        BufferedReader reader = getReader();
        String value = null;
        if (reader != null) {
            String tempValue;
            try {
                tempValue = reader.readLine();
                while (tempValue != null) {
                    String[] parts = tempValue.split(DELIMITER);
                    if (parts.length != 3) {
                        break;
                    }
                    String currentKey = parts[0];
                    if (key.equals(currentKey)) {
                        value = parts[1];
                        lastKeyRead = currentKey;
                        lastValueRead = value;
                    }
                    tempValue = reader.readLine();
                }
            } catch (IOException e) {e.printStackTrace();}
        }

        return value;
    }

    protected void deleteLinesWithKey(String key)
    {
        List<String> lines = new ArrayList<String>();
        BufferedReader reader = getReader();
        if (reader != null) {
            String lineValue;
            try {
                lineValue = reader.readLine();
                while (lineValue != null) {
                    String[] parts = lineValue.split(DELIMITER);
                    if (parts.length != 3) {
                        break;
                    }
                    String currentKey = parts[0];
                    if (!key.equals(currentKey)) {
                        lines.add(lineValue);
                    }
                    lineValue = reader.readLine();
                }
            } catch (IOException e) {e.printStackTrace();}
        }
        File file = getFile();
        file.delete();
        file = getFile();
        try {
            Writer writer = getWriter(file);
            for (String line : lines) {
                writer.write(line + "\n");
            }
            writer.flush();
            writer.close();
            lastKeyRead = null;
            lastValueRead = null;
            lastModified = file.lastModified();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected BufferedReader getReader()
    {
        BufferedReader bufferedReader = null;
        Reader reader = getFileReader();
        if (reader != null) {
            bufferedReader = new BufferedReader(getFileReader(), bufferSize);
        }

        return bufferedReader;
    }

    protected Reader getFileReader()
    {
        Reader reader = null;
        try {
            reader = new FileReader(getFile());
        } catch (FileNotFoundException e) {}

        return reader;
    }

    protected boolean write(String key, Object value)
    {
        deleteLinesWithKey(key);
        File file = getFile();
        try {
            Writer writer = getWriter(file);
            writer.write(key + DELIMITER + String.valueOf(value) + DELIMITER +
                Calendar.getInstance().getTimeInMillis() + "\n");
            writer.flush();
            writer.close();
            lastKeyRead = null;
            lastValueRead = null;
            lastModified = file.lastModified();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    protected Writer getWriter(File file)
    {
        try {
            return new FileWriter(file, true);
        } catch (IOException e) {
            return null;
        }
    }

    protected File getDirectory()
    {
        String directoryPath = getFileDirectory();
        File directory = getFile(directoryPath);
        directory.mkdir();

        return directory;
    }

    protected File getFile()
    {
        return getFile(getDirectory(), getFileName(), getFileExtension());
    }

    protected File getFile(String path)
    {
        return new File(path);
    }

    protected File getFile(File directory, String path, String extension)
    {
        File file = new File(directory, path + "." + extension);

        return file;
    }

    protected String getFileDirectory()
    {
        StringBuilder pathBuilder = getStringBuilder();
        File sdDir = context.getFilesDir();
        pathBuilder.append(sdDir.getPath() + "/");
        if (directory != null) {
            pathBuilder.append(directory + "/");
        }

        return pathBuilder.toString();
    }

    protected StringBuilder getStringBuilder()
    {
        return new StringBuilder();
    }

    protected String getFileName()
    {
        return filename;
    }

    protected String getFileExtension()
    {
        return extension;
    }

    synchronized public long getLastModified()
    {
        return lastModified;
    }
}
# android-storage

* Provides simple interface for saving key / value pairs
* Three types of storage:
  1. Memcache - stored in memory, very fast, available only while app is running
  1. File - stored on disk, a little slower, persists when app is not running, even after device reboot
  1. Memcache / File - combination of the above two, provides speed and persistence

## How to use

1. First, import the following:
```java
import com.hansonchris.android.storage.StorageFactory;
import com.hansonchris.android.storage.StorageInterface;
```
1. Get reference to storage interface using `com.hansonchris.android.storage.StorageFactory`:
```java
StorageInterface memcacheStorage = StorageFactory.getStorage(this, StorageFactory.Type.Memcache);
```
1. Save values:
```java
memcacheStorage.put("isNewUser", true);
```
1. Get values:
```java
boolean isNewUser = memcacheStorage.getBoolean("isNewUser");
```

The interface is similar to the familiar `android.os.Bundle` class, but a  little simpler:
```java
public boolean containsKey(String key);
public void clearAll();
public void clear(String key);
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
```

## Code samples
#### StorageMemcache
```java
//Usage of StorageMemcache - this data will only live while the app is running.
//Access will be very fast.
StorageInterface memcacheStorage = StorageFactory.getStorage(this, StorageFactory.Type.Memcache);
memcacheStorage.put("isJedi", true);
memcacheStorage.put("name", "Luke");
memcacheStorage.put("mass", 77);
memcacheStorage.put("height", 1.72f);
memcacheStorage.put("homePlanet", "Tatooine");

Log.i(TAG, "memcacheStorage isJedi: " + memcacheStorage.getBoolean("isJedi"));
Log.i(TAG, "memcacheStorage name: " + memcacheStorage.getString("name"));
Log.i(TAG, "memcacheStorage mass: " + memcacheStorage.getInt("mass"));
Log.i(TAG, "memcacheStorage height: " + memcacheStorage.getFloat("height"));
Log.i(TAG, "memcacheStorage homePlanet: " + memcacheStorage.get("homePlanet"));
```
###### Output:
```
memcacheStorage isJedi: true
memcacheStorage name: Luke
memcacheStorage mass: 77
memcacheStorage height: 1.72
memcacheStorage homePlanet: Tatooine
```
#### StorageFile
```java
//Usage of StorageFile - this data will only persist through device reboots.
//Access will be slower than with StorageMemcache
Map<String, String> fileParams = new HashMap<String, String>();
StorageInterface fileStorage = StorageFactory.getStorage(this, StorageFactory.Type.File, fileParams);
fileStorage.put("isJedi", true);
fileStorage.put("name", "Yoda");
fileStorage.put("mass", 17);
fileStorage.put("height", 0.66f);
fileStorage.put("homePlanet", "Dagobah");

Log.i(TAG, "fileStorage isJedi: " + fileStorage.getBoolean("isJedi"));
Log.i(TAG, "fileStorage name: " + fileStorage.getString("name"));
Log.i(TAG, "fileStorage mass: " + fileStorage.getInt("mass"));
Log.i(TAG, "fileStorage height: " + fileStorage.getFloat("height"));
Log.i(TAG, "fileStorage homePlanet: " + fileStorage.get("homePlanet"));
```
###### Output:
```
fileStorage isJedi: true
fileStorage name: Yoda
fileStorage mass: 17
fileStorage height: 0.66
fileStorage homePlanet: Dagobah
```
#### StorageMemcacheFile
```java
//Usage of StorageMemcacheFile - this data will persist through device reboots.
//Reads will be as fast as StorageMemcache, writes will be as fast as StorageFile
Map<String, String> memcacheFileParams = new HashMap<String, String>();
StorageInterface memcacheFileStorage = StorageFactory.getStorage(this, StorageFactory.Type.MemcacheFile, memcacheFileParams);
memcacheFileStorage.put("isJedi", false);
memcacheFileStorage.put("name", "R2D2");
memcacheFileStorage.put("mass", 32);
memcacheFileStorage.put("height", 0.96f);
memcacheFileStorage.put("homePlanet", "Naboo");

Log.i(TAG, "memcacheFileStorage isJedi: " + memcacheFileStorage.getBoolean("isJedi"));
Log.i(TAG, "memcacheFileStorage name: " + memcacheFileStorage.getString("name"));
Log.i(TAG, "memcacheFileStorage mass: " + memcacheFileStorage.getInt("mass"));
Log.i(TAG, "memcacheFileStorage height: " + memcacheFileStorage.getFloat("height"));
Log.i(TAG, "memcacheFileStorage homePlanet: " + memcacheFileStorage.get("homePlanet"));
```
###### Output:
```
memcacheFileStorage isJedi: false
memcacheFileStorage name: R2D2
memcacheFileStorage mass: 32
memcacheFileStorage height: 0.96
memcacheFileStorage homePlanet: Naboo
```

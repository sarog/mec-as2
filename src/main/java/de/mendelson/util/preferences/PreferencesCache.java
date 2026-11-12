//$Header: /as2/de/mendelson/util/preferences/PreferencesCache.java 9     22/05/25 9:05 Heller $
package de.mendelson.util.preferences;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Copyright (C) mendelson-e-commerce GmbH Berlin Germany
 *
 * This software is subject to the license agreement set forth in the license.
 * Please read and agree to all terms before using this software.
 * Other product and brand names are trademarks of their respective owners.
 */
/**
 * Cache for the server preferences to prevent database access
 *
 * @author S.Heller
 * @version $Revision: 9 $
 */
public class PreferencesCache {

    private final Cache<String, String> CACHE;

    private static PreferencesCache instance;

    private final AtomicInteger accessCount = new AtomicInteger(0);

    private PreferencesCache() {
        this.CACHE = Caffeine.newBuilder()
                //.recordStats()
                .expireAfterWrite(15, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Singleton for the whole application
     */
    public static synchronized PreferencesCache instance() {
        if (instance == null) {
            instance = new PreferencesCache();
        }
        return instance;
    }

    /**
     * Returns the cached value or null if it has been either expired or the
     * value is not cached
     *
     * @param key Preferences key to get the server preferences value for
     * @return
     */
    public String get(String key) {
        //output stats only if the stat mechanism is enabled in the Caffein Builder by .recordStats().
        //The stats will not count if this is not enabled
        if (this.CACHE.stats().hitCount() > 0 && accessCount.incrementAndGet() % 100 == 0) {
            System.out.println(this.getStats());
        }
        return CACHE.getIfPresent(key);
    }

    /**
     * Adds a new key value pair to the cache
     */
    public void put(String key, String value) {
        this.CACHE.put(key, value);
    }

    /**
     * Removes a key from the cache
     */
    public void remove(String key) {
        this.CACHE.invalidate(key);
    }

    /**
     * Clear the cache. This might be required in HA mode if there are multiple
     * nodes working on the same preferences and a request needs to get the
     * current stored value in the database
     */
    public void clear() {
        this.CACHE.invalidateAll();
    }

    public String getStats() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Preferences Cache Statistics:\n");
        stringBuilder.append("  Hit Count        : ").append(CACHE.stats().hitCount()).append('\n');
        stringBuilder.append("  Miss Count       : ").append(CACHE.stats().missCount()).append('\n');
        stringBuilder.append("  Eviction Count   : ").append(CACHE.stats().evictionCount()).append('\n');
        stringBuilder.append("  Eviction Weight  : ").append(CACHE.stats().evictionWeight()).append('\n');
        stringBuilder.append("  Hit Rate         : ").append(String.format("%.2f%%", CACHE.stats().hitRate() * 100)).append('\n');
        stringBuilder.append("  Miss Rate        : ").append(String.format("%.2f%%", CACHE.stats().missRate() * 100)).append('\n');
        return (stringBuilder.toString());
    }

}

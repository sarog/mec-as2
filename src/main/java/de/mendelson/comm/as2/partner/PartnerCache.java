//$Header: /as2/de/mendelson/comm/as2/partner/PartnerCache.java 2     22/05/25 9:05 Heller $
package de.mendelson.comm.as2.partner;

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
 * Cache for the partner access
 *
 * @author S.Heller
 * @version $Revision: 2 $
 */
public class PartnerCache {

    private final Cache<String, Partner> CACHE;

    public static final String AS2ID = "as2id:";
    public static final String NAME = "name:";
    public static final String DBID = "dbid:";

    private static PartnerCache instance;

    private final AtomicInteger accessCount = new AtomicInteger(0);

    private PartnerCache() {
        this.CACHE = Caffeine.newBuilder()
                //.recordStats()
                .expireAfterWrite(15, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Singleton for the whole application
     */
    public static synchronized PartnerCache instance() {
        if (instance == null) {
            instance = new PartnerCache();
        }
        return instance;
    }

    public void put(Partner partner) {
        if (partner != null) {
            this.CACHE.put(AS2ID + partner.getAS2Identification(), partner);
            this.CACHE.put(NAME + partner.getName(), partner);
            if (partner.getDBId() != -1) {
                this.CACHE.put(DBID + String.valueOf(partner.getDBId()), partner);
            }            
        }
    }


    public Partner get(final String CATEGORY, String id) {
        //output stats only if the stat mechanism is enabled in the Caffein Builder by .recordStats().
        //The stats will not count if this is not enabled
        if (this.CACHE.stats().hitCount() > 0 && accessCount.incrementAndGet() % 100 == 0) {
            System.out.println(this.getStats());
        }
        return CACHE.getIfPresent(CATEGORY + id);
    }

    public void remove(Partner partner) {
        this.CACHE.invalidate(AS2ID + partner.getAS2Identification());
        this.CACHE.invalidate(NAME + partner.getName());
        if (partner.getDBId() != -1) {
            this.CACHE.invalidate(DBID + String.valueOf(partner.getDBId()));
        }
    }

    public void clear() {
        CACHE.invalidateAll();
    }

    public String getStats() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Partner Cache Statistics:\n");
        stringBuilder.append("  Hit Count        : ").append(CACHE.stats().hitCount()).append('\n');
        stringBuilder.append("  Miss Count       : ").append(CACHE.stats().missCount()).append('\n');
        stringBuilder.append("  Eviction Count   : ").append(CACHE.stats().evictionCount()).append('\n');
        stringBuilder.append("  Eviction Weight  : ").append(CACHE.stats().evictionWeight()).append('\n');
        stringBuilder.append("  Hit Rate         : ").append(String.format("%.2f%%", CACHE.stats().hitRate() * 100)).append('\n');
        stringBuilder.append("  Miss Rate        : ").append(String.format("%.2f%%", CACHE.stats().missRate() * 100)).append('\n');
        return (stringBuilder.toString());
    }

}

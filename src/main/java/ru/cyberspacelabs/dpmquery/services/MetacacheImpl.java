package ru.cyberspacelabs.dpmquery.services;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.cyberspacelabs.darkplaces.GameServer;
import ru.cyberspacelabs.dpmquery.Endpoint;
import ru.cyberspacelabs.dpmquery.contracts.Metacache;
import ru.cyberspacelabs.dpmquery.contracts.metacache.MetacacheEntry;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by mzakharov on 19.06.17.
 */
@Service("metacache")
public class MetacacheImpl implements Metacache {
    private static Logger logger = LoggerFactory.getLogger(MetacacheImpl.class);

    @Value("${metacache.limit:500}")
    private long maxSize;

    @Value("${metacache.expires:120}")
    private long lifetime;

    @Value("${metacache.entry.limit:500}")
    private long entryMaxSize;

    @Value("${metacache.entry.expires:30}")
    private long entryLifetime;

    private Cache<String, MetacacheEntry> metacache;

    public MetacacheImpl(){
        metacache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(300, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public Set<GameServer> refreshAndCache(String master, String query, String game) throws ExecutionException {
        MetacacheEntry metacacheEntry = metacache.get(MetacacheEntry.addressAndQueryToKey(master, query),
                () -> createMetacacheEntry(master, query, game));
        return metacacheEntry.refresh(null);
    }

    @Override
    public Set<GameServer> refreshAndCache(String master, String query, String game, List<Endpoint> pinnedServers) throws ExecutionException{
        MetacacheEntry metacacheEntry = metacache.get(MetacacheEntry.addressAndQueryToKey(master, query),
                () -> createMetacacheEntry(master, query, game));
        return metacacheEntry.refresh(pinnedServers);
    }

    private MetacacheEntry createMetacacheEntry(String master, String query, String game) {
        logger.info("Metacache missing for {} with query \"{}\" for game \"{}\"; Creating ...", master, query, game);
        MetacacheEntry result = new MetacacheEntry(master, query, game);
        result.setLifetime(entryLifetime);
        result.setMaxSize(entryMaxSize);
        result.afterWire();
        return result;
    }

    @PostConstruct
    public void afterWire() {
        metacache = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterAccess(lifetime, TimeUnit.SECONDS)
                .build();
        logger.info("Metacache initialized with capacity {} in lifetime {} sec", maxSize, lifetime);
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
        afterWire();
    }

    public long getLifetime() {
        return lifetime;
    }

    public void setLifetime(long lifetime) {
        this.lifetime = lifetime;
        afterWire();
    }
}

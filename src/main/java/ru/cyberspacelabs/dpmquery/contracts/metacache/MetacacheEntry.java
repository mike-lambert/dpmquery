package ru.cyberspacelabs.dpmquery.contracts.metacache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import ru.cyberspacelabs.darkplaces.GameBrowser;
import ru.cyberspacelabs.darkplaces.GameServer;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by mzakharov on 19.06.17.
 */
public class MetacacheEntry {
    private static Logger logger = LoggerFactory.getLogger(MetacacheEntry.class);

    private String key;
    private GameBrowser discoveryClient;
    private Cache<String, GameServer> cache;
    @Value("${metacache.entry.limit:500}")
    private long maxSize;
    @Value("${metacache.entry.expires:30}")
    private long lifetime;

    public MetacacheEntry(String master, String query, String game){
        String gameId = (game != null && !game.trim().isEmpty() ? game : UUID.randomUUID().toString());
        this.key = addressAndQueryToKey(master, query);
        this.discoveryClient = new GameBrowser(master, query).setGame(gameId);
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();
    }

    public String getKey() {
        return key;
    }

    public GameBrowser getDiscoveryClient() {
        return discoveryClient;
    }

    public Set<GameServer> refresh() {
        Map<String, GameServer> present = this.cache.asMap();
        Set<GameServer> result = Sets.newConcurrentHashSet(present.values());
        if (result.isEmpty()){
            logger.info("{}: cache expired or oversized; refreshing", discoveryClient.getMasterAddress());
            result = this.discoveryClient.refresh();
            result.parallelStream().forEach(server -> cache.put(server.getAddress(), server));
        }
        return result;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public long getLifetime() {
        return lifetime;
    }

    public void setLifetime(long lifetime) {
        this.lifetime = lifetime;
    }

    @PostConstruct
    public void afterWire(){
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(lifetime, TimeUnit.SECONDS)
                .build();
        logger.info("Entry '{}' initialized with capacity {} in lifetime {} sec", key, maxSize, lifetime);
    }

    public static String addressAndQueryToKey(String master, String query){
        return master + "|'" + query + "'";
    }
}

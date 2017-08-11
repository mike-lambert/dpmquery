package ru.cyberspacelabs.dpmquery.contracts;

import ru.cyberspacelabs.darkplaces.GameServer;

import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by mzakharov on 19.06.17.
 */
public interface Metacache {
    Set<GameServer> refreshAndCache(String master, String query) throws ExecutionException;
}

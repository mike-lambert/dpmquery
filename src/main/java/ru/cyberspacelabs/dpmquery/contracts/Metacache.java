package ru.cyberspacelabs.dpmquery.contracts;

import ru.cyberspacelabs.darkplaces.GameServer;
import ru.cyberspacelabs.dpmquery.Endpoint;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by mzakharov on 19.06.17.
 */
public interface Metacache {
    Set<GameServer> refreshAndCache(String master, String query, String game) throws ExecutionException;
    Set<GameServer> refreshAndCache(String masterAddress, String masterQuery, String game, List<Endpoint> pinnedServers) throws ExecutionException;
}

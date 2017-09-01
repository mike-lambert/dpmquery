package ru.cyberspacelabs.dpmquery.contracts;

import ru.cyberspacelabs.darkplaces.GameServer;
import ru.cyberspacelabs.dpmquery.Endpoint;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by mzakharov on 19.06.17.
 */
public interface DiscoveryService {
    Set<GameServer> queryMaster(String masterAddress, String masterQuery, String game) throws Exception;
    Set<GameServer> queryMaster(String master, String query, String game, List<Endpoint> pinnedServers) throws Exception;
}

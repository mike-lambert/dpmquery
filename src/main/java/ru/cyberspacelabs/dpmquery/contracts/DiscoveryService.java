package ru.cyberspacelabs.dpmquery.contracts;

import ru.cyberspacelabs.darkplaces.GameServer;

import java.util.Set;

/**
 * Created by mzakharov on 19.06.17.
 */
public interface DiscoveryService {
    Set<GameServer> queryMaster(String masterAddress, String masterQuery, String game) throws Exception;
}

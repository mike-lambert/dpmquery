package ru.cyberspacelabs.dpmquery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cyberspacelabs.darkplaces.GameBrowser;
import ru.cyberspacelabs.darkplaces.GameServer;
import ru.cyberspacelabs.dpmquery.contracts.DiscoveryService;
import ru.cyberspacelabs.dpmquery.contracts.Metacache;

import java.util.Set;
import java.util.UUID;

/**
 * Created by mzakharov on 19.06.17.
 */
@Service("discoveryService")
public class DiscoveryServiceImpl implements DiscoveryService {
    @Autowired
    private Metacache metacache;

    @Override
    public Set<GameServer> queryMaster(String masterAddress, String masterQuery) throws Exception {
        return metacache.refreshAndCache(masterAddress, masterQuery);
    }
}

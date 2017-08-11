package ru.cyberspacelabs.dpmquery.rest.api.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cyberspacelabs.darkplaces.GameServer;
import ru.cyberspacelabs.dpmquery.contracts.DiscoveryService;

import java.util.Set;

/**
 * Created by mzakharov on 19.06.17.
 */
@Api(value = "/api/v1/master", description = "DarkPlaces Master Server Query operations")
@RestController
@RequestMapping("/api/v1/master")
public class MasterQueryController {
    @Autowired
    private DiscoveryService discoveryService;

    @ApiOperation(value = "Query Master by given endpoint and caches results",
            response = GameServer.class,
            responseContainer = "Set",
            httpMethod = "GET",
            produces = "application/json"
    )
    @RequestMapping("/query/{address}/{query}")
    Set<GameServer> query(
            @ApiParam(value = "Master server UDP endpoint in format \"host:port\", e.g \"dpmaster.deathmask.net:27950\"", required = true)
            @PathVariable("address")
            String master,

            @ApiParam(value = "Query according to game, e.g. \"getservers 68 empty full\" for Quake 3 Arena", required = true)
            @PathVariable("query") String query
    ) throws Exception {
        return discoveryService.queryMaster(master, query);
    }
}

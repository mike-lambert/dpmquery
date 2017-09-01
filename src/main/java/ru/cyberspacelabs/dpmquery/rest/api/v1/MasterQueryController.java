package ru.cyberspacelabs.dpmquery.rest.api.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.cyberspacelabs.collections.ComparatorFactory;
import ru.cyberspacelabs.collections.ServerPingComparator;
import ru.cyberspacelabs.darkplaces.GameServer;
import ru.cyberspacelabs.dpmquery.QueryRequest;
import ru.cyberspacelabs.dpmquery.contracts.DiscoveryService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            responseContainer = "List",
            httpMethod = "GET",
            produces = "application/json"
    )
    @RequestMapping(value = "/query/{address}/{query}", method = RequestMethod.GET)
    List<GameServer> query(
            @ApiParam(value = "Master server UDP endpoint in format \"host:port\", e.g \"dpmaster.deathmask.net:27950\"", required = true)
            @PathVariable("address")
            String master,

            @ApiParam(value = "Query according to game, e.g. \"getservers 68 empty full\" for Quake 3 Arena", required = true)
            @PathVariable("query") String query,

            @ApiParam("Optional parameter for filling \"game\" field; by default - UUID, assigned to request")
            @RequestParam(value = "game", required = false) String game,

            @ApiParam("Optional parameter for applying sorting. E.g. \"ping|load|capacity\"")
            @RequestParam(value = "sort", required = false) String sort,

            @ApiParam("Optional parameter for limiting results. E.g. 10 (TOP-10)")
            @RequestParam(value = "limit", required = false) Integer limit,

            @ApiParam("Optional parameter for cutting servers, which ping more than pointed. Default 300 msec")
            @RequestParam(value = "maxPing", required = false) Long maxPing
    ) throws Exception {
        long over = maxPing == null ? 300 : maxPing;
        List<GameServer> results = new ArrayList<>();
        results.addAll(discoveryService.queryMaster(master, query, game));

        results = results.stream().filter(s -> s.getRequestDuration() < over).collect(Collectors.toList());

        if (sort != null && !sort.trim().isEmpty()){
            Collections.sort(results, ComparatorFactory.create(sort));
        }

        if (limit != null && limit > 0){
            results = results.subList(0, Math.min(limit, results.size()));
        }

        return results;

    }

    @ApiOperation(value = "Query Master by given endpoint, include status of pinned servers, and caches results",
            response = GameServer.class,
            responseContainer = "List",
            httpMethod = "POST",
            produces = "application/json",
            consumes = "application/json"
    )
    @RequestMapping(value = "/query/{address}/{query}", method = RequestMethod.POST)
    List<GameServer> query(
            @ApiParam(value = "Master server UDP endpoint in format \"host:port\", e.g \"dpmaster.deathmask.net:27950\"", required = true)
            @PathVariable("address")
            String master,

            @ApiParam(value = "Query according to game, e.g. \"getservers 68 empty full\" for Quake 3 Arena", required = true)
            @PathVariable("query") String query,

            @ApiParam(value = "Structure to handle complex parameters of query", required = true)
            @RequestBody
            @NotNull
            QueryRequest request
    ) throws Exception {
        long over = request.getMaxPing() == 0 ? 300 : request.getMaxPing();
        List<GameServer> results = new ArrayList<>();
        results.addAll(discoveryService.queryMaster(master, query, request.getGame(), request.getPinnedServers()));

        results = results.stream().filter(s -> s.getRequestDuration() < over).collect(Collectors.toList());

        if (request.getSort() != null && !request.getSort().trim().isEmpty()){
            Collections.sort(results, ComparatorFactory.create(request.getSort()));
        }

        if (request.getLimit() > 0){
            results = results.subList(0, Math.min(request.getLimit(), results.size()));
        }

        return results;

    }
}

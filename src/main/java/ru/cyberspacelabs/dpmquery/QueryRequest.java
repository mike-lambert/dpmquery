package ru.cyberspacelabs.dpmquery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 01.09.17.
 */
@ApiModel(description = "Class to encapsulate query details")
public class QueryRequest {
    private long maxPing;
    private String game;
    private String sort;
    private int limit;
    private List<Endpoint> pinnedServers;

    public QueryRequest(){
        pinnedServers = new ArrayList<>();
    }

    @ApiModelProperty(value = "Limit ping threshold for cutting off laggy or faraway servers", dataType = "long")
    public long getMaxPing() {
        return maxPing;
    }

    @ApiModelProperty(value = "String to fill \"game\" field of server", dataType = "string")
    public String getGame() {
        return game;
    }

    @ApiModelProperty(value = "String, representing sort order, e.g. \"ping|load|capacity\"", dataType = "string")
    public String getSort() {
        return sort;
    }

    @ApiModelProperty(value = "Limit count of discovered servers, not include pinned", dataType = "int")
    public int getLimit() {
        return limit;
    }

    public void setMaxPing(long maxPing) {
        this.maxPing = maxPing;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @ApiModelProperty(value = "Define pinned servers, which status will appear even if server offline", dataType = "java.util.List<ru.cyberspacelabs.dpmquery.Endpoint>")
    public List<Endpoint> getPinnedServers() {
        return pinnedServers;
    }

    public void setPinnedServers(List<Endpoint> pinnedServers) {
        this.pinnedServers = pinnedServers;
    }
}

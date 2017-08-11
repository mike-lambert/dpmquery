package ru.cyberspacelabs.darkplaces;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by mzakharov on 31.01.17.
 */
@ApiModel(
        description = "Contains info about server, which hosts particular game session"
)
public class GameServer {
    private String address;
    private int serverProtocol;
    private String map;
    private int playersPresent;
    private int slotsAvailable;
    private String displayName;
    private String gameType;
    private long requestDuration;
    private String game;

    public GameServer(){
        setRequestDuration(-1);
    }

    @ApiModelProperty(value = "Request duration to server, in milliseconds", dataType = "long", required = true)
    public long getRequestDuration() {
        return requestDuration;
    }

    public void setRequestDuration(long requestDuration) {
        this.requestDuration = requestDuration;
    }

    @ApiModelProperty(value = "Server UDP endpoint, e.g. \"127.0.0.1:27960\"", dataType = "string", required = true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ApiModelProperty(value = "Server protocol level", dataType = "integer", required = true)
    public int getServerProtocol() {
        return serverProtocol;
    }

    public void setServerProtocol(int serverProtocol) {
        this.serverProtocol = serverProtocol;
    }

    @ApiModelProperty(value = "Server map name running", dataType = "string", required = true)
    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    @ApiModelProperty(value = "Connected players count", dataType = "integer", required = true)
    public int getPlayersPresent() {
        return playersPresent;
    }

    public void setPlayersPresent(int playersPresent) {
        this.playersPresent = playersPresent;
    }

    @ApiModelProperty(value = "Maximum players available", dataType = "integer", required = true)
    public int getSlotsAvailable() {
        return slotsAvailable;
    }

    public void setSlotsAvailable(int slotsAvailable) {
        this.slotsAvailable = slotsAvailable;
    }

    @ApiModelProperty(value = "Server name for browsers", dataType = "string", required = true)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @ApiModelProperty(value = "Game type server running (FFA, DM, etc)", dataType = "string", required = true)
    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    @ApiModelProperty(value = "Indicates taht dataset valid", dataType = "boolean", required = true)
    public boolean isValid(){
        return getRequestDuration() >= 0;
    }

    public String getGame() {
        return game;
    }

    @ApiModelProperty(value = "Used for identifying request, which made this result", dataType = "string", required = true)
    public void setGame(String game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameServer that = (GameServer) o;

        if (getServerProtocol() != that.getServerProtocol()) return false;
        return getAddress().equals(that.getAddress());

    }

    @Override
    public int hashCode() {
        int result = getAddress().hashCode();
        result = 31 * result + getServerProtocol();
        return result;
    }
}

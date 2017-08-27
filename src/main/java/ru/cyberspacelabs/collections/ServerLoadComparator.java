package ru.cyberspacelabs.collections;

import ru.cyberspacelabs.darkplaces.GameServer;

import java.util.Comparator;

/**
 * Created by mike on 24.08.17.
 */
public class ServerLoadComparator extends AbstractWeighter<GameServer> implements Comparator<GameServer> {
    @Override
    public int compare(GameServer previous, GameServer current) {
        return previous.getPlayersPresent() < current.getPlayersPresent() ? 1
                : previous.getPlayersPresent() == current.getPlayersPresent() ? 0
                : -1;
    }

    @Override
    public double weight(GameServer object) {
        return object.getPlayersPresent() <= 0 ? factor : factor / object.getPlayersPresent();
    }
}

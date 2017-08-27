package ru.cyberspacelabs.collections;

import ru.cyberspacelabs.darkplaces.GameServer;

import java.util.Comparator;

/**
 * Created by mike on 24.08.17.
 */
public class ServerPingComparator extends AbstractWeighter<GameServer> implements Comparator<GameServer> {
    @Override
    public int compare(GameServer previous, GameServer current) {
        return previous.getRequestDuration() < current.getRequestDuration() ? 1
                : previous.getRequestDuration() == current.getRequestDuration() ? 0
                : -1;
    }

    @Override
    public double weight(GameServer object) {
        return object.getRequestDuration() <= 0 ? 0 : factor * object.getRequestDuration();
    }
}

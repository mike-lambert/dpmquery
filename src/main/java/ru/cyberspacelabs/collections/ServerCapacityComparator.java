package ru.cyberspacelabs.collections;

import ru.cyberspacelabs.darkplaces.GameServer;

import java.util.Comparator;

/**
 * Created by mike on 24.08.17.
 */
public class ServerCapacityComparator extends AbstractWeighter<GameServer> implements Comparator<GameServer> {
    public ServerCapacityComparator(double factor) {
        super(factor);
    }

    @Override
    public int compare(GameServer previous, GameServer current) {
        return previous.getSlotsAvailable() < current.getSlotsAvailable() ? 1
                : previous.getSlotsAvailable() == current.getSlotsAvailable() ? 0
                : -1;
    }

    @Override
    public double weight(GameServer object) {
        return object.getSlotsAvailable() <= 0 ? factor : factor/object.getSlotsAvailable();
    }
}

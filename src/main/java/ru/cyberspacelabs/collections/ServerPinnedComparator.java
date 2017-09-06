package ru.cyberspacelabs.collections;

import ru.cyberspacelabs.darkplaces.GameServer;

import java.util.Comparator;

/**
 * Created by mike on 01.09.17.
 */
public class ServerPinnedComparator extends AbstractWeighter<GameServer> implements Comparator<GameServer> {
    public ServerPinnedComparator(double factor) {
        super(factor);
    }

    @Override
    public int compare(GameServer left, GameServer right) {
        return left.isPinned() && !right.isPinned() ? 1
                : right.isPinned() && !left.isPinned() ? -1
                : 0;
    }

    @Override
    public double weight(GameServer object) {
        return object.isPinned() ? 0 : factor;
    }
}

package ru.cyberspacelabs.collections;

import ru.cyberspacelabs.darkplaces.GameServer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mike on 24.08.17.
 */
public class ComparatorFactory {
    public static final String SORT_RTT = "ping";
    public static final String SORT_CAPACITY = "capacity";
    public static final String SORT_LOAD = "load";
    public static Comparator<GameServer> create(String sort) {
        String[] order = null;
        List<Weight<GameServer>> chain = new ArrayList<>();
        if (sort.contains("|")){
            order = sort.split("\\|");
        } else {
            order = new String[]{sort};
        }
        double exponent = 10.0d;
        double ingress = 5;
        chain.add(new ServerPinnedComparator(Math.pow(exponent, 9)));
        for(int i = 0; i < order.length; i++){
            order[i] = order[i].trim();
            Weight current = null;
            if (SORT_RTT.equals(order[i])){
                current = new ServerPingComparator(Math.pow(exponent, ingress));
            }

            if (SORT_CAPACITY.equals(order[i])){
                current = new ServerCapacityComparator(Math.pow(exponent, ingress));
            }

            if (SORT_LOAD.equals(order[i])){
                current = new ServerLoadComparator(Math.pow(exponent, ingress));
            }

            if (current != null) {
                chain.add(current);
                ingress = ingress - 2;
            }
        }

        return (left, right) -> {
            final int[] weight = {0, 0};
            chain.forEach(c -> {
                weight[0] += c.weight(left);
                weight[1] += c.weight(right);
            });
            return weight[0] < weight[1] ? -1
                    :weight[0] == weight[1] ? 0
                    : 1;
        };
    }
}

package ru.cyberspacelabs.collections;

/**
 * Created by mike on 25.08.17.
 */
public interface Weight<T> {
    double getFactor();
    void setFactor(double factor);
    double weight(T object);
}

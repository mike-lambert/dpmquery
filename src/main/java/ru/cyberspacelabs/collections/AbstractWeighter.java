package ru.cyberspacelabs.collections;

/**
 * Created by mike on 25.08.17.
 */
public abstract class AbstractWeighter<T> implements Weight<T> {
    protected double factor;

    public AbstractWeighter(){
        this(1);
    }

    public AbstractWeighter(double factor){
        setFactor(factor);
    }

    @Override
    public double getFactor() {
        return factor;
    }

    @Override
    public void setFactor(double factor) {
        this.factor = factor;
    }

}

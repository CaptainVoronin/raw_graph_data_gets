package ru.nextbi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс описывает связь вершины с другой вершиной
 * Например:
 * link: must=(bank) or=(person, organisation)
 * говорит, что связь доолна обязятельно быть создана с банком и кроме нее либо с person, либо с organisation
 */
public class Link{
    public enum Condition{ MUST, OR, AND, MAY };

    /**
     * Усдовия содания связли
     */
    Condition condition;

    List<Target> targets;

    public Link( Condition condition )
    {
        this.condition = condition;
        targets = new ArrayList<>();
    }

    public void addTarget( Target target )
    {
        targets.add( target );
    }

    public List<Target> getTargets(){
        return targets;
    }

    public Condition getCondition()
    {
        return condition;
    }

    public class Target
    {
        public String className;
        int min;
        int max;
    }

    public void addTarget( String className, Integer min, Integer max )
    {
        Target t = new Target();
        t.className = className;
        t.min = min;
        t.max = max;
        addTarget( t );
    }
}

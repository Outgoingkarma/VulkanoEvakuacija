package com.example.vulkanoevakuacija.agent;

import com.example.vulkanoevakuacija.strategy.GameContext;
import com.example.vulkanoevakuacija.strategy.MovementStrategy;

public abstract class Agent extends Entity implements Updatable{
    private final int id;
    private MovementStrategy movementStrategy;
    protected Agent(int id, com.example.vulkanoevakuacija.model.Position start, MovementStrategy movementStrategy) {
        super(start);
        this.id = id;
        this.movementStrategy = movementStrategy;
    }
    public int getId() {return id;}
    public MovementStrategy getMovementStrategy() {return movementStrategy;}
    public void setMovementStrategy(MovementStrategy movementStrategy) { this.movementStrategy = movementStrategy; }

    @Override
    public abstract void update(GameContext ctx);
}

package com.example.vulkanoevakuacija.strategy;

import com.example.vulkanoevakuacija.agent.Resident;

public final class  FixedSpeedStrategy implements MovementStrategy {
    private final int steps;
    public FixedSpeedStrategy(int steps) {
        if (steps <= 0) throw new IllegalArgumentException("steps must be positive");
        this.steps = steps;
    }
    @Override
    public int stepsPerTurn(Resident resident, GameContext ctx) {
        return steps;
    }
}

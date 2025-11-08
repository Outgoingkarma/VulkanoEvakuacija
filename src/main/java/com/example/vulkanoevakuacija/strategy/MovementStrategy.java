package com.example.vulkanoevakuacija.strategy;

import com.example.vulkanoevakuacija.agent.Resident;

public interface MovementStrategy {
    int stepsPerTurn(Resident resident, GameContext ctx);
}

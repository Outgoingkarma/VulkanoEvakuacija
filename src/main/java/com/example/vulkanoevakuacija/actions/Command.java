package com.example.vulkanoevakuacija.actions;

import com.example.vulkanoevakuacija.strategy.GameContext;

public interface Command {
    boolean execute(GameContext ctx);
}

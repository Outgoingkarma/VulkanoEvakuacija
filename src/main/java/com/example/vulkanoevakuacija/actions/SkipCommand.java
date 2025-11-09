package com.example.vulkanoevakuacija.actions;

import com.example.vulkanoevakuacija.strategy.GameContext;

public class SkipCommand implements Command {
    @Override
    public boolean execute(GameContext ctx) {
        System.out.println("Skipped turn");
        return true;
    }
}

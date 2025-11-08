package com.example.vulkanoevakuacija.agent;

import com.example.vulkanoevakuacija.model.Position;
import com.example.vulkanoevakuacija.strategy.FixedSpeedStrategy;

public final class SlowResident extends Resident{
    public SlowResident(int id, Position start) {
        super(id, start, new FixedSpeedStrategy(1));
    }
}

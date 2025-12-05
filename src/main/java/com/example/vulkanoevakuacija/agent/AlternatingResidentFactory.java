package com.example.vulkanoevakuacija.agent;

import com.example.vulkanoevakuacija.map.Position;

public class AlternatingResidentFactory implements ResidentFactory{
    @Override
    public Resident createResident(int id, Position position) {
        if (id%2 == 0) {
            return new FastResident(id, position);
        }
        return new SlowResident(id, position);
    }
}

package com.example.vulkanoevakuacija.agent;

import com.example.vulkanoevakuacija.map.Position;

public interface ResidentFactory {
    Resident createResident(int id, Position position);
}

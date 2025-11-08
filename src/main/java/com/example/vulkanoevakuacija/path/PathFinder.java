package com.example.vulkanoevakuacija.path;

import com.example.vulkanoevakuacija.model.GameMap;
import com.example.vulkanoevakuacija.model.Position;

import java.util.Optional;
import java.util.Set;

public interface PathFinder {
    Optional<Path> find(GameMap gameMap, Position start, Set<Position> targets);
}

package com.example.vulkanoevakuacija.path;

import com.example.vulkanoevakuacija.map.GameMap;
import com.example.vulkanoevakuacija.map.Position;

import java.util.Optional;
import java.util.Set;

public interface PathFinder {
    Optional<Path> find(GameMap gameMap, Position start, Set<Position> targets);
}

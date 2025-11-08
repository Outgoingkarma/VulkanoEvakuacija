package com.example.vulkanoevakuacija.path;

import com.example.vulkanoevakuacija.model.GameMap;
import com.example.vulkanoevakuacija.model.Position;

import java.util.*;

public final class BfsPathFinder implements PathFinder{

    @Override
    public Optional<Path> find(GameMap gameMap, Position start, Set<Position> targets) {
        Objects.requireNonNull(gameMap, "gameMap");
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(targets, "targets");
        if (targets.isEmpty()) return Optional.empty();
        if (!gameMap.inBounds(start)) return Optional.empty();
        if (targets.contains(start)) {
            return Optional.of((Path.of(List.of(start))));
        }
        ArrayDeque<Position> queue = new ArrayDeque<>();
        Map<Position, Position> prev = new HashMap<>();
        boolean[][] visited = new boolean[gameMap.rows()][gameMap.cols()];

        visited[start.getRow()][start.getCol()] = true;
        queue.add(start);

        Position foundGoal = null;
        while (!queue.isEmpty()){
            Position current = queue.remove();
            for (Position neighbor : gameMap.neighbors(current)){
                if (visited[neighbor.getRow()][neighbor.getCol()]) continue;
                if (gameMap.getTile(neighbor).isWalkable()) continue;
                if (gameMap.getTile(neighbor).isDangerous()) continue;
                visited[neighbor.getRow()][neighbor.getCol()] = true;
                prev.put(neighbor, current);
                if (targets.contains(neighbor)){
                    foundGoal = neighbor;
                    break;
                }
                queue.add(neighbor);
            }
        }
        if (foundGoal == null) return Optional.empty();
        return Optional.of(reconstructPath(prev, start, foundGoal));
    }
    private Path reconstructPath(Map<Position, Position> prev, Position start, Position goal){
        List<Position> reserved = new ArrayList<>();
        for (Position p = goal; p != null; p = prev.get(p)){
            reserved.add(p);
            if (p.equals(start)) break;
        }
        Collections.reverse(reserved);
        return Path.of(reserved);
    }
}

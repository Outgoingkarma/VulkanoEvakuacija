package com.example.vulkanoevakuacija.strategy;

import com.example.vulkanoevakuacija.map.GameMap;
import com.example.vulkanoevakuacija.map.Position;
import com.example.vulkanoevakuacija.map.TileType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class LavaSpread {
    public int [][] computeArrivalTimes(GameMap gameMap){
        int rows = gameMap.rows(), cols = gameMap.cols();
        int [][] lavaTime = new int[rows][cols];
        for (int r=0; r<rows; r++){
            for (int c=0; c < cols; c++) lavaTime[r][c] = Integer.MAX_VALUE;
        }
        Deque<Position> queue = new ArrayDeque<>();
        for (int r=0; r<rows; r++){
            for (int c=0; c < cols; c++){
                Position p = new Position(r, c);
                if (gameMap.getTile(p).getType() == TileType.LAVA){
                    lavaTime[r][c] = 0;
                    queue.addLast(p);
                }
            }
        }

        while (!queue.isEmpty()){
            Position current = queue.removeFirst();
            int currentTime = lavaTime[current.getRow()][current.getCol()];
            for (Position neighbor : gameMap.neighbors(current)){
                TileType tileType = gameMap.getTile(neighbor).getType();
                boolean spreads = (tileType == TileType.BARRICADE || tileType == TileType.ROAD || tileType == TileType.HOUSE );
                if (!spreads) continue;
                int arrivalTime = currentTime+1;
                if (lavaTime[neighbor.getRow()][neighbor.getCol()] > arrivalTime){
                    lavaTime[neighbor.getRow()][neighbor.getCol()] = arrivalTime;
                    queue.addLast(neighbor);
                }
            }
        }
        return lavaTime;
    }

    public void applyOneStepSpread (GameMap gameMap) {
        List<Position> targets = new ArrayList<>();
        for (int r=0; r<gameMap.rows(); r++){
            for (int c=0; c < gameMap.cols(); c++) {
                Position p = new Position(r, c);
                if (gameMap.getTile(p).getType() != TileType.LAVA) continue;
                for(Position neighbor : gameMap.neighbors(p)){
                    TileType tileType = gameMap.getTile(neighbor).getType();
                    if (tileType == TileType.BARRICADE || tileType == TileType.ROAD || tileType == TileType.HOUSE ){
                        targets.add(neighbor);
                    }
                }
            }
        }
        for (Position p : targets){ gameMap.getTile(p).hitByLavaOnce(); }
    }
}

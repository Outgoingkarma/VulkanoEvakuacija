package com.example.vulkanoevakuacija.map;

import com.example.vulkanoevakuacija.Game.GameConfig;
import com.example.vulkanoevakuacija.path.BfsPathFinder;
import com.example.vulkanoevakuacija.path.PathFinder;

import java.util.*;

public class SimpleRandomMapGenerator implements MapGenerator {
    private final int rows;
    private final int cols;
    private final int houseCount;
    private final int safeCount;
    private final int lavaSeeds;
    private final int barricadesCount;
    private final int wallsCount;
    private final Random random;
    public SimpleRandomMapGenerator(int rows, int cols, int houseCount, int safeCount, int barricadesCount, int wallsCount, int lavaSeeds) {
        this.rows = rows;
        this.cols = cols;
        this.houseCount = houseCount;
        this.safeCount = safeCount;
        this.barricadesCount = barricadesCount;
        this.lavaSeeds = lavaSeeds;
        this.wallsCount = wallsCount;
        this.random = new Random();
    }
    @Override
    public String[] generateMap() {
        final int maxAttempts = 50;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            char[][] map = createBaseMap();
            if (!placeHouses(map)) continue;
            if (!placeSafeZones(map)) continue;
            if (!placeLavaSeeds(map)) continue;
            if (!placeBarricades(map)) continue;
            if (!placeWalls(map)) continue;
            String[] layout = toStringArray(map);
            if (isPlayable(layout)) {
                return layout;
            }
        }
        throw new RuntimeException("Failed to generate map after " + maxAttempts + " attempts!");
    }

    private char[][] createBaseMap() {
        char[][] map = new char[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean border = (row == 0 || row == rows - 1 || col == 0 || col == cols - 1);
                map[row][col] = border ? GameConfig.WALL : GameConfig.ROAD;
            }
        }
        return map;
    }



private boolean placeRandomFeatures( char[][] map, int desiredCount, char feature) {
        int placed = 0, attempts = 0, maxAttempts = Math.max(desiredCount * 10, (rows - 2) * (cols - 2));
        while (placed < desiredCount && attempts < maxAttempts) {
            int row = random.nextInt(rows - 2) + 1;
            int col = random.nextInt(cols - 2) + 1;
            if (map[row][col] == GameConfig.ROAD) {
                map[row][col] = feature;
                placed++;
            }
            attempts++;
        }
        return placed == desiredCount;

}






    private boolean placeHouses(char[][] map) { return placeRandomFeatures(map, houseCount, GameConfig.HOUSE); }
    private boolean placeSafeZones(char[][] map) { return placeRandomFeatures(map, safeCount, GameConfig.SAFE); }
    private boolean placeLavaSeeds(char[][] map) { return placeRandomFeatures(map, lavaSeeds, GameConfig.LAVA); }
    private boolean placeBarricades(char[][] map) { return placeRandomFeatures(map, barricadesCount, GameConfig.BARRICADE); }
    private boolean placeWalls(char[][] map) { return placeRandomFeatures(map, wallsCount, GameConfig.WALL); }
    private String[] toStringArray(char[][] map) {
        String[] result = new String[rows];
        for (int row = 0; row < rows; row++) {
            result[row] = new String(map[row]);
        }
        return result;
    }
    private boolean isPlayable(String[] layout) {
        GameMap gameMap = new GameMap(layout);
        PathFinder pathFinder = new BfsPathFinder();
        List<Position> houses = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Position p = new Position(row, col);
                if (gameMap.getTile(p).getType() == TileType.HOUSE) {
                    houses.add(p);
                }
            }
        }
        Set<Position> safeness = new HashSet<>(gameMap.getSafeZones());
        if(safeness.isEmpty() || houses.isEmpty()){
            return false;
        }
        for (Position house : houses) {
            if (pathFinder.find(gameMap, house, safeness).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}

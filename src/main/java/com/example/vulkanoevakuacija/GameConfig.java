package com.example.vulkanoevakuacija;

public final class GameConfig {
    private GameConfig() {}

    public static final int PLAYER_TOTAL_ACTIONS_COUNT = 6;
    public static final int LAVA_SPREAD_RANGE = 1;

    public static final char ROAD = '.';
    public static final char WALL = '#';
    public static final char SAFE = 'S';
    public static final char LAVA = 'L';
    public static final char HOUSE = 'H';
    public static final char BARRICADE = 'B';

    public static final String[] DEFAULT_MAP = {
            "###########",
            "#H..#..L.S#",
            "#..##....##",
            "#..#......#",
            "#..#..H...#",
            "#....#....#",
            "#S..##....#",
            "###########"
    };
}

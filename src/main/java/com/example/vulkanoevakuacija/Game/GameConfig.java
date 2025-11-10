package com.example.vulkanoevakuacija.Game;

public final class GameConfig {
    private GameConfig() {}

    public static final int PLAYER_TOTAL_ACTIONS_COUNT = 0;

    public static final char ROAD = '.';
    public static final char WALL = '#';
    public static final char SAFE = 'S';
    public static final char LAVA = 'L';
    public static final char HOUSE = 'H';
    public static final char BARRICADE = 'B';
    public static final char RESIDENT_SLOW = '☺';
    public static final char RESIDENT_FAST = '⚡';
    public static final char RESIDENT_EVACUATED = '✔';
    public static final char RESIDENT_DEAD = '☠';


    public static final int rows = 10, cols = 22,
            houses = 3, safeZones = 1, randomBarricades = 70, randomWalls = 10,
            lavaSeeds = 1,

            BARRICADE_HP = 3;

    public static final int gameSpeed = 500;


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

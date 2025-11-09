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


    public static final int rows = 100, cols = 120,
            houses = 20, safeZones = 5, randomBarricades = 50, randomWalls = 1000,
            lavaSeeds = 2;

    public static final int gameSpeed = 50;


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

package com.example.vulkanoevakuacija.Game;

public final class GameConfig {
    private GameConfig() {}

    public static final int PLAYER_TOTAL_ACTIONS_COUNT = 1;

    public static final char ROAD = '.';
    public static final char WALL = '■';
    //public static final char WALL = '#';
    public static final char SAFE = '✔';
   // public static final char LAVA = 'L';
    public static final char LAVA = '~';
    public static final char HOUSE = 'H';
   // public static final char BARRICADE = 'B';
    public static final char BARRICADE = 'O';
    public static final char RESIDENT_SLOW = '☺';
    public static final char RESIDENT_FAST = '⚡';
    //public static final char RESIDENT_EVACUATED = '✔';
    public static final char RESIDENT_DEAD = '☠';


    public static final int rows = 8, cols = 18,
            houses = 3, safeZones = 2, randomBarricades = 30, randomWalls = 20,
            lavaSeeds = 1,

            BARRICADE_HP = 3;

    public static final int gameSpeed = 1000;


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

package com.example.vulkanoevakuacija.model;

import com.example.vulkanoevakuacija.strategy.GameConfig;

public class GameStarter {
    public static void main(String[] args){
        GameMap gameMap = new GameMap(GameConfig.DEFAULT_MAP);
        System.out.println("Grid OK: " + gameMap.rows() + "x" + gameMap.cols());
        char[][] view = gameMap.toCharArray();
        for (int row = 0; row < gameMap.rows(); row++){
            System.out.printf("%3d | %s%n", row, new String(view[row]));
        }
    }
}

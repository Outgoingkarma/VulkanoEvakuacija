package com.example.vulkanoevakuacija.model;

import com.example.vulkanoevakuacija.GameConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public final class GameMap {
    private final int rows;
    private final int cols;
    private final Tile[][] tiles;
    private final List<Position> safeZones = new ArrayList<>();

    public GameMap(String[] layout){
        if(layout == null || layout.length == 0){
            throw new IllegalArgumentException("Layout negali buti tuscias");
        }
        this.rows = layout.length;
        this.cols = layout[0].length();
        this.tiles = new Tile[rows][cols];

        for(int r = 0; r < rows; r++){
            if(layout[r].length() != cols){
                throw new IllegalArgumentException("Visos eilutes privalo buti vieno ilgio. " +
                        "Eilute " + (r+1) + " turi ilgi " + layout[r].length() + ", o tiketasi " + cols + "."
                );
            }
            for(int c = 0; c < cols; c++){
                char ch = layout[r].charAt(c);
                TileType type = charToType(ch);
                tiles[r][c] = new Tile(type);
                if (type == TileType.SAFE){
                    safeZones.add(new Position(r, c));
                }
            }
        }
    }
    public int rows() {return rows;}
    public int cols() {return cols;}
    public boolean inBounds(Position p){
        return p.getRow() >= 0 && p.getRow() < rows && p.getCol() >= 0 && p.getCol() < cols;
    }
    public Tile getTile(Position p){
        if(!inBounds(p)) throw new IllegalArgumentException("Uz ribu: " +p);
        return tiles[p.getRow()][p.getCol()];
    }
    public void setTile(Position p, TileType type){
        if(!inBounds(p)) throw new IllegalArgumentException("Uz ribu: " +p);
        tiles[p.getRow()][p.getCol()] = new Tile(type);
        if (type == TileType.SAFE && !safeZones.contains(p)){
            safeZones.add(p);
        }
    }
    public List<Position> getSafeZones(){return Collections.unmodifiableList(safeZones);}
    public List<Position> neighbors(Position p){
        int allFourDirections = 4;
        List<Position> out = new ArrayList<>(allFourDirections);
        int row = p.getRow(), col = p.getCol();
        addIfIn(out, new Position(row - 1, col));
        addIfIn(out, new Position(row + 1, col));
        addIfIn(out, new Position(row, col - 1));
        addIfIn(out, new Position(row, col + 1));
        return out;
    }
    private void addIfIn(List<Position> list, Position p){
        if(inBounds(p)) list.add(p);
    }
    private TileType charToType(char ch){
        return switch (ch) {
            case '.' -> TileType.ROAD;
            case 'H' -> TileType.HOUSE;
            case 'S' -> TileType.SAFE;
            case 'L' -> TileType.LAVA;
            case '#' -> TileType.WALL;
            case 'B' -> TileType.BARRICADE;
            default -> throw new IllegalArgumentException("Nezinomas simbolis: '" + ch + "'");
        };
    }
    public char[][] toCharArray(){
        char[][] buf = new char[rows][cols];
        for (int row = 0; row< rows; row++){
            for (int col = 0; col < cols; col++){
                buf[row][col] = typeToChar(tiles[row][col].getType());
            }
        }
        return buf;
    }
    private char typeToChar(TileType type){
        return switch (type) {
            case ROAD -> GameConfig.ROAD;
            case HOUSE -> GameConfig.HOUSE;
            case SAFE -> GameConfig.SAFE;
            case LAVA -> GameConfig.LAVA;
            case WALL -> GameConfig.WALL;
            case BARRICADE -> GameConfig.BARRICADE;
        };
    }

}

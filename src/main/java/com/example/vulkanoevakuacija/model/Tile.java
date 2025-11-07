package com.example.vulkanoevakuacija.model;

public final class Tile {
    private TileType type;

    public Tile(TileType type) {
        this.type = type;
    }

    public TileType getType() {return type;}
    public void setType(TileType type) {this.type = type;}

    public boolean isWalkable(){
        return switch (type){
            case ROAD, SAFE, HOUSE -> true;
            case LAVA, WALL, BARRICADE -> false;
        };
    }
    public boolean isDangerous(){return type == TileType.LAVA;}
}

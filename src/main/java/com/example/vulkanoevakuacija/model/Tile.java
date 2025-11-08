package com.example.vulkanoevakuacija.model;

public final class Tile {
    private TileType type;
    private int lavaDelayTurns;

    public Tile(TileType type) {
        this.type = type;
        this.lavaDelayTurns = initialLavaDelay(type);
    }
    private int turn = 1;
    private int initialLavaDelay(TileType type){

        return switch (type){
            case BARRICADE -> (2 * turn);
            default -> 0;
        };
    }

    public TileType getType() {return type;}
    public void setType(TileType type) {
        this.type = type;
        this.lavaDelayTurns = initialLavaDelay(type);
    }

    public boolean isWalkable(){
        return switch (type){
            case ROAD, SAFE, HOUSE -> true;
            case LAVA, WALL, BARRICADE -> false;
        };
    }
    public boolean isDangerous(){return type == TileType.LAVA;}


    private boolean isWeakLavaTarget() {
        return type == TileType.ROAD || type == TileType.HOUSE;
    }

    public boolean lavaCanEnterNow() {
        if (type == TileType.BARRICADE) {
            return isBarricadeDestroyed();
        }
        return isWeakLavaTarget();
    }
    public boolean isBarricadeDestroyed(){
        return lavaDelayTurns <= 0;
    }
    public void hitByLavaOnce() {
        if (!isBarricadeDestroyed() && this.type == TileType.BARRICADE) {
            lavaDelayTurns--;
            if (lavaCanEnterNow()) {
                this.type = TileType.LAVA;
            }
        }
         else if (isWeakLavaTarget()) {
            this.type = TileType.LAVA;
        }
    }
}

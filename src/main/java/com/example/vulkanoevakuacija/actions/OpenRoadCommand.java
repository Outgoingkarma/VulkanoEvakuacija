package com.example.vulkanoevakuacija.actions;

import com.example.vulkanoevakuacija.map.GameMap;
import com.example.vulkanoevakuacija.map.Position;
import com.example.vulkanoevakuacija.map.TileType;
import com.example.vulkanoevakuacija.strategy.GameContext;

public final class OpenRoadCommand implements Command {
    private final Position target;
    public OpenRoadCommand(Position target) {this.target = target;}

    @Override
    public boolean execute(GameContext ctx) {
        if (ctx.getActionsLeft() <= 0) {
            System.out.println("You have no actions left!");
            return false;
        }
        GameMap gameMap = ctx.getGameMap();
        if (!gameMap.inBounds(target)){
            System.out.println("Position out of bounds!");
            return false;
        }
        var tile = gameMap.getTile(target);
        if (tile.getType() != TileType.WALL) {
            System.out.println("You can only open roads in walls!");
            return false;
        }
        gameMap.setTile(target, TileType.ROAD);
        ctx.spendAction();
        System.out.println("Road opened!" + target);
        return true;
    }
}

package com.example.vulkanoevakuacija.actions;

import com.example.vulkanoevakuacija.model.GameMap;
import com.example.vulkanoevakuacija.model.Position;
import com.example.vulkanoevakuacija.model.TileType;
import com.example.vulkanoevakuacija.strategy.GameContext;

public final class BuildBarricadeCommand implements Command{
    private final Position target;
    public BuildBarricadeCommand(Position target) {this.target = target;}

    @Override
    public boolean execute(GameContext ctx) {
        if(ctx.getActionsLeft() <= 0) {
            System.out.println("You have no actions left!");
            return false;
        }
        GameMap gameMap = ctx.getGameMap();
        if(!gameMap.inBounds(target)){
            System.out.println("Position out of bounds!");
            return false;
        }
        var tile = gameMap.getTile(target);
        if (tile.getType() != TileType.ROAD) {
            System.out.println("You can only build barricades on roads!");
            return false;
        }
        gameMap.setTile(target, TileType.BARRICADE);
        ctx.spendAction();
        System.out.println("Barricade built!" + target);
        return true;

    }
}

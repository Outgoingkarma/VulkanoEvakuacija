package com.example.vulkanoevakuacija.agent;

import com.example.vulkanoevakuacija.model.GameMap;
import com.example.vulkanoevakuacija.model.Position;
import com.example.vulkanoevakuacija.model.TileType;
import com.example.vulkanoevakuacija.path.Path;
import com.example.vulkanoevakuacija.path.PathFinder;
import com.example.vulkanoevakuacija.strategy.GameContext;
import com.example.vulkanoevakuacija.strategy.MovementStrategy;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Resident extends Agent{
    private boolean evacuated;
    public Resident(int id, Position start, MovementStrategy movementStrategy) {
        super(id, start, movementStrategy);
    }

    @Override
    public void update(GameContext ctx) {
        if(!isAlive() || evacuated) return;
        int stepsToMake = getMovementStrategy().stepsPerTurn(this, ctx);
        for(int i = 0; i < stepsToMake; i++) {
            if (!tryStepTowardsSafe(ctx)) break;
            if (evacuated) break;
        }
    }
    private boolean tryStepTowardsSafe(GameContext ctx) {
        GameMap gameMap = ctx.getGameMap();
        PathFinder pf = ctx.getPathFinder();
        Set<Position> targets = new HashSet<>(gameMap.getSafeZones());
        Optional<Path> opt = pf.find(gameMap, getPosition(), targets);
        if (opt.isEmpty()) return false;

        Path path = opt.get();
        if (targets.contains(getPosition())) { this.evacuated = true; return true; }

        Position next = path.nextAfter(getPosition()).orElse(null);
        if (next == null) return false;

        var nextTile = gameMap.getTile(next);
        //---------------------------------------------------------------
        if (!nextTile.isWalkable() || nextTile.isDangerous()) return false;
        setPosition(next);
        //---------------------------------------------------------------
        if (gameMap.getTile(next).getType() == TileType.SAFE) {
            this.evacuated = true;
            return true;
        }
        return false;
    }

    public boolean isEvacuated(){ return evacuated; }

}

package com.example.vulkanoevakuacija.Game;

import com.example.vulkanoevakuacija.agent.FastResident;
import com.example.vulkanoevakuacija.agent.SlowResident;
import com.example.vulkanoevakuacija.agent.Updatable;
import com.example.vulkanoevakuacija.map.*;
import com.example.vulkanoevakuacija.path.BfsPathFinder;
import com.example.vulkanoevakuacija.path.PathFinder;
import com.example.vulkanoevakuacija.strategy.GameContext;
import com.example.vulkanoevakuacija.strategy.LavaSpread;

import java.util.ArrayList;
import java.util.List;

public class GameStarter {
    public static void main(String[] args) {



        MapGenerator generator = new SimpleRandomMapGenerator( GameConfig.rows, GameConfig.cols,
                GameConfig.houses, GameConfig.safeZones, GameConfig.randomBarricades, GameConfig.randomWalls,
                GameConfig.lavaSeeds);

        String[] layout = generator.generateMap();
        //String[] layout = GameConfig.DEFAULT_MAP;
        GameMap gameMap = new GameMap(layout);
        PathFinder pathFinder = new BfsPathFinder();
        List<Updatable> agents = createResidentsFromHouses(gameMap);
        GameContext ctx = new GameContext(gameMap, pathFinder, agents, GameConfig.PLAYER_TOTAL_ACTIONS_COUNT);
        LavaSpread spread = new LavaSpread();
        GameEngine engine = new GameEngine(ctx, spread);
        engine.run();
    }

    private static List<Updatable> createResidentsFromHouses(GameMap gameMap) {
        List<Updatable> list = new ArrayList<>();
        int idCounter = 1;
        for (int row = 0; row < gameMap.rows(); row++){
            for (int col = 0; col <gameMap.cols(); col++){
                Position p = new Position(row, col);
                if (gameMap.getTile(p).getType() == TileType.HOUSE) {
                    if (idCounter % 2 == 0) {
                        list.add(new FastResident(idCounter, p));
                    }else {
                        list.add(new SlowResident(idCounter, p));
                    }
                    idCounter++;
                }
            }
        }
        return list;
    }


}

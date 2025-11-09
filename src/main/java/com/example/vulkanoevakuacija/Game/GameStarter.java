package com.example.vulkanoevakuacija.Game;

import com.example.vulkanoevakuacija.agent.FastResident;
import com.example.vulkanoevakuacija.agent.SlowResident;
import com.example.vulkanoevakuacija.agent.Updatable;
import com.example.vulkanoevakuacija.model.GameMap;
import com.example.vulkanoevakuacija.model.Position;
import com.example.vulkanoevakuacija.model.TileType;
import com.example.vulkanoevakuacija.path.BfsPathFinder;
import com.example.vulkanoevakuacija.path.PathFinder;
import com.example.vulkanoevakuacija.strategy.GameContext;
import com.example.vulkanoevakuacija.strategy.LavaSpread;

import java.util.ArrayList;
import java.util.List;

public class GameStarter {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap(GameConfig.DEFAULT_MAP);
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

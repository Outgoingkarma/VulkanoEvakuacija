package com.example.vulkanoevakuacija;

import com.example.vulkanoevakuacija.Game.GameConfig;
import com.example.vulkanoevakuacija.actions.BuildBarricadeCommand;
import com.example.vulkanoevakuacija.actions.SkipCommand;
import com.example.vulkanoevakuacija.agent.*;
import com.example.vulkanoevakuacija.map.GameMap;
import com.example.vulkanoevakuacija.map.Position;
import com.example.vulkanoevakuacija.map.TileType;
import com.example.vulkanoevakuacija.path.BfsPathFinder;
import com.example.vulkanoevakuacija.path.PathFinder;
import com.example.vulkanoevakuacija.strategy.GameContext;
import com.example.vulkanoevakuacija.strategy.LavaSpread;
import com.example.vulkanoevakuacija.strategy.MovementStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {

    private static String row(char... cells) {
        return new String(cells);
    }

    @Test
    void residentEvacuatesWhenSafeIsAdjacent() {
        String[] layout = {
                row(GameConfig.WALL, GameConfig.WALL, GameConfig.WALL, GameConfig.WALL),
                row(GameConfig.WALL, GameConfig.HOUSE, GameConfig.SAFE, GameConfig.WALL),
                row(GameConfig.WALL, GameConfig.WALL, GameConfig.WALL, GameConfig.WALL)
        };
        GameMap map = new GameMap(layout);
        PathFinder pathFinder = new BfsPathFinder();
        MovementStrategy strategy = (resident, ctx) -> 1;
        Resident resident = new SlowResident(1, new Position(1, 1));
        resident.setMovementStrategy(strategy);
        List<Updatable> agents = new ArrayList<>(List.of(resident));
        GameContext ctx = new GameContext(map, pathFinder, agents, 0);

        resident.update(ctx);

        assertTrue(resident.isEvacuated(), "Resident should reach safety in one step");
        assertEquals(new Position(1, 2), resident.getPosition());
    }

    @Test
    void pathFinderReturnsEmptyWhenWallsBlockRoute() {
        String[] layout = {
                row(GameConfig.WALL, GameConfig.WALL, GameConfig.WALL),
                row(GameConfig.WALL, GameConfig.HOUSE, GameConfig.WALL),
                row(GameConfig.WALL, GameConfig.WALL, GameConfig.SAFE)
        };
        GameMap map = new GameMap(layout);
        PathFinder pathFinder = new BfsPathFinder();

        var result = pathFinder.find(map, new Position(1, 1), Set.of(new Position(2, 2)));

        assertTrue(result.isEmpty(), "Walls should make the safe zone unreachable");
    }

    @Test
    void lavaSpreadConvertsRoadButDamagesBarricadeOverTime() {
        String[] layout = {
                row(GameConfig.WALL, GameConfig.WALL, GameConfig.WALL, GameConfig.WALL),
                row(GameConfig.WALL, GameConfig.ROAD, GameConfig.LAVA, GameConfig.BARRICADE),
                row(GameConfig.WALL, GameConfig.WALL, GameConfig.WALL, GameConfig.WALL)
        };
        GameMap map = new GameMap(layout);
        LavaSpread spread = new LavaSpread();

        spread.applyOneStepSpread(map);

        assertEquals(TileType.LAVA, map.getTile(new Position(1, 1)).getType(), "Road next to lava should ignite");
        assertEquals(TileType.BARRICADE, map.getTile(new Position(1, 3)).getType(), "Barricade should resist initial lava hit");
    }

    @Test
    void buildBarricadeConsumesActionAndUpdatesTile() {
        String[] layout = {
                row(GameConfig.WALL, GameConfig.WALL, GameConfig.WALL),
                row(GameConfig.WALL, GameConfig.ROAD, GameConfig.WALL),
                row(GameConfig.WALL, GameConfig.WALL, GameConfig.WALL)
        };
        GameMap map = new GameMap(layout);
        GameContext ctx = new GameContext(map, new BfsPathFinder(), new ArrayList<>(), 2);
        BuildBarricadeCommand command = new BuildBarricadeCommand(new Position(1, 1));

        boolean executed = command.execute(ctx);

        assertTrue(executed);
        assertEquals(TileType.BARRICADE, map.getTile(new Position(1, 1)).getType());
        assertEquals(1, ctx.getActionsLeft());
    }

    @Test
    void factoryMethodCreatesAlternatingResidents() {
        ResidentFactory factory = new AlternatingResidentFactory();
        Resident first = factory.createResident(1, new Position(0, 0));
        Resident second = factory.createResident(2, new Position(0, 1));

        assertInstanceOf(SlowResident.class, first);
        assertInstanceOf(FastResident.class, second);
    }

    @Test
    void skipCommandLeavesActionCountUnchanged() {
        String[] layout = {
                row(GameConfig.WALL, GameConfig.WALL, GameConfig.WALL),
                row(GameConfig.WALL, GameConfig.ROAD, GameConfig.WALL),
                row(GameConfig.WALL, GameConfig.WALL, GameConfig.WALL)
        };
        GameContext ctx = new GameContext(new GameMap(layout), new BfsPathFinder(), new ArrayList<>(), 1);
        SkipCommand skip = new SkipCommand();

        boolean executed = skip.execute(ctx);

        assertTrue(executed);
        assertEquals(1, ctx.getActionsLeft(), "Skipping should not consume actions");
    }
}
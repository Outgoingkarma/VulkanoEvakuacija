package com.example.vulkanoevakuacija.strategy;

import com.example.vulkanoevakuacija.agent.Updatable;
import com.example.vulkanoevakuacija.model.GameMap;
import com.example.vulkanoevakuacija.path.PathFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameContext {
    private final GameMap gameMap;
    private final PathFinder pathFinder;
    private final List<Updatable> agents;
    private int actionsLeft;
    private int turn;
    public GameContext(GameMap gameMap, PathFinder pathFinder, List<Updatable> agents, int actionsLeft) {
        this.gameMap = Objects.requireNonNull(gameMap);
        this.pathFinder = Objects.requireNonNull(pathFinder);
        this.agents = new ArrayList<>(Objects.requireNonNull(agents));
        this.actionsLeft = actionsLeft;
        this.turn = 0;
    }

    public GameMap getGameMap(){ return gameMap; }
    public PathFinder getPathFinder() { return pathFinder; }

    public List<Updatable> getAgents() { return agents; }
    public void addAgent(Updatable agent) { agents.add(Objects.requireNonNull(agent)); }

    public int getActionsLeft() { return actionsLeft; }

    public void spendAction() { if (actionsLeft > 0) actionsLeft--; }

    public int getTurn() { return turn; }

    public void nextTurn() { turn++; }

}

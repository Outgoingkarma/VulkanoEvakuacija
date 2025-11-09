package com.example.vulkanoevakuacija.Game;

import com.example.vulkanoevakuacija.actions.Command;
import com.example.vulkanoevakuacija.actions.CommandParser;
import com.example.vulkanoevakuacija.agent.Resident;
import com.example.vulkanoevakuacija.agent.Updatable;
import com.example.vulkanoevakuacija.model.GameMap;
import com.example.vulkanoevakuacija.model.Position;
import com.example.vulkanoevakuacija.model.TileType;
import com.example.vulkanoevakuacija.strategy.GameContext;
import com.example.vulkanoevakuacija.strategy.LavaSpread;

import java.util.Scanner;

public final class GameEngine {
    private final GameContext ctx;
    private final LavaSpread lavaSpread;
    private final Scanner scanner = new Scanner(System.in);
    public GameEngine(GameContext ctx, LavaSpread lavaSpread) {
        this.ctx = ctx;
        this.lavaSpread = lavaSpread;
    }

    public void run() {
        System.out.println("Welcome to Vulkano Evakuacija!");



        while (!isGameOver()) {
            printState();
            if (ctx.getActionsLeft() > 0){ handlePlayerActions();
            } else {
                System.out.println("Veiksmai beigesi!!!");
                System.out.println("Nebegali daugiau statyti/atverti keliu ar barikadu!");
                System.out.println("Zaidimas tesiasi");
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
            moveAgents();
            lavaSpread.applyOneStepSpread(ctx.getGameMap());
            resolveDeathsOnLava();
            ctx.nextTurn();
        }
        printFinalResult();

    }
    private void handlePlayerActions() {
//        if (ctx.getActionsLeft() <= 0) {
//            System.out.println("Veiksmai beigesi!!!");
//            System.out.println("Nebegali daugiau statyti/atverti keliu ar barikadu!");
//            System.out.println("Zaidimas tesiasi");
//            return;
//        }


        while (true) {
            System.out.println("Ivesk komanda (Eiliskumas ->  <komanda> <eilute> <stulpelis>)");
            System.out.println("Galimos komandos: ");
            System.out.println("b/barikada/barricade ");
            System.out.println("o/atverti/open ");
            System.out.println("s/praleisti/skip ");
            String line = scanner.nextLine();

            var parsed = CommandParser.parse(line);
            if (parsed.isEmpty()) {
                System.out.println("Neteisinga komanda!");
                continue;
            }

            Command cmd = parsed.get();
            boolean success = cmd.execute(ctx);

            if (!success) {
                continue;
            }
        break;
        }






    }
    private void moveAgents() {
        for (Updatable a : ctx.getAgents()){
            a.update(ctx);
        }
    }
    private void resolveDeathsOnLava() {
        GameMap gameMap = ctx.getGameMap();
        for (Updatable a : ctx.getAgents()){
            if (a instanceof Resident resident) {
                if (resident.isAlive() && !resident.isEvacuated()) {
                    if (gameMap.getTile(resident.getPosition()).getType() == TileType.LAVA) {
                        resident.die();
                        System.out.println("Gyventojas " + resident.getId() + " mire lavos bangoje ties " + resident.getPosition());
                    }
                }
            }
        }
    }

    private boolean isGameOver() {
        boolean anyAliveNotEvacuated = false;
        boolean anyEvacuated = false;
        for (Updatable a : ctx.getAgents()) {
            if (a instanceof Resident resident) {
                if (resident.isAlive() && !resident.isEvacuated()) anyAliveNotEvacuated = true;
                if (resident.isEvacuated()) anyEvacuated = true;
            }
        }
        return !anyAliveNotEvacuated;
    }


    private boolean anyResidentExists() {
        for (Updatable a : ctx.getAgents()) {
            if (a instanceof Resident) return true;
        }
        return false;
    }
    private void printState (){
    GameMap gameMap = ctx.getGameMap();
    char[][] view = gameMap.toCharArray();
    for (Updatable updatable : ctx.getAgents()) {
        if (updatable instanceof Resident resident) {
            Position pos = resident.getPosition();
            if (!gameMap.inBounds(pos)) continue;
            if (!resident.isAlive()) {
                view[pos.getRow()][pos.getCol()] = GameConfig.RESIDENT_DEAD;
            }  else {
                view[pos.getRow()][pos.getCol()] = 'R';
            }
        }
    }
    System.out.println();
    System.out.println("---------------");
    System.out.println("Eilute\\Stulpelis 0.." + (gameMap.cols() - 1));
    for (int row = 0; row < gameMap.rows(); row++){
        System.out.printf("%3d | %s%n", row, new String(view[row]));
    }


    System.out.println("Like veiksmai: " + ctx.getActionsLeft() + " / " + GameConfig.PLAYER_TOTAL_ACTIONS_COUNT);
    System.out.println("Zingsnis: " + ctx.getTurn());
    printResidentsSummary();
    }
    private void printResidentsSummary (){
        int aliveInDanger = 0, dead = 0, evacuated = 0;
        for (Updatable a : ctx.getAgents()) {
            if (a instanceof Resident resident) {
                if (resident.isEvacuated()){
                    evacuated++;
                }else if (!resident.isAlive()){
                    dead++;
                }
                else aliveInDanger++;
            }
            System.out.println("Gyventojai gyvi: " + aliveInDanger + ", mire: " + dead + ", issigelbejo: " + evacuated);
        }
    }

    private void printFinalResult() {
        System.out.println();
        System.out.println("Zaidimas baigtas!");
        printResidentsSummary();
    }




}

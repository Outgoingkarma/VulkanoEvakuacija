package com.example.vulkanoevakuacija.actions;

import com.example.vulkanoevakuacija.model.Position;

import java.util.Locale;
import java.util.Optional;

public final class CommandParser {
    private CommandParser() { }

    public static Optional<Command> parse(String command) {
        if (command == null || command.isBlank()) return Optional.empty();
        String[] parts = command.trim().toLowerCase(Locale.ROOT).split("\\s+");
        String cmd = parts[0];

        try {
            switch (cmd) {
                case "b", "barricade", "barikada" -> {
                    if (parts.length != 3) {
                        System.out.println("Naudojimas: b <row> <col>");
                        return Optional.empty();
                    }
                    int row = Integer.parseInt(parts[1]);
                    int col = Integer.parseInt(parts[2]);
                    return Optional.of(new BuildBarricadeCommand(new Position(row, col)));
                }
                case "o", "open", "atverti" -> {
                    if (parts.length != 3) {
                        System.out.println("Naudojimas: o <row> <col>");
                        return Optional.empty();
                    }
                    int row = Integer.parseInt(parts[1]);
                    int col = Integer.parseInt(parts[2]);
                    return Optional.of(new OpenRoadCommand(new Position(row, col)));
                }
                case "s", "skip", "praleisti" -> {
                    return Optional.of(new SkipCommand());
                }
                default -> {
                    System.out.println("Nepriskyrimo komanda: " + cmd);
                    return Optional.empty();
                }
            }
        }catch (NumberFormatException e){
            System.out.println("Eilute/stulpelis turi buti skaicius!");
            return Optional.empty();
        }
    }
}

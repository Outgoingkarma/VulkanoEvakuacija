package com.example.vulkanoevakuacija.path;

import com.example.vulkanoevakuacija.map.Position;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Path {
    private final List<Position> steps;
    private Path(List<Position> steps){
        this.steps = steps;
    }
    public static Path of(List<Position> steps){
        Objects.requireNonNull(steps, "steps");
        if (steps.isEmpty()) throw new IllegalArgumentException("Path can't be empty");
        return new Path(steps);
    }

    public Optional<Position> nextAfter(Position current) {
        int i = steps.indexOf(current);
        if (i < 0 || i + 1 >= steps.size()) return Optional.empty();
        return Optional.of(steps.get(i + 1));
    }


}

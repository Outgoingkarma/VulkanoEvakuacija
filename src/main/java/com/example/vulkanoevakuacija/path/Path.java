package com.example.vulkanoevakuacija.path;

import com.example.vulkanoevakuacija.model.Position;

import java.util.List;
import java.util.Objects;

public final class Path {
    private final List<Position> steps;
    private Path(List<Position> steps){
        this.steps = steps;
    }
    public static Path of(List<Position> steps){
        Objects.requireNonNull(steps, "steps");
        if (steps.isEmpty()) throw new IllegalArgumentException("Path negali buti tuscias");
        return new Path(steps);
    }
    public List<Position> getSteps(){ return steps; }
    public Position nextAfter(Position current){
        for (int i = 0; i < steps.size() - 1; i++){
            if (steps.get(i).equals(current)) return steps.get( i + 1 );
        }
        return null;
    }
    public int length(){ return steps.size(); }
    public Position start(){ return steps.get(0); }
    @Override
    public String toString() { return steps.toString(); }

}

package com.example.vulkanoevakuacija.model;

import java.util.Objects;

public final class Position implements Comparable<Position> {
    private final int row;
    private final int col;
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position p)) return false;
        return row == p.row && col == p.col;
    }
    @Override public int hashCode() {return Objects.hash(row, col);}

    @Override public String toString() {return "(" + row + ", " + col + ")";}

    @Override
    public int compareTo(Position other) {
        int byRow = Integer.compare(this.row, other.row);
        return (byRow != 0) ? byRow : Integer.compare(this.col, other.col);
    }
}

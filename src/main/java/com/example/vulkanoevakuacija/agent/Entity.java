package com.example.vulkanoevakuacija.agent;

import com.example.vulkanoevakuacija.model.Position;

public abstract class Entity {
    private Position position;
    private boolean alive = true;

    protected Entity(Position position) {this.position = position;}
    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
    public boolean isAlive() {return alive;}
    public void die() {this.alive = false;}
}

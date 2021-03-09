package com.mygdx.game.Objects;

public class Point {
    public float x;
    public float y;
    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }
    public Point(Point p){
        this.x = p.x;
        this.y = p.y;
    }
    public Point(){
        x = 0;
        y = 0;
    }
    public float distanceFrom(Point p){
        return (float)Math.sqrt(p.x*p.x + p.y*p.y);
    }
}

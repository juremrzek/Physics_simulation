package com.mygdx.game.Objects;

public class Circle {
    private float x;
    private float y;
    private float r;
    private float vx;
    private float vy;
    public Circle(float x, float y, float r, float vx, float vy) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.vx = vx;
        this.vy = vy;
    }
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
    public float getR() {
        return r;
    }
    public void setR(float r) {
        this.r = r;
    }
    public float getVx() {
        return vx;
    }
    public void setVx(float vx) {
        this.vx = vx;
    }
    public void setVy(float vy){
        this.vy = vy;
    }
    public float getVy(){
        return vy;
    }
    public float getSpeed(){
        return (float)Math.sqrt(vx*vx + vy*vy);
    }
}

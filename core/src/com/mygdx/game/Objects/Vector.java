package com.mygdx.game.Objects;

public class Vector {
    public Point p1;
    public Point p2;
    public Vector(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    public Vector(){
        this.p1 = new Point();
        this.p2 = new Point();
    }
    public float dotProduct(Vector v){
        return (p2.x - p1.x)*(v.p2.x - v.p1.x) + (p2.y - p1.y)*(v.p2.y - v.p1.y);
    }
    public double getAngle(Vector v){
        if(this.getLength() == 0 || v.getLength() == 0){
            return 0;
        }
        if(Math.abs(this.dotProduct(v)/(this.getLength()*v.getLength())) > 1){
            System.out.println("error - cos value not between -1 and 1");
            System.out.println(this.dotProduct(v)/(this.getLength()*v.getLength()));
            return 0;
        }
        return Math.acos(this.dotProduct(v)/(this.getLength()*v.getLength()));
    }
    public double getLength(){
        return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y));
    }
    public void scale(float scalar){
        p2.x = p1.x+(p2.x - p1.x)*scalar;
        p2.y = p1.y+(p2.y - p1.y)*scalar;
    }
}

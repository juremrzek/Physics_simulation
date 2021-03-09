package com.mygdx.game.Objects;

import com.badlogic.gdx.utils.FloatArray;
import com.mygdx.game.Screens.ElasticCollision;

public class Polygon {
    public int n;
    public float[] xPoints;
    public float[] yPoints;
    public float[] x;
    public float[] y;
    public float[] startAngles;
    public Point center; //center of mass
    public float vx;
    public float vy;
    public float angle;
    public float omega;
    public float mass;

    public Polygon(int n, float mass, float vx, float vy, float angle, float omega){
        xPoints = new float[n];
        yPoints = new float[n];
        x = new float[n]; y = new float[n];
        startAngles = new float[n];
        this.n = n;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.omega = omega;
        this.mass = mass;
        center = new Point();
    }
    public float[] getPoints(){ //merge xPoints and yPoints into one table (so it is suitable for polygon collisions and drawing)
        float[] combinedPoints = new float[xPoints.length + yPoints.length];
        for(int i=0; i<combinedPoints.length; i+=2) {
            combinedPoints[i]=xPoints[i/2];
            combinedPoints[i+1]=yPoints[i/2];
        }
        return combinedPoints;
    }
    public void move(float x, float y){
        center.x += x;
        center.y += y;
        for(int i=0; i<n; i++) {
            xPoints[i] += x;
            yPoints[i] += y;
            this.x[i] = xPoints[i] - center.x;
            this.y[i] = yPoints[i] - center.y;
        }
    }
    public void rotate(){
        float r;
        for(int i=0; i<n; i++){
            r = (float)Math.sqrt(x[i]*x[i]+y[i]*y[i]);
            xPoints[i] = center.x+(float)(r*Math.cos(angle+startAngles[i]));
            yPoints[i] = center.y+(float)(r*Math.sin(angle+startAngles[i]));
            //System.out.println(r);
            x[i] = xPoints[i] - center.x;
            y[i] = yPoints[i] - center.y;
        }
    }
    public void init(){
        float sum = 0;
        for (float xPoint : xPoints) {
            sum += xPoint;
        }
        center.x = sum/n;

        sum = 0;
        for (float yPoint : yPoints) {
            sum += yPoint;
        }
        center.y = sum/n;

        for(int i=0; i<n; i++){
            x[i] = xPoints[i] - center.x;
            y[i] = yPoints[i] - center.y;
            Vector pointVector = new Vector(new Point(0, 0), new Point(x[i], y[i]));
            Vector xVector = new Vector(new Point(0, 0), new Point(1, 0));
            if(y[i]>0)
                startAngles[i] = (float)pointVector.getAngle(xVector);
            else
                startAngles[i] = (float)(Math.PI*2-pointVector.getAngle(xVector));
        }
    }
    public boolean collides(Polygon poly){
        Vector normal;
        Vector vertice = new Vector();
        double length;
        float max1; float min1; float max2; float min2;
        float projection;
        for(int firstPolygon=0; firstPolygon<2; firstPolygon++) {
            Polygon poly1,poly2;
            if(firstPolygon == 1) {
                poly1 = this;
                poly2 = poly;
            }
            else{
                poly1 = poly;
                poly2 = this;
            }
            for (int i = 0; i < poly1.n; i++) {
                if (i == 0) {
                    normal = new Vector(new Point(0, 0), new Point(poly1.y[poly1.n - 1] - poly1.y[0], poly1.x[0] - poly1.x[poly1.n - 1]));
                } else normal = new Vector(new Point(0, 0), new Point(poly1.y[i - 1] - poly1.y[i], poly1.x[i] - poly1.x[i - 1]));
                length = normal.getLength();
                normal.p2.x /= (float)length;
                normal.p2.y /= (float)length; //normalisation of the normal

                max1 = max2 = -Float.MAX_VALUE;
                min1 = min2 = Float.MAX_VALUE;
                for (int j = 0; j < poly1.n; j++) {
                    projection = poly1.x[j]*normal.p2.x+poly1.y[j]*normal.p2.y;
                    if (projection < min1)
                        min1 = projection;
                    if (projection > max1)
                        max1 = projection;
                }

                for (int j = 0; j < poly2.n; j++) {
                    projection = (poly2.xPoints[j]-poly1.center.x)*normal.p2.x+(poly2.yPoints[j]-poly1.center.y)*normal.p2.y;
                    if (projection < min2)
                        min2 = projection;
                    if (projection > max2)
                        max2 = projection;
                }
                if (!(max2 >= min1 && max1 >= min2)) {
                    return false;
                }
            }
        }
        return true;
    }
    private FloatArray getFloatArray(){
        return new FloatArray(getPoints());
    }
}

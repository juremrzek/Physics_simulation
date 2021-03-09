package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Objects.Circle;
import com.mygdx.game.Objects.Point;
import com.mygdx.game.Objects.Polygon;
import com.mygdx.game.Objects.Vector;
import com.mygdx.game.PhysicsApp;

public class ElasticCollision implements Screen {
    private PhysicsApp game;
    private SpriteBatch batch;
    private ShapeRenderer sr;
    private OrthographicCamera camera;
    private FitViewport viewport;

    //Objects to draw polygons
    private Pixmap pixmap;
    private PolygonSprite poly;
    private PolygonSpriteBatch polyBatch;
    private Texture textureSolid;
    private EarClippingTriangulator ect;

    private Point ScreenCenter;
    Polygon rect1;
    Polygon rect2;

    public ElasticCollision(PhysicsApp game, SpriteBatch batch, ShapeRenderer sr){
        this.game = game;
        this.batch = batch;
        this.sr = sr;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 900, camera);
        ScreenCenter = new Point(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2);

        rect1 = new Polygon(4, 100, -100, 0, 0.32f, 0);
        rect2 = new Polygon(4, 80, 50, 0, 0, 0);

        rect1.xPoints[0] = ScreenCenter.x+300; //b
        rect1.yPoints[0] = ScreenCenter.y-100;
        rect1.xPoints[1] = ScreenCenter.x+400;
        rect1.yPoints[1] = ScreenCenter.y+100;
        rect1.xPoints[2] = ScreenCenter.x+200;
        rect1.yPoints[2] = ScreenCenter.y+100;
        rect1.xPoints[3] = ScreenCenter.x+200;
        rect1.yPoints[3] = ScreenCenter.y-100;
        rect1.init();

        rect2.xPoints[0] = ScreenCenter.x;
        rect2.yPoints[0] = ScreenCenter.y+50;
        rect2.xPoints[1] = ScreenCenter.x+50;
        rect2.yPoints[1] = ScreenCenter.y-50;
        rect2.xPoints[2] = ScreenCenter.x-50;
        rect2.yPoints[2] = ScreenCenter.y-50;
        rect2.xPoints[3] = ScreenCenter.x-50;
        rect2.yPoints[3] = ScreenCenter.y+50;
        rect2.init();

        init();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        drawPolygon(rect1.getPoints(), Color.BLUE);
        if(rect2.collides(rect1))
            drawPolygon(rect2.getPoints(), Color.RED);
        else
            drawPolygon(rect2.getPoints(), Color.BLUE);
        /*sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(camera.combined);
        sr.setColor(Color.BLACK);
        sr.circle(rect1.center.x, rect1.center.y, 10);
        sr.setColor(Color.WHITE);*/

        /*Vector[] normals = rect2.collided(rect1);
        for(int i=0; i<rect2.n; i++){
            sr.rectLine(normals[i].p1.x+rect2.center.x, normals[i].p1.y+rect2.center.y, normals[i].p2.x+rect2.center.x, normals[i].p2.y+rect2.center.y, 5);
        }*/
        //System.out.println("p1: "+temp.p1.x+", "+temp.p1.y);
        //System.out.println("p2: "+temp.p2.x+", "+temp.p2.y);

        sr.end();

        update(delta);
    }

    private void update(float dt){
        rect2.move(rect2.vx*dt, rect2.vy*dt);
        rect1.angle+=rect1.omega*dt;
        rect2.angle+=rect2.omega*dt;
        rect1.rotate();
        rect2.rotate();
        //rect2.move(rect2.vx*dt, rect2.vy*dt);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.Q))
            game.dispose();
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            rect2.vy = 200;
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            rect2.vy = -200;
        else rect2.vy = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            rect2.vx = +200;
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            rect2.vx = -200;
        else rect2.vx=0;
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            rect2.omega = (float)Math.PI;
        else if(Gdx.input.isKeyPressed(Input.Keys.D))
            rect2.omega = -(float)Math.PI;
        else rect2.omega = 0;

    }

    private void init(){
        //objects to draw polygons
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        ect = new EarClippingTriangulator();
        polyBatch = new PolygonSpriteBatch();
        pixmap.fill();
        textureSolid = new Texture(pixmap);
        PolygonRegion polyReg = new PolygonRegion(new TextureRegion(textureSolid), rect1.getPoints(), ect.computeTriangles(rect1.getPoints()).toArray());
        poly = new PolygonSprite(polyReg);
    }

    private void drawPolygon(float [] points, Color color){
        pixmap.setColor(color);
        pixmap.fill();
        textureSolid = new Texture(pixmap);
        PolygonRegion polyReg = new PolygonRegion(new TextureRegion(textureSolid), points, ect.computeTriangles(points).toArray());
        poly.setRegion(polyReg);
        poly.setOrigin(25, 25);
        polyBatch.setProjectionMatrix(camera.combined);
        polyBatch.begin();
        polyBatch.draw(polyReg, 2, 2);
        polyBatch.end();
        textureSolid.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        pixmap.dispose();
        polyBatch.dispose();
        textureSolid.dispose();
    }
}

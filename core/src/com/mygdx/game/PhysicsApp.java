package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Screens.ElasticCollision;

public class PhysicsApp extends Game {
	private SpriteBatch batch;
	private ShapeRenderer sr;
	public boolean fullscreen;
	public Graphics.Monitor primaryMonitor;

	@Override
	public void create () {
		batch = new SpriteBatch();
		fullscreen = false;
		sr = new ShapeRenderer();
		sr.setColor(Color.RED);
		primaryMonitor = Gdx.graphics.getPrimaryMonitor();
		if(fullscreen)
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode(primaryMonitor));
		setScreen(new ElasticCollision(this, batch, sr));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		sr.dispose();
		System.exit(0);
	}
}

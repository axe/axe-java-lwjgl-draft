package com.axe.test;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import com.axe.Axe;
import com.axe.View;
import com.axe.game.Game;
import com.axe.game.GameState;
import com.axe.input.Key;
import com.axe.input.KeyState;
import com.axe.lwjgl.AxeLWJGL;
import com.axe.lwjgl.SettingsLWJGL;
import com.axe.math.Vec2f;
import com.axe.node.RenderedNode;
import com.axe.render.Renderer;
import com.axe.window.Window;
import com.axe2d.Camera2;
import com.axe2d.Scene2;

public class TestFullscreen 
{

	public static void main(String[] args)
	{
		SettingsLWJGL settings = new SettingsLWJGL();
		
		AxeLWJGL.init(settings);
		
		Axe.renderers.register(TestNode.VIEW, new TestNodeRenderer());
		
		Debug.listen(Axe.monitors);
		
		Game game = Axe.games.create();
		
		Window window = game.newWindow();
		window.getPlacement().center(640, 480);
		window.setFullscreen(true);
		window.setTitle("TestFullscreen");
		
		Debug.listen(game);
		Debug.listen(window);
		
		Scene2 scene = game.newScene(2);
		scene.setRoot(new TestNode());
		
		View<Vec2f> view = window.newView(scene);
		Camera2 camera = view.getCamera();
		camera.size.set(640, 480);
		camera.update();
		
		game.run();
	}
	
	private static class TestNode extends RenderedNode<Vec2f>
	{
		public static final int VIEW = Axe.renderers.create();
		
		public KeyState esc;
		public Vec2f center = new Vec2f();
		public Vec2f size = new Vec2f(320, 240);
		
		public TestNode()
		{
			super( VIEW );
			
			this.esc = Axe.keys.getKey( Key.ESCAPE );
		}
		
		@Override
		public void update(GameState state, View<Vec2f> view) 
		{
			if (esc.down)
			{
				view.getGame().stop();
			}
		}
	}

	private static class TestNodeRenderer implements Renderer<TestNode>
	{
		@Override
		public void begin(TestNode entity, GameState state, View view) 
		{
			float cx = entity.center.x;
			float cy = entity.center.y;
			float hx = entity.size.x * 0.5f;
			float hy = entity.size.y * 0.5f;
			
			glBegin( GL_QUADS );
			glColor3f(1, 1, 1);
			glVertex2f(cx-hx, cy-hy);
			glVertex2f(cx+hx, cy-hy);
			glVertex2f(cx+hx, cy+hy);
			glVertex2f(cx-hx, cy+hy);
			glEnd();
		}	
	}
	
}

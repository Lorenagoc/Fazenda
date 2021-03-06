 package entities;

//import java.awt.DisplayMode;

import org.lwjgl.input.Keyboard;
//import org.lwjgl.util.Display;
import org.lwjgl.util.vector.Vector3f;

import models.TextureModel;
import renderEngine.DisplayManager;

public class Player extends Entity{
	
	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 120;
	private static float GRAVITY = -60;
	private static final float JUMP_POWER = 30;
	
	private static final float TERRAIN_HEIGHT = 0;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean inAir = false;

	public Player(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move() {
		
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed = upwardsSpeed + GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		if(super.getPosition().y < TERRAIN_HEIGHT) {
			upwardsSpeed = 0;
			inAir  = false;
			super.getPosition().y = TERRAIN_HEIGHT;
		}
	}
	
	public void moveAuto() {
		
		automate();
		//double initial = System.currentTimeMillis();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		this.autojump();
	
	}
	
	private void jump() {
		if(!inAir) {
			this.upwardsSpeed = JUMP_POWER;
			inAir = true;
		}
	}
	
	private void autojump() {
		if(!inAir) {
			this.upwardsSpeed = JUMP_POWER;
			inAir = true;
		}
		upwardsSpeed = upwardsSpeed + GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		if(super.getPosition().y < TERRAIN_HEIGHT) {
			upwardsSpeed = 0;
			inAir  = false;
			super.getPosition().y = TERRAIN_HEIGHT;
		}
		
	}
	
	private void checkInputs() {

		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            this.currentSpeed = RUN_SPEED;
            
        }else if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            this.currentSpeed = -RUN_SPEED;
            
        }else {
        	this.currentSpeed = 0;
        }
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		
		}else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
			
		}else {
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_J)) {
			jump();
		}
	}
	
	public void automate() {
		
		this.currentSpeed = -RUN_SPEED;
		this.currentTurnSpeed = TURN_SPEED;
	
	}
	
}

package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromPlayer = 50; //zoom da câmera
	private float angleAroundPlayer = 0; //rotação da câmera
	
	private Vector3f position = new Vector3f(444,30,-207);
    private float pitch = 20;
    private float yaw = 0;
    private float roll;
    
    private Player player;
     
    //construtor
    public Camera(Player player){
    	this.player = player;
    }
    
    public Camera() {
    	
    }
     
    public void move(){
    	
       if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
            position.z= position.z - 1f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            position.z= position.z + 1f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            position.x= position.x + 1f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            position.x= position.x - 1f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y= position.y + 1f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            position.y= position.y - 1f;
        }
      
        System.out.println("posicao em x " +position.x);
        System.out.println("posicao em y " +position.y);
        System.out.println("posicao em z " +position.z);
        System.out.println("roll  " +this.roll);
        System.out.println("yaw " + this.yaw );
    }
    
    public void moveInThirdPerson() {   	
    	
    	calculateZoom();
    	calculatePitch();
    	calculateAngleAroundPlayer();  
    	float hotizontalDistance = calculateHorizontalDistance();
    	float verticalDistance = calculateVerticalDistance();
    	calculateThirdCameraPosition(hotizontalDistance, verticalDistance);
    	this.yaw = 180 -  (player.getRotY() + angleAroundPlayer);
    	
    	System.out.println("posicao em x " +position.x);
        System.out.println("posicao em y " +position.y);
        System.out.println("posicao em z " +position.z);
        System.out.println("roll  " +this.roll);
        System.out.println("yaw " + this.yaw );
    	
    }
     
    public Vector3f getPosition() {
        return position;
    }
 
    public float getPitch() {
        return pitch;
    }
 
    public float getYaw() {
        return yaw;
    }
 
    public float getRoll() {
        return roll;
    }
    
    private void calculateThirdCameraPosition(float horizontalDistance, float verticalDistance) {
    	float theta = player.getRotY() + angleAroundPlayer;
    	float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
    	float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
    	position.x = player.getPosition().x - offsetX;
    	position.z = player.getPosition().z - offsetZ;
    	position.y = player.getPosition().y + verticalDistance;
    }
          
    private float calculateHorizontalDistance() {
    	return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }
    
    private float calculateVerticalDistance() {
    	return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }
    
    private void calculateZoom() {
    	float zoomLevel = Mouse.getDWheel() * 0.1f;
    	distanceFromPlayer = distanceFromPlayer - zoomLevel;
    }
    
    private void calculatePitch() {
    	if(Mouse.isButtonDown(1)) {
    		float pitchChange = Mouse.getDY() * 0.1f;
    		pitch = pitch - pitchChange;
    		
    	}
    }
    
    private void calculateAngleAroundPlayer() {
    	if(Mouse.isButtonDown(0)) {
    		float angleChange = Mouse.getDX() * 0.3f;
    		angleAroundPlayer = angleAroundPlayer - angleChange;
    		
    	}
    }   
}

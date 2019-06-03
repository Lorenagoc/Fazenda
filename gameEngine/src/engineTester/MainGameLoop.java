package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TextureModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
	    Loader loader = new Loader();
	    
	    //---------------------------------------- texturas do terreno -----------------------------------------
	    
	    TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
	    TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
	    TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
	    TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
	    TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
	    
	    TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);   
	  	    
	    //--------------------------------------------------------------------------------------------------------
	    
	    
	  //  ModelData data = OBJFileLoader.loadOBJ("tree");
	       
	    RawModel model = OBJLoader.loadObjModel("tree", loader);
	    
	    //---------------------------------------------- carrega OBJ e textura dos objeto em cena  -----------------------------------------------
	    
	    TextureModel staticModel = new TextureModel(model, new ModelTexture(loader.loadTexture("tree")));
	    TextureModel grass = new TextureModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
	    TextureModel flower = new TextureModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("flower")));
	    TextureModel fern = new TextureModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
	    TextureModel bobble = new TextureModel(OBJLoader.loadObjModel("lowPolyTree", loader), new ModelTexture(loader.loadTexture("lowPolyTree")));
	    TextureModel lamp = new TextureModel(OBJLoader.loadObjModel("lamp", loader), new ModelTexture(loader.loadTexture("lamp")));
	    TextureModel barn = new TextureModel(OBJLoader.loadObjModel("barn", loader), new ModelTexture(loader.loadTexture("barn")));
	    
	    //----------------------------------------------------------------------------------------------------------------------------------------
	    
	    //----------------- ativa transparência e luz fake  ---------------------
	    
	    grass.getTexture().setHasTransparency(true);
	    grass.getTexture().setUseFakeLighting(true);
	    flower.getTexture().setHasTransparency(true);
	    flower.getTexture().setUseFakeLighting(true);
	    fern.getTexture().setHasTransparency(true);
	    
	    //-----------------------------------------------------------------------
	    	    
	    List<Entity> entities = new ArrayList<Entity>();
	    Random random = new Random(676452);
	    
	    for(int i=0;i<400;i++){ //são geradas 400 objetos (de cada) no terreno
	    		    	
	    	if(i%7 == 0) {
	    		
		    	entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 400 - 200,0, random.nextFloat() * -400), 0,0,0,1.8f));
		    	entities.add(new Entity(flower, new Vector3f(random.nextFloat() * 400 - 200,0, random.nextFloat() * -400), 0,0,0,2.3f));	
	    	}
	    	
	    	if(i%3 == 0) {
	    		
	    		entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 400 - 200,0, random.nextFloat() * -400), 0,random.nextFloat() * 360,0,0.9f));
	    		entities.add(new Entity(bobble, new Vector3f(random.nextFloat() * 800 - 400,0, random.nextFloat() * -600), 0,random.nextFloat() * 360,0,random.nextFloat() * 0.1f + 0.6f));
	    		entities.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 800 - 400,0, random.nextFloat() * -600), 0,0,0,random.nextFloat() * 1 + 4));
	    	}
	    }
	        
	    List<Light> lights = new ArrayList<Light>();
	    lights.add(new Light(new Vector3f(2000,4000,2000), new Vector3f(1,1,1f)));//sol
	    lights.add(new Light(new Vector3f(185,10,-293), new Vector3f(2,0,0), new Vector3f(1, 0.1f, 0.002f)));
	    lights.add(new Light(new Vector3f(380,10,-300), new Vector3f(0,2,2), new Vector3f(1, 0.1f, 0.002f)));
	    lights.add(new Light(new Vector3f(293,10,-305), new Vector3f(2,2,0), new Vector3f(1, 0.1f, 0.002f)));
	   
	    entities.add(new Entity(lamp, new Vector3f(185, 0, -293), 0, 0, 0, 1));
	    entities.add(new Entity(lamp, new Vector3f(380, 0, -300), 0, 0, 0, 1));
	    entities.add(new Entity(lamp, new Vector3f(293, 0, -305), 0, 0, 0, 1));
	    
	    
	    entities.add(new Entity(barn, new Vector3f(293, 15, -305), 0, 100, 0, 5));
	    
	    
	    Terrain terrain = new Terrain(-1,-1,loader, texturePack, blendMap);
	    Terrain terrain2 = new Terrain(0,-1,loader, texturePack, blendMap);
	    
	    MasterRenderer renderer = new MasterRenderer(loader);
	    
	    RawModel sheepModel = OBJLoader.loadObjModel("sheep", loader);
	    TextureModel stanfordSheep = new TextureModel(sheepModel, new ModelTexture(loader.loadTexture("sheep")));
	    
	    Player player = new Player(stanfordSheep, new Vector3f(153, 0, -274), 0, 100, 0, 5);
	    
	    
	    Camera camera = new Camera(player);
	    	       	    
	    while(!Display.isCloseRequested()){
	    	
	    	if(Keyboard.isKeyDown(Keyboard.KEY_C)) {
	    		camera.moveInThirdPerson();
	    	}else {
	    		camera.move();
	    	}
	    	
	        player.moveAuto(); 
	        renderer.processEntity(player);
	        renderer.processTerrain(terrain);
	        renderer.processTerrain(terrain2);
	        
	        for(Entity entity:entities){
	        	renderer.processEntity(entity);
	        }
	        renderer.render(lights, camera);
	        DisplayManager.updateDisplay();
	    }
 

	 
	    renderer.cleanUp();
	    loader.cleanUp();
	    DisplayManager.closeDisplay();
	}
}

/*exemplo de main para renderizar vários cubos de maneira aleatória
 * 
 * public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		RawModel model = OBJLoader.loadObjModel("ColourCube", loader);
		TextureModel cubeModel = new TextureModel(model,new ModelTexture(loader.loadTexture("cube")));
		Light light = new Light(new Vector3f(3000,2000,3000), new Vector3f(1,1,1));
		Camera camera = new Camera();
		List<Entity> allCubes = new ArrayList<Entity>;
		Random random = new Random;
		
		for(int i=0; i<200; i++){
			float x = random.nextFloat()* 100 - 50;
			float y = random.nextFloat()* 100 - 50;
			float z = random.nextFloat()* -300;
			allCubes.add(new Entity(cubeModel, new Vector3f(x,y,z), random.nextFloat() * 180f, random.nextFloat() * 180f,0f,1f));
		}
		
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()) {
			camera.move();
		
			for(Entity cube:allCubes){
				renderer.processEntity(cube);
			}
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
}*/

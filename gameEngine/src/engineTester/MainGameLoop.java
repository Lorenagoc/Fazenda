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
		
		/*Créditos para thinmatrix, principal fonte para a criação dessa cena*/
		
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
	    TextureModel fence = new TextureModel(OBJLoader.loadObjModel("fence", loader), new ModelTexture(loader.loadTexture("fence")));
	    TextureModel chicken = new TextureModel(OBJLoader.loadObjModel("chicken", loader), new ModelTexture(loader.loadTexture("chicken")));
	    TextureModel house = new TextureModel(OBJLoader.loadObjModel("house", loader), new ModelTexture(loader.loadTexture("house")));
	    TextureModel well = new TextureModel(OBJLoader.loadObjModel("well", loader), new ModelTexture(loader.loadTexture("well")));
	    
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
	    lights.add(new Light(new Vector3f(2000,4000,2000), new Vector3f(1.0f, 1.0f, 1.0f)));//sol
	    lights.add(new Light(new Vector3f(185,10,-293), new Vector3f(2,0,0), new Vector3f(1, 0.1f, 0.002f)));
	    lights.add(new Light(new Vector3f(380,10,-300), new Vector3f(0,2,2), new Vector3f(1, 0.1f, 0.002f)));
	    lights.add(new Light(new Vector3f(293,10,-305), new Vector3f(2,2,0), new Vector3f(1, 0.1f, 0.002f)));
	   
	    entities.add(new Entity(lamp, new Vector3f(185, 0, -293), 0, 0, 0, 1));
	    entities.add(new Entity(lamp, new Vector3f(380, 0, -300), 0, 0, 0, 1));
	    entities.add(new Entity(lamp, new Vector3f(293, 0, -305), 0, 0, 0, 1));
	      
	    entities.add(new Entity(barn, new Vector3f(293, 15, -360), 0, 100, 0, 5));
	    
	    entities.add(new Entity(house, new Vector3f(510, 0, -462), 0, 0, 0, 1));
	    
	    entities.add(new Entity(well, new Vector3f(492, 2, -462), 0, 0, 0, 2));
	    
	    entities.add(new Entity(chicken, new Vector3f(390, 0, -360), 0, 20, 0, 0.6f));
	    entities.add(new Entity(chicken, new Vector3f(395, 0, -368), 0, 45, 0, 0.6f));
	    entities.add(new Entity(chicken, new Vector3f(392, 0, -366), 0, 84, 0, 0.6f));
	    entities.add(new Entity(chicken, new Vector3f(380, 0, -364), 0, 90, 0, 0.6f));
	    entities.add(new Entity(chicken, new Vector3f(385, 0, -368), 0, 0, 0, 0.6f));
	    
	    
	    entities.add(new Entity(fence, new Vector3f(456, -11,-245), 0, 0, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(442, -11,-245), 0, 0, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(471, -11,-251), 0, 59, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(429, -11,-254), 0, -63, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(459, -11,-312), 0, 0, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(445, -11,-312), 0, 0, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(425, -11,-272), 0, -90, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(425, -11,-287), 0, -90, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(425, -11,-302), 0, -90, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(434, -11,-309), 0, 26, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(476, -11,-267), 0, 90, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(476, -11,-281), 0, 90, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(476, -11,-295), 0, 90, 0, 5));
	    entities.add(new Entity(fence, new Vector3f(466, -11,-310), 0, -67, 0, 5));
	    
	    
	    Terrain terrain = new Terrain(-1,-1,loader, texturePack, blendMap);
	    Terrain terrain2 = new Terrain(0,-1,loader, texturePack, blendMap);
	    
	    MasterRenderer renderer = new MasterRenderer(loader);
	    
	    RawModel sheepModel = OBJLoader.loadObjModel("sheep", loader);
	    TextureModel stanfordSheep = new TextureModel(sheepModel, new ModelTexture(loader.loadTexture("sheep")));
	    
	    Player playerMain = new Player(stanfordSheep, new Vector3f(444,30,-207), 0, 0, 0, 5);
	    	    
	    Camera camera = new Camera(playerMain);
	   
	    ArrayList<Player> curral = new ArrayList<>();
	    
	    //aqui 5 ovelhas que pulam e rodam, são geradas
	    for(int i = 0; i < 5 ; i++) {
	    	Player player =  new Player(stanfordSheep, new Vector3f(random.nextInt(50) + 414, 0, random.nextInt(50) - 320), 0, 100, 0, 5);
	    	curral.add(player);
	    }
	    	    	       	    
	    while(!Display.isCloseRequested()){
	    	
	    	//pressionando a tecla "c" a câmera, que muda para o fazendeiro, pode ser controlada pelo mouse e o fazendeiro com as telcas w,a,s,d
	    	//o zoom, quando a tecla c está pressionada, pode ser controlado pelo scroll do mouse
	    	//se nada for pressionado a câmera volta para o modo mundo, onde pode ser controlada pelas setas do teclado
	    	if(Keyboard.isKeyDown(Keyboard.KEY_C)) {
	    		camera.moveInThirdPerson();
	    	}else {
	    		camera.move();
	    	}
	    	
	    	playerMain.move();
	    	renderer.processEntity(playerMain);
	    		    	
	    	for(Player a : curral)
	    		 	a.moveAuto(); 
	    	
	        for(Player a : curral)
	        	renderer.processEntity(a);
	        
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

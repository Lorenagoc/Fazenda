package textures;

public class TerrainTexturePack {
	//essa classe irá conter todas as texturas de terrenos que iremos usar
	
	private TerrainTexture backgroundTexture;
	private TerrainTexture rTexture; 
	private TerrainTexture gTexture; 
	private TerrainTexture bTexture;
	
	//construtor
	public TerrainTexturePack(TerrainTexture backgroundTexture, TerrainTexture rTexture, TerrainTexture gTexture,
			TerrainTexture bTexture) {

		this.backgroundTexture = backgroundTexture;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
	}
	
	//get

	public TerrainTexture getBackgroundTexture() {
		return backgroundTexture;
	}

	public TerrainTexture getrTexture() {
		return rTexture;
	}

	public TerrainTexture getgTexture() {
		return gTexture;
	}

	public TerrainTexture getbTexture() {
		return bTexture;
	} 
}

package textures;

import java.nio.ByteBuffer;

public class TextureData {
	
	private int width;
    private int height;
    private ByteBuffer buffer;
    
    //construtor
    public TextureData(ByteBuffer buffer, int width, int height){
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }
    
    //get
    public int getWidth(){
        return width;
    }
     
    public int getHeight(){
        return height;
    }
     
    public ByteBuffer getBuffer(){
        return buffer;
    }

}

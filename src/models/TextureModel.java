package models;

import textures.ModelTexture;

public class TextureModel {
	//nessa classe trabalhamos com os modelos texturizados

	private RawModel rawModel;
	private ModelTexture texture;
	
	public TextureModel(RawModel model, ModelTexture texture) {
		
		this.rawModel = model;
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}
}

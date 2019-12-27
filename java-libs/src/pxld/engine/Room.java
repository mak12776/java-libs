package pxld.engine;

import java.util.ArrayList;

import pxld.Image;
import pxld.types.ColorMode;


public class Room 
{
	private Image image;
	private ArrayList<Sprite> sprites;
	
	public Room(Image image, ArrayList<Sprite> sprites)
	{
		if (sprites == null)
			sprites = new ArrayList<Sprite>();
		
		this.image = image;
		this.sprites = sprites;
	}
	
	public Room(ColorMode mode, int x, int y, ArrayList<Sprite> sprites)
	{
		this(new Image(mode, x, y), sprites);
	}
	
	public Image getImage()
	{
		return image;
	}
}

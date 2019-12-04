package libs.pxld.examples;

import libs.pxld.Image;
import libs.pxld.Picture.MapFunction;
import libs.pxld.types.ColorMode;
import libs.pxld.types.IntPoint;
import olds.pxld.MathTools;

public class ImageGenerators 
{
	public static Image image1(int x, int y)
	{
		Image image = new Image(ColorMode.L, x, y);
		IntPoint point = image.Center();
		double maxDist = image.width() / 2;
		
		image.map(new MapFunction() 
		{	
			@Override
			public void map(int x, int y, byte[] data, int offset) 
			{
				data[offset] = (byte) (MathTools.inverseDistanceFraction(x, y, point.x, point.y, maxDist) * 255);
			}
		});
		
		return image;
	}
}

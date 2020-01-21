
package pxld.examples;

import libs.tools.others.MathTools;
import pxld.Image;
import pxld.Picture.MapFunction;
import pxld.types.ColorMode;
import pxld.types.IntPoint;

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

	public static Image image2(IntPoint size)
	{
		Image image = new Image(ColorMode.L, size);

		return image;
	}
}

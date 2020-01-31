
package pxld;

import pxld.types.ColorMode;

public interface Picture
{
	byte[] getData();

	ColorMode getColorMode();

	int getColorWidth();

	int width();
	int height();

	public interface MapFunction
	{
		void map(int x, int y, byte[] data, int offset);
	}

	void draw(byte[] colorData);
	
	void map(MapFunction map);

	void drawPicture(int x, int y, Picture picture);
}

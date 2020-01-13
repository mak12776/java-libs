
package pxld;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import _olds.pxld.Random;
import pxld.types.ColorMode;
import pxld.types.IntPoint;

public class Image implements Picture
{
	private byte[] data;
	protected ColorMode mode;

	private int width;
	private int height;

	private int colorWidth;

	private int xWidth;
	private int yWidth;

	// [ simple functions ]

	public Image(ColorMode mode, int x, int y)
	{
		this.mode = mode;
		this.data = new byte[x * y * mode.getWidth()];

		this.width = x;
		this.height = y;

		this.colorWidth = mode.getWidth();

		this.xWidth = width * colorWidth;
		this.yWidth = height * xWidth;
	}

	public Image(ColorMode mode, IntPoint size)
	{
		this(mode, size.x, size.y);
	}

	// [ low-level info functions ]

	@Override
	public byte[] getData()
	{
		return data;
	}

	@Override
	public ColorMode getColorMode()
	{
		return mode;
	}

	@Override
	public int getColorWidth()
	{
		return colorWidth;
	}

	@Override
	public int width()
	{
		return width;
	}

	@Override
	public int height()
	{
		return height;
	}

	public int xWidth()
	{
		return xWidth;
	}

	public int yWidth()
	{
		return yWidth;
	}

	public int area()
	{
		return width * height;
	}

	public int xCenter()
	{
		return width / 2;
	}

	public int yCenter()
	{
		return height / 2;
	}

	public int xMax()
	{
		return width - 1;
	}

	public int yMax()
	{
		return height - 1;
	}

	public int xRandom()
	{
		return Random.intRange(0, width);
	}

	public int yRandom()
	{
		return Random.intRange(0, height);
	}

	// [ high-level info functions ]

	public IntPoint size()
	{
		return new IntPoint(width, height);
	}

	public IntPoint Center()
	{
		return new IntPoint(width / 2, height / 2);
	}

	public IntPoint Max()
	{
		return new IntPoint(width - 1, height - 1);
	}

	public IntPoint Random()
	{
		return Random.point(0, 0, width, height);
	}

	// [ low-level drawing functions ]

	@Override
	public void draw(byte[] colorData)
	{
		for (int i = 0; i < yWidth; i += colorWidth)
		{
			for (int c = 0; c < colorWidth; c += 1)
			{
				data[i + c] = colorData[c];
			}
		}
	}

	public void drawPoint(int x, int y, byte[] colorData)
	{
		int offset = (y * xWidth) + (x * colorWidth);
		for (int c = 0; c < colorWidth; c += 1)
		{
			data[offset + c] = colorData[c];
		}
	}

	public void drawRectangle(int x1, int y1, int x2, int y2, byte[] colorData)
	{
		for (int y = y1 * xWidth, yEnd = y2 * xWidth; y <= yEnd; y += xWidth)
		{
			for (int x = x1 * colorWidth, xEnd = x2 * colorWidth; x <= xEnd; x += colorWidth)
			{
				for (int c = 0; c < colorWidth; c += 1)
				{
					data[y + x + c] = colorData[c];
				}
			}
		}
	}

	@Override
	public void drawPicture(int x, int y, Picture picture)
	{
		if (picture instanceof Image)
		{
			drawImage(x, y, (Image) picture);
		} else if (picture instanceof View)
		{
			drawView(x, y, (View) picture);
		}
	}

	public void drawImage(int x, int y, Image image)
	{
		byte[] imageData = image.getData();

		for (int yOffset = y * xWidth, yEnd = (y + image.height()) * xWidth; yOffset < yEnd; yOffset += xWidth)
		{
			for (int xOffset = x * colorWidth, xEnd = (x + image.width()) * colorWidth; xOffset < xEnd; x += colorWidth)
			{
				for (int c = 0; c < colorWidth; c += 1)
				{
					data[yOffset + xOffset + c] = imageData[c];
				}
			}
		}
	}

	public void drawView(int x, int y, View view)
	{
		byte[] viewData = view.getData();

		for (int yOffset = y * xWidth, yViewOffset = view.yStart() * xWidth, yEnd = (y + view.height())
				* xWidth; yOffset < yEnd; yOffset += xWidth, yViewOffset += xWidth)
		{
			for (int xOffset = x * colorWidth, xViewOffset = view.xStart() * colorWidth, xEnd = (x + view.width())
					* colorWidth; xOffset < xEnd; x += colorWidth, xViewOffset += colorWidth)
			{
				for (int c = 0; c < colorWidth; c += 1)
				{
					data[yOffset + xOffset + c] = viewData[yViewOffset + xViewOffset + c];
				}
			}
		}
	}

	@Override
	public void map(MapFunction map)
	{
		for (int y = 0, yIndex = 0; y < yWidth; y += xWidth, yIndex += 1)
		{
			for (int x = 0, xIndex = 0; x < xWidth; x += colorWidth, xIndex += 1)
			{
				map.map(xIndex, yIndex, data, y + x);
			}
		}
	}

	// [ safe low-level functions ]

	public void safeDrawPoint(int x, int y, byte[] colorData)
	{
		if ((x < 0) || (x >= width) || (y < 0) || (y >= height))
			return;
		drawPoint(x, y, colorData);
	}

	// [ IO ]

	public void save(DataOutputStream stream) throws IOException
	{
		int colorWidth = mode.getWidth();

		stream.write(new byte[] { 'i', 'm', 'g', '.' });

		stream.writeByte(4); // size width
		stream.writeInt(colorWidth);
		stream.writeInt(width);
		stream.writeInt(height);

		stream.write(data);
	}

	public void save(String name) throws IOException
	{
		save(new DataOutputStream(new FileOutputStream(name)));
	}

	public boolean trySave(String name)
	{
		try
		{
			save(name);
		} catch (IOException e)
		{
			return false;
		}
		return true;
	}
}

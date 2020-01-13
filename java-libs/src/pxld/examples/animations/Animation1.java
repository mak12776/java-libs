
package pxld.examples.animations;

import _olds.pxld.Random;
import pxld.Animation;
import pxld.Image;
import pxld.Picture.MapFunction;
import pxld.types.ColorMode;

public class Animation1 extends Animation
{
	class Circle
	{
		int x;
		int y;

		double xSpeed;
		double ySpeed;

		double xAccelerate;
		double yAccelerate;

		public Circle(int x, int y, double xSpeed, double ySpeed, double xAccelerate, double yAccelerate)
		{
			this.x = x;
			this.y = y;
			this.xSpeed = xSpeed;
			this.ySpeed = ySpeed;
			this.xAccelerate = xAccelerate;
			this.yAccelerate = yAccelerate;
		}
	}

	private Circle[] circles;
	private int colorWidth;
	private int frameNumber = 30;

	public Animation1(int x, int y, int frameNumber)
	{
		image = new Image(ColorMode.RGB, x, y);
		this.colorWidth = image.getColorWidth();
		this.frameNumber = frameNumber;
	}

	@Override
	public void start()
	{
		circles = new Circle[100];
		for (int i = 0; i < circles.length; i += 1)
		{
			circles[i] = new Circle(image.xRandom(), image.yRandom(), Random.doubleRange(-5.0, 5.0),
					Random.doubleRange(-5.0, 5.0), 0, 0);
		}

		MapFunction func_1 = new MapFunction()
		{
			byte colorByte = 0;

			@Override
			public void map(int x, int y, byte[] data, int offset)
			{
				colorByte = (byte) (Math.pow(Math.sin(Math.random() * Math.PI / 2), 8) * (100 + 1));
				for (int c = 0; c < colorWidth; c += 1)
				{
					data[offset + c] = colorByte;
				}
			}
		};

		image.map(func_1);
	}

	@Override
	public boolean step()
	{
		if (frameNumber == 0)
			return false;

		frameNumber -= 1;
		return true;
	}
}

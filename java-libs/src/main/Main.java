package main;

import java.io.IOException;

import pxld.Image;
import pxld.examples.ImageGenerators;

public class Main 
{
	public static void main(String[] args) throws IOException
	{
		Image image = ImageGenerators.image1(1024, 1024);
		image.save("picture.img");
		System.out.println("Done");
	}
}

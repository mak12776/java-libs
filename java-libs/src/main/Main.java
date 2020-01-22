
package main;

import libs.buffers.Buffer;
import libs.tools.SystemTools;

public class Main
{
	public static void main(String[] args)
	{
		Buffer buffer = new Buffer(1024);
		
		if (args.length == 0)
		{
			System.out.println("usage: " + SystemTools.getProgramName() + " [FILE]...");
			return;
		}
		
	}
}

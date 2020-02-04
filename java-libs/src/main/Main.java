
package main;

import java.io.IOException;

import libs.buffers.immutable.BufferQueue;
import libs.tools.types.StringTools;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferQueue queue = new BufferQueue(20);
		
		queue.append(".Hello.".getBytes(), 0, true);
		String string = StringTools.byteArrayToString(queue.getBytes(), queue.getStart(), queue.getEnd(), '"', '"');
		System.out.println(string);
	}
}

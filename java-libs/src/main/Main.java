
package main;

import java.io.IOException;

import libs.buffers.BufferQueue;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		BufferQueue queue = new BufferQueue(20);
		
		queue.append("Hello".getBytes(), 0, true);
	}
}

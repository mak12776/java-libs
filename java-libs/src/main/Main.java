
package main;

import java.io.IOException;

import libs.buffers.Buffer;
import libs.bytes.ByteTest;
import libs.bytes.ByteTools;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		ByteTest test = new ByteTest()
		{
			@Override
			public boolean test(byte b)
			{
				// TODO Auto-generated method stub
				return false;
			}
		};
		Integer a = 10;
		Class<?> c = a.getClass();
		
		System.out.println(c.getTypeName());
		System.out.println(c.getName());
		System.out.println(c.getSimpleName());
		System.out.println(c.getCanonicalName());
	}
}

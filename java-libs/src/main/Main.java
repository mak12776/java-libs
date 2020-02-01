
package main;

import java.io.IOException;

import libs.bytes.ByteTools;
import libs.tools.types.StringTools;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		byte[] buffer = new byte[1024];
		byte[] hello = "Hello \n".getBytes();
		
		ByteTools.areaCopy(hello, 0, hello.length, buffer, 2, 10);
		
		System.out.println(StringTools.byteArrayToString(buffer, 0, buffer.length, "'", "'"));
	}
}


package tests.functions;

import java.io.IOException;

import libs.buffers.BufferViews;
import libs.exceptions.BaseException;
import libs.tools.types.ByteTools;
import libs.tools.types.StreamTools;

public class ToolsTest extends BaseTest
{
	public static void TestBufferLines(String name)
	{
		try
		{
			BufferViews bufferLines = StreamTools.readLines(name);
			for (int lnum = 0; lnum < bufferLines.views.length; lnum += 1)
			{
				output.println(lnum + ": [" + bufferLines.getLineString(lnum) + "]");
			}
		} catch (IOException | BaseException e)
		{
			e.printStackTrace();
		}
	}

	public static int compare(byte[] buffer, byte[] bytes)
	{
		return ByteTools.compare(buffer, 0, bytes, 0, buffer.length);
	}

	public void TestCompare()
	{
		byte[] a;
		byte[] b;

		a = new byte[] { 'a', 'b' };
		b = new byte[] { 'a', 'b' };
		output.println(compare(a, b));

		a = new byte[] { -128 };
		b = new byte[] { 127 };
		output.println(compare(a, b));

		a = new byte[] { 127 };
		b = new byte[] { -128 };
		output.println(compare(a, b));
	}
}

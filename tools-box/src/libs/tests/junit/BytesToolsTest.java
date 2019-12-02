package libs.tests.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import libs.tools.BytesTools;
import libs.types.ByteTest;

public class BytesToolsTest
{	
	public static byte[] Horse = "Horse".getBytes();
	public static byte[] Hello = "Hello".getBytes();
	public static byte[] Hell = "Hell".getBytes();
	public static byte[] Good = "Good".getBytes();
	public static byte[] sent = "Hello World, and Good Bye Every One.".getBytes();
	
	@Test
	public void testIsEqual()
	{
		assertTrue(BytesTools.isEqual(Hello, 0, Hello, 0, Hello.length));
		assertFalse(BytesTools.isEqual(Hello, 0, Horse, 0, Hello.length));
	}
	
	@Test
	public void testFind()
	{
		assertEquals(2, BytesTools.find(Hello, 0, Hello.length, ByteTest.isEqual('l')));
	}
	
	@Test
	public void testFindNot()
	{
		assertEquals(5, BytesTools.findNot(sent, 0, sent.length, ByteTest.isLetter));
		assertEquals(4, BytesTools.findNot(sent, 0, sent.length, ByteTest.inString("Hel")));
	}
	
	
}


package tests.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import libs.bytes.ByteTest;
import libs.functions.ByteFunctions;

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
		assertTrue(ByteFunctions.isEqual(Hello, 0, Hello, 0, Hello.length));
		assertFalse(ByteFunctions.isEqual(Hello, 0, Horse, 0, Hello.length));
	}

	@Test
	public void testFind()
	{
		assertEquals(2, ByteFunctions.find(Hello, 0, Hello.length, ByteTest.isEqual('l')));
	}

	@Test
	public void testFindNot()
	{
		assertEquals(5, ByteFunctions.findNot(sent, 0, sent.length, ByteTest.Class.isLetter));
		assertEquals(4, ByteFunctions.findNot(sent, 0, sent.length, ByteTest.inString("Hel")));
	}

}

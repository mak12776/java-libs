
package labs.safe;

import java.util.HashMap;

import labs.safe.types.oop.SafeBuffer;
import labs.tools.bytes.ByteArrayTools;
import labs.tools.bytes.ByteTools;
import labs.types.oop.buffers.BufferQueue;

public class SafeOptions
{
	// *** variables ***

	public static final boolean GLOBAL_SAFE = true;

	public static boolean get(Class<?> c)
	{
		Boolean result = options.get(c);
		if (result == null)
			throw new IllegalArgumentException("unknown class: " + c.getName());
		return result;
	}

	private static final HashMap<Class<?>, Boolean> options = new HashMap<Class<?>, Boolean>();

	private static void add(Class<?> c, boolean safe)
	{
		options.put(c, safe);
	}

	static
	{
		add(ByteTools.class, GLOBAL_SAFE);
		add(SafeBuffer.class, GLOBAL_SAFE);
		add(BufferQueue.class, GLOBAL_SAFE);
		add(ByteArrayTools.class, GLOBAL_SAFE);
	}
}

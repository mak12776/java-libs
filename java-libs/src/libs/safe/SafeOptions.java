package libs.safe;

import java.util.HashMap;

import libs.buffers.Buffer;
import libs.buffers.BufferQueue;
import libs.bytes.ByteArrayTools;
import libs.bytes.ByteTools;

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
	
	static {
		add(ByteTools.class, GLOBAL_SAFE);
		add(Buffer.class, GLOBAL_SAFE);
		add(BufferQueue.class, GLOBAL_SAFE);
		add(ByteArrayTools.class, GLOBAL_SAFE);
	}
}

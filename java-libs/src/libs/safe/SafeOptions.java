package libs.safe;

import java.util.HashMap;
import libs.bytes.ByteTools;

public class SafeOptions
{

	// *** variables ***
	
	public static final boolean GLOBAL_SAFE = true;
	
	public static boolean get(Class<?> c)
	{
		return options.get(c);
	}
	
	private static final HashMap<Class<?>, Boolean> options = new HashMap<Class<?>, Boolean>();
	
	private static void add(Class<?> c, boolean safe)
	{
		options.put(c, safe);
	}
	
	static {
		add(ByteTools.class, GLOBAL_SAFE);
	}
}

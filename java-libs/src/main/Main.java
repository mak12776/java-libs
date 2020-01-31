
package main;

public class Main
{
	public static final int END = 1000;
	
	public static final boolean SAFE = false;
	
	public static void fun1()
	{
		int number = 0;
		for (int index = 0; index < END; index += 1)
		{
			if (SAFE && (index % 2 == 0))
				number *= index;
		}
	}
	
	public static void fun2()
	{
		int number = 0;
		for (int index = 0; index < END; index += 1)
		{
			if (SAFE)
				if (index % 2 == 0)
					number *= index;
		}
	}
	
	public static boolean function1(final boolean test)
	{
		System.out.println("function1(" + test + ")");
		return test;
	}
	
	public static boolean function2(final boolean test)
	{
		System.out.println("function2(" + test + ")");
		return test;
	}
	
	public static void main(String[] args)
	{
		if (function1(false) && function2(true))
			System.out.println("true");
		
		for (int i = 1, j = 1; i < Integer.MAX_VALUE; i += 1)
			j *= i;
		
		long time1 = System.nanoTime();
		fun1();
		long time2 = System.nanoTime();
		fun2();
		long time3 = System.nanoTime();
		
		System.out.println(time2 - time1);
		System.out.println(time3 - time2);
	}
}

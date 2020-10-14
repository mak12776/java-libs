package main;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import libs.exceptions.BaseException;
import libs.tools.SystemTools;
import libs.tools.bytes.ByteTools;
import libs.tools.io.PrintTools;
import libs.tools.io.StreamTools;

public class CompressMain
{
	// main compress function
	
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: " + SystemTools.getProgramName() + " [FILE]");
			return;
		}
		byte[] buffer = null;
		
		try
		{
			buffer = StreamTools.readFile(args[0]);
		}
		catch (BaseException | IOException e)
		{
			e.printStackTrace();
			return;
		}
		
		System.out.println("File Name: " + args[0]);
		System.out.println("File Size: " + buffer.length);
		
		PrintTools.defaultWidth = 10;
		
		PrintTools.printSeparator();
		
		int[] counts = countEachByte(buffer);
		double[] percentageOfCounts = getPercentageOfInts(counts, buffer.length);
		
		System.out.println("print percentages more than 1%");
		
		for (int index = 0; index < counts.length; index += 1)
			if (percentageOfCounts[index] >= 1.0)
				System.out.printf("%02x: %.1f%% (%d)\n", index, percentageOfCounts[index], counts[index]);
		
		PrintTools.printSeparator();
	}
	
	public static double[] getPercentageOfInts(int[] counts, int total)
	{
		double[] result = new double[counts.length];
		for (int index = 0; index < counts.length; index += 1)
			result[index] = ((double)counts[index] / (double)total) * 100;
		return result;
	}
	
	public static int[] countEachByte(byte[] buffer)
	{
		int[] counts = new int[256];
		Arrays.fill(counts, 0);
		for (int index = 0; index < buffer.length; index += 1)
			counts[(int)buffer[index] & 0xFF] += 1;
		return counts;
	}
	
	// other functions
		
	private static void addToMap(HashMap<Integer, LinkedList<Integer>> map, int key, int value)
	{
		LinkedList<Integer> list = null;
		if (!map.containsKey(key))
		{
			list = new LinkedList<Integer>();
			map.put(key, list);
		}
		else
		{
			list = map.get(key);
		}
		list.add(value);
	}
	
	public static void findByteDuplications(byte[] buffer)
	{
		HashMap<Integer, LinkedList<Integer>> duplicates = new HashMap<Integer, LinkedList<Integer>>();
		
		outer_loop:
		for (int index = 0, maxIndex = buffer.length - 1; index < maxIndex; index += 1)
		{
			if (buffer[index] == buffer[index + 1])
			{
				int loopIndex = index + 2;
				
				if (loopIndex == buffer.length)
				{
					addToMap(duplicates, loopIndex - index, index);
					break;
				}
				
				while (buffer[index] == buffer[loopIndex])
				{
					loopIndex += 1;
					if (loopIndex == buffer.length)
					{
						addToMap(duplicates, loopIndex - index, index);
						break outer_loop;
					}
				}
				
				addToMap(duplicates, loopIndex - index, index);
				index = loopIndex - 1;
			}
		}
		
		for (Map.Entry<Integer, LinkedList<Integer>> entry : duplicates.entrySet())
		{
			System.out.println("for size: " + entry.getKey());
			for (int index : entry.getValue())
			{
				System.err.println("- " + index);
			}
		}
	}
	
	public static void countByteNumbers(byte[] buffer)
	{
		int[] byteNumbers = new int[1 << Byte.SIZE];
		Arrays.fill(byteNumbers, 0);
		
		for (int index = 0; index < buffer.length; index += 1)
		{
			byteNumbers[buffer[index] & 0xFF] += 1;
		}
		
		double[] bytePercent = new double[1 << Byte.SIZE];
		for (int index = 0; index < byteNumbers.length; index += 1)
		{
			bytePercent[index] = (double)byteNumbers[index] / (double)buffer.length * 100;
		}
		
		for (int index = 0; index < byteNumbers.length; index += 1)
		{
			if (byteNumbers[index] != 0)
			{
				System.out.println(String.format("number of %x: (%.1f) %d", index, bytePercent[index], byteNumbers[index]));
			}
		}
	}
	
	public static void oldMain(byte[] buffer)
	{
		// find repetitive duplicates
		findRepetitiveDuplicates(buffer, new AddDuplicateClass());
		
		// print formated duplicates
		
		System.out.println("number of keys: " + duplicates.size());
		PrintTools.printSeparator(10);
		
		for (Map.Entry<Long, LinkedList<Integer>> entry : duplicates.entrySet())
		{
			int value = (int)((entry.getKey() >> Integer.SIZE) & ByteTools.Longs.byteMask);
			int length = (int)(entry.getKey() & ByteTools.Longs.intMask);
			
			System.out.printf("for length: %d, value: %02x, counts: %d%n", length, value, entry.getValue().size());
		}
	}
	
	private static interface addDuplicateInterface
	{
		public void add(int index, int length, byte value);
	}
	
	private static void findRepetitiveDuplicates(byte[] buffer, addDuplicateInterface func)
	{
		int index = 0;
		int maxIndex = buffer.length - 1;
		
		outer_loop:
		while (index < maxIndex)
		{
			if (buffer[index] == buffer[index + 1])
			{
				int loopIndex = index + 2;
				
				if (loopIndex == buffer.length)
				{
					func.add(index, 2, buffer[index]);
					break;
				}
				
				while (buffer[loopIndex] == buffer[index])
				{
					loopIndex += 1;
					if (loopIndex == buffer.length)
					{
						func.add(index, loopIndex - index, buffer[index]);
						break outer_loop;
					}
				}
				
				func.add(index, loopIndex - index, buffer[index]);
				index = loopIndex;
			}
			else
			{
				index += 1;
			}
		}
	}
	
	private static HashMap<Long, LinkedList<Integer>> duplicates = new HashMap<Long, LinkedList<Integer>>();
	
	private static class AddDuplicateClass implements addDuplicateInterface
	{
		@Override
		public void add(int index, int length, byte value)
		{
			long key = (long)length | ((long)value << (long)Integer.SIZE);
			
			LinkedList<Integer> indexes = duplicates.get(key);
			if (indexes == null)
			{
				indexes = new LinkedList<Integer>();
				duplicates.put(key, indexes);
			}
			
			indexes.add(index);
		}
	}
}

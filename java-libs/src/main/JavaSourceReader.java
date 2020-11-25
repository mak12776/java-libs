package main;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import labs.tools.SystemTools;
import labs.tools.types.StringTools;

class DirectoryIterable implements Iterable<File>
{
	File rootFile;
	
	public DirectoryIterable(File file)
	{
		this.rootFile = file;
	}
	
	public DirectoryIterable(String path)
	{
		this.rootFile = new File(path);
	}
	
	public class DirectoryIterator implements Iterator<File>
	{
		public LinkedList<File> files;
		public boolean checkNext;
		
		@Override
		public boolean hasNext()
		{
			while (!files.isEmpty())
			{
				File file = files.peek();
				if (file.isDirectory())
					files.addAll(Arrays.asList(file.listFiles()));
				else 
					return true;
				files.pop();
			}
			return false;
		}

		@Override
		public File next()
		{
			if (!hasNext()) throw new NoSuchElementException();
			return files.poll();
		}
	}

	@Override
	public Iterator<File> iterator()
	{
		return new DirectoryIterator();
	}
}

public class JavaSourceReader
{
	private static Predicate<File> javaFormatPredicate = new Predicate<File>()
	{
		@Override
		public boolean test(File file)
		{
			return StringTools.endsWith(file.getName(), ".java");
		}
	};
	
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.err.println("usage: " + SystemTools.getProgramName() + " [DIR]");
			System.exit(0);
		}
	}
}

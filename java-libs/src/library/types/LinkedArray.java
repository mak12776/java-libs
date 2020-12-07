package library.types;

import java.util.ArrayList;

public class LinkedArray<T>
{
	public static class Node<T>
	{
		public ArrayList<T> list;
		Node<T> next;
		
		public Node()
		{
			list = null;
			next = null;
		}
		
		public Node(int size)
		{
			list = new ArrayList<>();
			next = null;
		}
	}
	
	public int arraySize;
	public int length;
	
	public Node<T> first;
	public Node<T> last;
	
	public LinkedArray(int arraySize)
	{
		this.arraySize = arraySize;
		this.length = 0;
	}
	
	public void add(T item)
	{
		if (length == 0)
			first = last = new Node<T>(this.arraySize);
		else if (length % arraySize == 0)
			last = (last.next = new Node<T>(this.arraySize));
		first.list.set(length % arraySize, item);
	}
}

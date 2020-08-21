
package libs.types.oop;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import libs.exceptions.UnimplementedCodeException;
import libs.tools.MathTools;

public class LinkedArrayList<T> implements List<T>
{
	private class ListNode<E>
	{
		public E[] array;
		public ListNode<E> next;
		
		@SuppressWarnings("unchecked")
		public ListNode(int arraySize)
		{
			array = (E[]) new Object[arraySize];
			next = null;
		}	
	}
	
	private ListNode<T> first;
	private ListNode<T> last;
	
	private int arraySize;
	private int nodeLength;
	private int lastLength;
	
	
	public LinkedArrayList(int arraySize)
	{
		first = last = null;
		this.arraySize = arraySize; 
		nodeLength = lastLength = 0;
	}

	@Override
	public boolean add(T e)
	{
		if (first == null)
		{
			first = last = new ListNode<T>(arraySize);
			last.array[0] = e;
			nodeLength = lastLength = 1;
		}
		else if (lastLength == arraySize)
		{
			last = (last.next = new ListNode<T>(arraySize));
			lastLength = 1;
			nodeLength += 1;
		}
		else
		{
			last.array[lastLength++] = e;
		}
		return true;
	}
	
	@Override
	public T get(int index)
	{
		if (index < 0)
			throw new IndexOutOfBoundsException("negative index: " + index);
		
		int baseIndex = index / arraySize;
		int nodeIndex = index % arraySize;
		
		if (baseIndex >= nodeLength || nodeIndex >= nodeLength)	
			throw new IndexOutOfBoundsException("invalid index: " + index);
		
		return (T) getNode(baseIndex).array[nodeIndex];
	}
	
	@Override
	public T set(int index, T element)
	{
		if (index < 0)
			throw new IndexOutOfBoundsException("negative index: " + index);
		
		int baseIndex = index / arraySize;
		int nodeIndex = index % arraySize;
		
		if (baseIndex >= nodeLength || nodeIndex >= nodeLength)	
			throw new IndexOutOfBoundsException("invalid index: " + index);
		
		return getNode(baseIndex).array[nodeIndex] = element;
	}
	
	@Override
	public void clear()
	{
		first = last = null;
		nodeLength = lastLength = 0;
	}
	

	@Override
	public boolean contains(Object o)
	{
		ListNode<T> temp = first;
		while (temp != null)
		{
			for (int index = 0; index < lastLength; index += 1)
			{
				if (o == null ? temp.array[index] == null : o.equals(temp.array[index]))
					return true;
			}
			temp = temp.next;
		}
		return false;
	}
	
	private ListNode<T> getNode(int baseIndex)
	{
		ListNode<T> temp = first;
		while (baseIndex != 0)
		{
			temp = temp.next;
			baseIndex -= 1;
		}
		return temp;
	}
	
	@Override
	public boolean isEmpty()
	{
		return first == null;
	}

	@Override
	public int size()
	{
		return MathTools.checkedMultiply(nodeLength, arraySize);
	}
	
	// *** TODO: write more functions later: unimplemented codes ***

	@Override
	public boolean containsAll(Collection<?> c)
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public Iterator<T> iterator()
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public int lastIndexOf(Object o)
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public ListIterator<T> listIterator()
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public ListIterator<T> listIterator(int index)
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex)
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public Object[] toArray()
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public void add(int index, Object element)
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public int indexOf(Object o)
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public T remove(int index)
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public boolean remove(Object o)
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public boolean addAll(Collection<? extends T> c)
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c)
	{
		throw new UnimplementedCodeException();
	}

	@Override
	public <N> N[] toArray(N[] a)
	{
		throw new UnimplementedCodeException();
	}
}

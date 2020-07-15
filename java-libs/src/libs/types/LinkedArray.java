
package libs.types;

public class LinkedArray<T>
{
	private static class ListNode<E>
	{
		public E[] array;
		public int size;
	}
	
	private ListNode<T> first;
	private ListNode<T> last;
	private int arraySize;
	private int size;
	
	
	public LinkedArray(int arraySize)
	{
		first = last = null;
		this.arraySize = arraySize; 
		size = 0;
	}
}

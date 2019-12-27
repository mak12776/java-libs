package libs.types;

public interface Stream<T> 
{
	T next();
	boolean hasNext();
}

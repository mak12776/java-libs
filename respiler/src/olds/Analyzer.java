package olds;

public class Analyzer 
{
	public LinkedBufferStream stream;
	public LinkedBuffer buffer;
	public int bufferSize;
	public Line line;
	
	public Analyzer(LinkedBufferStream stream, int bufferSize) 
	{
		this.stream = stream;
		this.buffer = null;
		this.bufferSize = bufferSize;
		this.line = null;
	}
}

package respiler;

import java.io.IOException;

import exceptions.BaseException;
import types.ByteTest;
import types.Line;

public class Main 
{
	public static final boolean SAFE = true;
	
	public static void main(String[] args) throws IOException, BaseException
	{
		Tests.TestAnalyzer();
	}
}

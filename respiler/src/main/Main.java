package main;

import java.io.IOException;

import tests.ToolsTest;

public class Main 
{
	public static final boolean SAFE = true;
	
	public static void main(String[] args)
	{
		ToolsTest.TestBufferLines("code.rest");
	}
}

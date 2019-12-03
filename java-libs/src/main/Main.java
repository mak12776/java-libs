package main;

import java.io.IOException;

import libs.types.ByteTest;

public class Main 
{
	public static final boolean SAFE = true;
	
	public static void main(String[] args) throws IOException
	{
		ByteTest test = ByteTest.isEqual('h');
		System.out.println(test.test((byte) 'h'));
		System.out.println(test.test((byte) 'a'));
	}
}

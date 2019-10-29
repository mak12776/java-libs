package respiler;

import java.io.IOException;
import java.io.PrintStream;

import exceptions.BaseException;
import exceptions.ParserException;
import respiler.Respiler.TokenStream;
import types.BufferLines;
import types.TokenType;
import types.tokens.Token;

public class Tests
{
	public static PrintStream output = System.out;
	public static String codeFileName = "code.rest";
	
	public static void TestAnalyzer()
	{
		BufferLines bufferLines = null;
		TokenStream stream = null;
		
		try 
		{
			bufferLines = Respiler.readLines(codeFileName);
			stream = Respiler.parseBufferLines(bufferLines);
			
			int lnum = 0;
			
			Token token;
			while ((token = stream.nextToken()) != null)
			{
				while (lnum <= token.startLine || (token.type == TokenType.MULTI_LINE_COMMENT && lnum <= token.endLine))
				{
					output.println(bufferLines.getLine(lnum));
					lnum += 1;
				}
				output.println(token);
			}
		}
		catch (IOException | BaseException e)
		{
			e.printStackTrace();
		}
		catch (ParserException e) 
		{
			System.out.println((char)bufferLines.buffer[e.startIndex]);
			e.printStackTrace();
		}
	}
}

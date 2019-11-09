package tests;

import java.io.IOException;
import java.io.PrintStream;

import exceptions.BaseException;
import exceptions.ParserException;
import respiler.Parser;
import respiler.Parser.TokenStream;
import respiler.types.tokens.Token;
import respiler.types.tokens.TokenType;
import tools.StreamTools;
import tools.types.BufferLines;

public class RespilerTest extends BaseTest
{
	public static String codeFileName = "code.rest";
	
	public static void TestAnalyzer()
	{
		BufferLines bufferLines = null;
		TokenStream stream = null;
		
		try 
		{
			bufferLines = StreamTools.readBufferLines(codeFileName);
			stream = Parser.parseBufferLines(bufferLines);
			
			int lnum = 0;
			
			Token token;
			while ((token = stream.nextToken()) != null)
			{
				while (lnum <= token.startLine || (token.type == TokenType.MULTI_LINE_COMMENT && lnum <= token.endLine))
				{
					output.println(bufferLines.getLineString(lnum));
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

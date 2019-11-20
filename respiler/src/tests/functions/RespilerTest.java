package tests.functions;

import java.io.IOException;
import java.io.PrintStream;

import respiler.OldParser;
import respiler.OldParser.TokenStream;
import respiler.exceptions.ParserException;
import respiler.types.tokens.TokenType;
import respiler.types.tokens.olds.OldToken;
import tools.StreamTools;
import tools.bytes.BufferUnpackedViews;
import tools.exceptions.BaseException;

public class RespilerTest extends BaseTest
{
	public static String codeFileName = "code.rest";
	
	public static void TestAnalyzer()
	{
		BufferUnpackedViews bufferLines = null;
		TokenStream stream = null;
		
		try 
		{
			bufferLines = StreamTools.readLines(codeFileName);
			stream = OldParser.parseBufferLines(bufferLines);
			
			int lnum = 0;
			
			OldToken token;
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

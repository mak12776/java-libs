package tests.functions;

import java.io.IOException;
import java.io.PrintStream;

import less.exceptions.ParserException;
import less.parsers.OldParser;
import less.parsers.OldParser.TokenStream;
import less.types.tokens.TokenType;
import less.types.tokens.olds.OldToken;
import libs.exceptions.BaseException;
import libs.tools.StreamTools;
import libs.types.bytes.BufferUnpackedViews;

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

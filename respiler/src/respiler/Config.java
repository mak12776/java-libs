package respiler;

import java.util.AbstractMap.SimpleEntry;

import respiler.types.tokens.TokenType;
import tools.ByteTools;
import tools.types.ByteTest;

import java.util.ArrayList;
import java.util.List;

public class Config 
{
	public static List<SimpleEntry<byte[], TokenType>> keywords = new ArrayList<SimpleEntry<byte[],TokenType>>();
	
	private static void put(String string, TokenType key)
	{
		keywords.add(new SimpleEntry<>(string.getBytes(), key));
	}
	
	{
		put("from",					TokenType.FROM);
		put("import", 				TokenType.IMPORT);
		put("as", 					TokenType.AS);
		
		put("goto",					TokenType.GOTO);
		put("label",				TokenType.LABEL);
		
		put("if", 					TokenType.IF);
		put("then", 				TokenType.THEN);
		put("else",					TokenType.ELSE);
		put("end",					TokenType.END);
		
		put("repeat", 				TokenType.REPEAT);
		put("for", 					TokenType.FOR);
		put("in", 					TokenType.IN);
		put("while", 				TokenType.WHILE);
		
		put("switch", 				TokenType.SWITCH);
		put("case",  				TokenType.CASE);
		put("default",  			TokenType.DEFAUL);
		
		put("break",  				TokenType.BREAK);
		put("continue",  			TokenType.CONTINUE);
		
		put("var", 					TokenType.VAR);
		put("const", 				TokenType.CONST);
		put("let",  				TokenType.LET);
		put("ref",  				TokenType.REF);
		
		put("type", 				TokenType.TYPE);
		put("untype", 				TokenType.UNTYPE);
		
		put("struct", 				TokenType.STRUCT);
		put("class", 				TokenType.CLASS);
		put("self", 				TokenType.SELF);
		
		put("public", 				TokenType.PUBLIC);
		put("private", 				TokenType.PRIVATE);
		
		put("true", 				TokenType.TRUE);
		put("false", 				TokenType.FALSE);
		put("null", 				TokenType.NULL);
	}
	
	public static TokenType searchKeyword(byte[] bytes, int offset, int length)
	{
		byte[] key;
		
		for (int i = 0; i < keywords.size(); i += 1)
		{
			key = keywords.get(i).getKey();
			if (key.length == length)
			{
				for (int j = 0; j < key.length; j += 1)
				{
					if (key[j] != bytes[offset + j])
					{
						return null;
					}
				}
				return keywords.get(i).getValue();
			}
		}
		return null;
	}
	
	public static boolean matchKeywordPattern(byte[] bytes)
	{
		if (bytes.length != 0)
		{
			for (int i = 0; i < bytes.length; i += 1)
			{
				if (!ByteTools.isLower(bytes[i]))
				{
					return true;
				}
			}
			return false;
		}
		return true;
	}
}

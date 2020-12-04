
package labs.faces.cface;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

public class CFace
{
	public static <T> List<T> newList()
	{
		return new LinkedList<T>();
	}

	public static EnumMap<Key, byte[]> keywords = new EnumMap<CFace.Key, byte[]>(Key.class);

	private static void insert(Key key, String value)
	{
		keywords.put(key, value.getBytes());
	}

	public static List<Key> checkKeywords()
	{
		List<Key> keys = new LinkedList<CFace.Key>();
		for (Key key : Key.values())
			if (!CFace.keywords.containsKey(key))
				keys.add(key);
		return keys;
	}

	public enum Key
	{
		MACRO_PREFIX, MACRO_INCLUDE, MACRO_DEFINE, MACRO_UNDEF,

		MACRO_IF, MACRO_IFDEF, MACRO_IFNDEF, MACRO_ELSE, MACRO_ELIF, MACRO_ENDIF,

		AUTO, EXTERN, STATIC, REGISTER, CONST,

		STRUCT, UNION, ENUM, IF, ELSE, FOR, WHILE, SWITCH, CASE, DEFAULT, BREAK, CONTINUE,

		AND, OR, NOT, EQ, NE, LT, LE, GT, GE,

		ADD, SUB, MUL, DIV, MOD, BIT_AND, BIT_OR, BIT_XOR, BIT_NOT, SHIFT_LEFT, SHIFT_RIGHT,

		ASSIGN,

		ADD_ASSIGN, SUB_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, BIT_AND_ASSIGN, BIT_OR_ASSIGN, BIT_XOR_ASSIGN,
		SHIFT_LEFT_ASSIGN, SHIFT_RIGHT_ASSIGN,
	}

	static
	{
		insert(Key.MACRO_PREFIX, "#");
		insert(Key.MACRO_INCLUDE, "include");
		insert(Key.MACRO_DEFINE, "define");
		insert(Key.MACRO_UNDEF, "undef");

		insert(Key.MACRO_IF, "if");
		insert(Key.MACRO_IFDEF, "ifdef");
		insert(Key.MACRO_IFNDEF, "ifndef");
		insert(Key.MACRO_ELIF, "elif");
		insert(Key.MACRO_ELSE, "else");
		insert(Key.MACRO_ENDIF, "endif");

		insert(Key.AUTO, "auto");
		insert(Key.EXTERN, "extern");
		insert(Key.STATIC, "static");
		insert(Key.REGISTER, "register");
		insert(Key.CONST, "const");

		insert(Key.STRUCT, "struct");
		insert(Key.UNION, "union");
		insert(Key.ENUM, "enum");
		insert(Key.IF, "if");
		insert(Key.ELSE, "else");
		insert(Key.FOR, "for");
		insert(Key.WHILE, "while");
		insert(Key.SWITCH, "switch");
		insert(Key.CASE, "case");
		insert(Key.DEFAULT, "default");
		insert(Key.BREAK, "break");
		insert(Key.CONTINUE, "continue");

		insert(Key.AND, "&&");
		insert(Key.OR, "||");
		insert(Key.NOT, "!");
		insert(Key.EQ, "==");
		insert(Key.NE, "!=");
		insert(Key.LT, "<");
		insert(Key.LE, "<=");
		insert(Key.GT, ">");
		insert(Key.GE, ">=");

		insert(Key.ADD, "+");
		insert(Key.SUB, "-");
		insert(Key.MUL, "*");
		insert(Key.DIV, "/");
		insert(Key.MOD, "%");
		insert(Key.BIT_AND, "&");
		insert(Key.BIT_OR, "|");
		insert(Key.BIT_XOR, "^");
		insert(Key.BIT_NOT, "~");
		insert(Key.SHIFT_LEFT, "<<");
		insert(Key.SHIFT_RIGHT, ">>");

		insert(Key.ASSIGN, "=");
		insert(Key.ADD_ASSIGN, "+=");
		insert(Key.SUB_ASSIGN, "-=");
		insert(Key.MUL_ASSIGN, "*=");
		insert(Key.DIV_ASSIGN, "/=");
		insert(Key.MOD_ASSIGN, "%=");
		insert(Key.BIT_AND_ASSIGN, "&=");
		insert(Key.BIT_OR_ASSIGN, "|=");
		insert(Key.BIT_XOR_ASSIGN, "^=");
		insert(Key.SHIFT_LEFT_ASSIGN, "<<=");
		insert(Key.SHIFT_RIGHT_ASSIGN, ">>");
	}
}

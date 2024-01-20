/*
 * Class: CS 4308 Section 1
 * Term: Spring 2021
 * Names: Matt Gibson,
 * 		Souleymane Camara,
 * 		and Zhuoxing Wang
 * Instructor: Deepa Muralidhar
 * Project: Deliverable 3 Scanner, Parser, and Interpreter - Java
 */

// Token.java acts as a data structure to assist the scanner

public class Token
{
	// Instance variables
	
	// TokenType type indicates the specific token identifier
	public TokenType type;
	
	// String lexeme contains the value/name of the token as a String
	public String lexeme;
	
	// int reference stores the ID code for keywords and identifiers
	public int reference;
	
	// int line stores the line number of the token in the original file
	public int line;
	
	// enum TokenType provides an easy way to list possible token types in a
	// format that is easy to add to in the case of BASIC coverage expansion
	public enum TokenType
	{
		KEYWORD
		{
			public String toString()
			{
				return("KEYWORD");
			}
		},
		STRING_LITERAL
		{
			public String toString()
			{
				return("STRING_LITERAL");
			}
		},
		DECIMAL_CONSTANT
		{
			public String toString()
			{
				return("DECIMAL_CONSTANT");
			}
		},
		INTEGER_CONSTANT
		{
			public String toString()
			{
				return("INTEGER_CONSTANT");
			}
		},
		
		IDENTIFIER
		{
			public String toString()
			{
				return("IDENTIFIER");
			}
		},
		MODULE_IDENTIFIER
		{
			public String toString()
			{
				return("MODULE_IDENTIFIER");
			}
		},
		SUB_IDENTIFIER
		{
			public String toString()
			{
				return("SUB_IDENTIFIER");
			}
		},
		FUNCTION_IDENTIFIER
		{
			public String toString()
			{
				return("FUNCTION_IDENTIFIER");
			}
		},
		INTEGER_IDENTIFIER
		{
			public String toString()
			{
				return("INTEGER_IDENTIFIER");
			}
		},
		DECIMAL_IDENTIFIER
		{
			public String toString()
			{
				return("DECIMAL_IDENTIFIER");
			}
		},
		STRING_IDENTIFIER
		{
			public String toString()
			{
				return("STRING_IDENTIFIER");
			}
		},
		BOOLEAN_IDENTIFIER
		{
			public String toString()
			{
				return("BOOLEAN_IDENTIFIER");
			}
		},
		
		SPACE
		{
			public String toString()
			{
				return("SPACE");
			}
		},
		NEW_LINE
		{
			public String toString()
			{
				return("\nNEW_LINE");
			}
		},
		TAB
		{
			public String toString()
			{
				return("TAB");
			}
		},
		EQUALS
		{
			public String toString()
			{
				return("EQUALS");
			}
		},
		LEFT_PARENTHESES
		{
			public String toString()
			{
				return("LEFT_PARENTHESES");
			}
		},
		RIGHT_PARENTHESES
		{
			public String toString()
			{
				return("RIGHT_PARENTHESES");
			}
		},
		DOT
		{
			public String toString()
			{
				return("DOT");
			}
		},
		PLUS
		{
			public String toString()
			{
				return("PLUS");
			}
		},
		AND
		{
			public String toString()
			{
				return("AND");
			}
		},
		GREATER_THAN
		{
			public String toString()
			{
				return("GREATER_THAN");
			}
		},
		LESS_THAN
		{
			public String toString()
			{
				return("LESS_THAN");
			}
		},
		COMMA
		{
			public String toString()
			{
				return("COMMA");
			}
		},
		SLASH
		{
			public String toString()
			{
				return("SLASH");
			}
		},
		STAR
		{
			public String toString()
			{
				return("STAR");
			}
		},
		MINUS
		{
			public String toString()
			{
				return("MINUS");
			}
		}
	}
	
	// Constructor takes in initialization values for the variables
	public Token(TokenType t, String val, int ref, int lineNumber)
	{
		lexeme = val;
		reference = ref;
		type = t;
		line = lineNumber;
	}
	
	// toString method included only for demonstration in the main method
	public String toString()
	{
		String str = lexeme + "(";
		str += type.toString();
		str += ")";
		return(str);
	}
}
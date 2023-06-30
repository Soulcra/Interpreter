# Interpreter
The scanner is the software module that performs lexical analysis. The scanner has the following goals:
	•	Recognize the next token in the source line
	•	Recognize if the token is a keyword in the language by a lookup in the keyword table, return the number code for the keyword
	•	If not a keyword, return the numeric code for the token: integer identifier, float identifier, string identifier, boolean identifier, keyword (one number code for each keyword), a constant (one number code for each type), string literal.
	•	Store the name of the identifier in a global variable
	•	Store the value of each constant or literal in a global variable. I recommend a global variable for every type of constant or literal.
	•	Store the number of the source line scanned in a variable.

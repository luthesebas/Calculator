package calculator.parser;

/**
 *
 */
public class RecursiveDescentParser {

	private String expression; // expression to parse
	private char character; // current character of the expression
	private int position; // current position of the character
	
	private boolean isParsed; // indicates if the expression has been already parsed
	private double solution; // solution of the expression
	
	/**
	 * Constructs a recursive descent parser for arithmetic expressions
	 */
	public RecursiveDescentParser() {
		// ...
	}
	
	/**
	 * Constructs a recursive descent parser for arithmetic expressions.
	 * @param expression Arithmetic expression to parse
	 */
	public RecursiveDescentParser(String expression) {
		init(expression);
	}
	

    /**
     * Parses the current expression
     * @return
     */
    public double parse() {    	
    	if (this.isParsed) {
    		return this.solution;
    	} else {
    		return parse(this.expression);
    	}
    }
    
    /**
     * Parses the given arithmetic expression.
     * @param expression Arithmetic expression to parse
     * @return
     */
    public double parse(String expression) {
		init(expression);
        
		nextChar();
        double x = parseExpression();

        this.isParsed = true;

        // Error?
        if (this.position < this.expression.length()) {
        	//throw new RuntimeException("Unexpected character '" + this.character + "' at position: " + this.position);
        	System.out.println("Unexpected character '" + this.character + "' at position: " + this.position);
        	return this.solution = Double.NaN;
        }
        
    	return this.solution = x;
    }
    
    /**
     * Defines the new arithmetic expression to parse.
     * @param expression Arithmetic expression
     */
    public void setExpression(String expression) {
    	init(expression);
    }
    
    /**
     * Initializes the recursive descent parser.
     * @param expression Arithmetic expression
     */
    private void init(String expression) {
    	if (expression == null) {
			//throw new RuntimeException("No arithmetic expression to parse");
        	System.out.println("No arithmetic expression to parse");
    		this.isParsed = true;
    		this.expression = null;
    	} else {
    		this.isParsed = false;
    		this.expression = expression.replaceAll(" ", ""); // remove all spaces and initialize expression
		}
    	
		this.position = -1;
		this.character = 0;
		this.solution = Double.NaN;
    }
    
	/**
	 * Reads the next character of the given arithmetic expression.
	 */
    private void nextChar() {
        if (++this.position < this.expression.length()) {
        	this.character = this.expression.charAt(this.position);
        } else {
        	this.character = 0;
        }
    }
    
    /*
     * Arithmetic grammar:
     * 
     * expression 	::= expression "+" term | expression "-" term | term
     * term 		::= term "*" power | term "%" power | term "/" power | power
     * power		::= factor "^" power | factor
     * factor		::= "-" function | "+" function | function
     * function		::= "sin" factor | "cos" factor | "tan" factor | "sqrt" factor | numeral
     * numeral		::= "(" expression ")" | number
     */

    /**
     * Parses the arithmetic expression. Grammar:
     * <pre> expression ::= expression "+" term | expression "-" term | term </pre>
     * @return The value of the expression or {@code Double.NaN}
     */
    private double parseExpression() {
        double x = parseTerm();
        
        while (this.position < this.expression.length()) {
            switch(this.character) {
	        	case '+': nextChar(); x += parseTerm(); break;
	        	case '-': nextChar(); x -= parseTerm(); break;
	        	default: return x;
            }
        } //while
        
        return x;
    }

    /**
     * Parses the term expression. Grammar:
     * <pre> term ::= term "*" power | term "%" power | term "/" power | power </pre>
     * @return The value of the term expression or {@code Double.NaN}
     */
    private double parseTerm() {
        double x = parsePower();
        
        while (true) {
            switch(this.character) {
	        	case '*': nextChar(); x *= parseTerm(); break;
	        	case '/': nextChar(); x /= parseTerm(); break;
	        	case '%': nextChar(); x %= parseTerm(); break;
	        	//case '^': nextChar(); x = Math.pow(x, parseFactor()); break;
	        	default: return x;
            }
        } //while
    }
    
    /**
     * Parses the power expression. Grammar:
     * <pre> power ::= factor "^" power | factor </pre>
     * @return The value of the power expression or {@code Double.NaN}
     */
    private double parsePower() {
        double x = parseFactor();
        
        while (true) {
            if (this.character == '^') {
            	nextChar(); 
            	x = Math.pow(x, parsePower());
            } else {
	        	return x;
            }
        } //while
    }

    /**
     * Parses the factor expression. Grammar:
     * <pre> factor ::= "-" function | "+" function | function </pre>
     * @return The value of the factor expression or {@code Double.NaN}
     */
    private double parseFactor() {   	
    	if (this.character == '-') { // unary minus
    		nextChar(); 
    		return - parseFunction();	  
    	} else if (this.character == '+') { // unary plus
    		nextChar();
    	}
    	
    	return parseFunction();
    }
    
    /**
     * Parses the function expression. Grammar:
     * <pre> function ::= "sin" factor | "cos" factor | "tan" factor | "sqrt" factor | numeral </pre>
     * @return The value of the function expression or {@code Double.NaN}
     */
    private double parseFunction() {
        double x;
        int startPos = this.position;  
        
    	if (this.character >= 'a' && this.character <= 'z') { // functions
			while (this.character >= 'a' && this.character <= 'z') { // read the function name
				nextChar();
			}
			String func = this.expression.substring(startPos, this.position);
			
			x = parseFactor(); // calculate function input parameter
			
			if (func.equalsIgnoreCase("sin")) return Math.sin(Math.toRadians(x)); // sin;
			if (func.equalsIgnoreCase("cos")) return Math.cos(Math.toRadians(x)); // cos;
			if (func.equalsIgnoreCase("tan")) return Math.tan(Math.toRadians(x)); // tan;
			if (func.equalsIgnoreCase("sqrt")) return Math.sqrt(x); // square root
			else {
				//throw new RuntimeException("Unknown function '" + func + "' at position: " + startPos);
				System.out.println("Unknown function '" + func + "' at position: " + startPos);
				return Double.NaN;
			}
        } else {
        	return parseNumeral();
        }
    }
    
    /**
     * Parses the numeral expression. Grammar:
     * <pre> numeral ::= "(" expression ")" | number </pre>
     * @return The value of the numeral expression or {@code Double.NaN}
     */
    private double parseNumeral() {  
        double x;
        int startPos = this.position;       
        
        // numeral ::= "(" expression ")"
        if (this.character == '(') { // left parentheses
        	nextChar();
        	x = parseExpression();
            if (this.character == ')') {
            	nextChar(); // right parentheses
            } else {
            	//throw new RuntimeException("Unclosed parentheses at: " + this.position);
            	System.out.println("Unclosed parentheses at: " + this.position);
            	return Double.NaN;
            }
        // numeral ::= number
        } else if ((this.character >= '0' && this.character <= '9') || this.character == '.') { // numbers
            x = readNumber(startPos);
        } else {
        	//throw new RuntimeException("Unexpected character: " + (char)this.character);
        	System.out.println("Unexpected character: '" + (char)this.character + "' at position: " + this.position);
			return Double.NaN;
        }
        
        return x;
    } //parseFactor
    
    /**
     * Reads the complete number.
     * @param startPos Character start position of the number of the expression
     * @return The value of number or {@code Double.NaN}
     */
    private double readNumber(int startPos) {
        while ((this.character >= '0' && this.character <= '9') || this.character == '.') { // read full number
        	nextChar();
        }
        
    	String num = this.expression.substring(startPos, this.position);
        try {
        	return Double.parseDouble(num);
        } catch (NumberFormatException e) {
			//throw new RuntimeException("Invalid number '" + num + "' at position: " + startPos);
			System.out.println("Invalid number '" + num + "' at position: " + startPos);
        	return Double.NaN;
        }
    }
    
   
    
}



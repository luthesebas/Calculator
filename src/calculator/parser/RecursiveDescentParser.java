package calculator.parser;

/**
 *
 */
public class RecursiveDescentParser {

	private final String expression;
	private int position;
	private char character;
	
	/**
	 * 
	 */
	public RecursiveDescentParser(String expression) {
		this.position = -1;
		this.character = 0;
		// Remove all spaces and initialize expression
		this.expression = expression.replaceAll(" ", ""); 
	}
	
	/**
	 * Reads the next character of the given expression.
	 */
    private void nextChar() {
        if (++this.position < this.expression.length()) {
        	this.character = this.expression.charAt(this.position);
        } else {
        	this.character = 0;
        }
    }

    /**
     * 
     * @return
     */
    public double parse() {    	
        nextChar();
        double x = parseExpression();
        if (this.position < this.expression.length()) throw new RuntimeException("Unexpected: " + (char)this.character);
        return x;
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
     * expression ::= expression "+" term | expression "-" term | term
     * @return
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
     * term ::= term "*" power | term "%" power | term "/" power | power
     * @return
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
     * power ::= factor "^" power | factor
     * @return
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
     * factor ::= "-" function | "+" function | function
     * @return
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
     * function	::= "sin" factor | "cos" factor | "tan" factor | "sqrt" factor | numeral
     * @return
     */
    private double parseFunction() {
        int startPos = this.position;   
        
    	if (this.character >= 'a' && this.character <= 'z') { // functions
            String func = readFunction(startPos);
            return calculateFunction(func, parseFactor());
        } else {
        	return parseNumeral();
        }
    }
    
    /**
     * numeral ::= "(" expression ")" | number
     * @return
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
        	System.out.println("Unexpected character: " + (char)this.character);
			return Double.NaN;
        }
        
        return x;
    } //parseFactor
    
    /**
     * Reads the complete number
     * @param startPos
     * @return
     */
    private double readNumber(int startPos) {
        while ((this.character >= '0' && this.character <= '9') || this.character == '.') { // read full number
        	nextChar();
        }
        
        try {
        	return Double.parseDouble(this.expression.substring(startPos, this.position));
        } catch (NumberFormatException e) {
        	return Double.NaN;
        }
    }
	
    /**
     * Reads the function name
     * @param startPos
     * @return
     */
    private String readFunction(int startPos) {
        while (this.character >= 'a' && this.character <= 'z') {
        	nextChar();
        }
       return this.expression.substring(startPos, this.position);
    }
    
    /**
     * Calculate the function with input parameter
     * @param func
     * @param x
     * @return
     */
    private double calculateFunction(String func, double x) {
		if (func.equalsIgnoreCase("sin")) return Math.sin(Math.toRadians(x)); // sin;
		if (func.equalsIgnoreCase("cos")) return Math.cos(Math.toRadians(x)); // cos;
		if (func.equalsIgnoreCase("tan")) return Math.tan(Math.toRadians(x)); // tan;
		if (func.equalsIgnoreCase("sqrt")) return Math.sqrt(x); // square root
		else {
			//throw new RuntimeException("Unknown function: " + func);
			System.out.println("Unknown function: " + func);
			return Double.NaN;
		}
    }

    
    
}



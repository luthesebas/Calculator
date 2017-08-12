/**
 * 
 */
package calculator.parser;

/**
 * 
 */
public abstract class FirstFollow {

	public static final String E_1 = "(i";
	public static final String E_2 = "+";
	public static final String E_3 = "-";
	public static final String E_4 = "#)";
	
	public static final String T_1 = "(i";
	public static final String T_2 = "*";
	public static final String T_3 = "/";
	public static final String T_4 = "%";
	public static final String T_5 = "#)+-";
	
	public static final String P_1 = "(i";
	public static final String P_2 = "^";
	public static final String P_3 = "#)(i+-*/%";
	
	public static final String F_1 = "(i";
	public static final String F_2 = "+";
	public static final String F_3 = "-";
	public static final String F_4 = "#)(i*/%^";
	
	public static final String N_1 = "(";
	public static final String N_2 = "i";
	
}

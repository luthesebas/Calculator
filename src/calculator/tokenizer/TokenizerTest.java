/**
 * 
 */
package calculator.tokenizer;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 */
public class TokenizerTest {

	@Test
	public void test() {
		String[] expected = new String[]{
				"45", "*", "5", "/", "0.7", "+",
				"(", "2.", "-", "5", "%", "3", ")"
			};
		Tokenzier scanner = new Tokenzier("45*5/0.7+(2.-5%3)");
		
		Token token = scanner.getToken();
		
		int i = 0;
		while (token.ID != TokenID.END_OF_EXPRESSION) {
			assertEquals(expected[i], token.VALUE);
			
			i++;
			token = scanner.getToken();
		}//while
	}

}

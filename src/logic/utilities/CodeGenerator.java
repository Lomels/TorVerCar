package logic.utilities;

import java.security.SecureRandom;

public class CodeGenerator {
	private static final String ALLOWED = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
	private static SecureRandom rnd = new SecureRandom();
	private static int len = 16;
	
	private CodeGenerator() {
		throw new IllegalStateException("Utility class");
	}
	
	public static String randomCode() {
		   StringBuilder sb = new StringBuilder( len );
		   for( int i = 0; i < len; i++ ) 
		      sb.append( ALLOWED.charAt( rnd.nextInt(ALLOWED.length()) ) );
		   return sb.toString();		
	}
}

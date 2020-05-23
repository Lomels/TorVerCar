package logic.utilities;

import java.security.SecureRandom;

public class CodeGenerator {
	private static final String allowed = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom rnd = new SecureRandom();
	private static int len = 16;
	
	public static String randomCode() {
		   StringBuilder sb = new StringBuilder( len );
		   for( int i = 0; i < len; i++ ) 
		      sb.append( allowed.charAt( rnd.nextInt(allowed.length()) ) );
		   return sb.toString();		
	}
}

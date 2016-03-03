package src.main.java.admin.utils;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.digest.DigestUtils;

public class EncriptarContrasenha {
	
	public static void main(String[] args){
	
		
		//original
	String textoSinEncriptar="1"; 
//	String textoEncriptadoConSHA=DigestUtils.sha1Hex(textoSinEncriptar); 
//	
//	
//	
//	System.out.println ("Texto Encriptado con SHA : "+textoEncriptadoConSHA);
		//original
	
	try {

	 String secretString = textoSinEncriptar;
	 
    // Generate a temporary key for this example. In practice, you would
    // save this key somewhere. Keep in mind that you can also use a
    // Pass Phrase.
    SecretKey desKey       = KeyGenerator.getInstance("DESede").generateKey();
    

    // Create encrypter/decrypter class
    StringEncrypter desEncrypter = new StringEncrypter(desKey, desKey.getAlgorithm());

    
    // Encrypt the string
    String desEncrypted       = desEncrypter.encrypt(secretString);

    
    // Decrypt the string
    String desDecrypted       = desEncrypter.decrypt(desEncrypted);

    
    // Print out values
    System.out.println(desKey.getAlgorithm() + " Encryption algorithm");
    System.out.println("    Original String  : " + secretString);
    System.out.println("    Encrypted String : " + desEncrypted);
    System.out.println("    Decrypted String : " + desDecrypted);
    System.out.println();
	 } catch (NoSuchAlgorithmException e) {
     }
		
		
	
	}
}

import java.io.BufferedReader;

public abstract class CryptographyMethod {

	public abstract void encrypt(BufferedReader in) throws Exception;

	public abstract void decrypt(BufferedReader in) throws Exception;

	public abstract void attack(BufferedReader in) throws Exception;
	
	public abstract void showPrivateInfo();
	
	public abstract void showPublicInfo();
}

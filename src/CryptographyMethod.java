import java.io.BufferedReader;

public abstract class CryptographyMethod {

	public abstract void encryptInput(BufferedReader in) throws Exception;

	public abstract void decryptInput(BufferedReader in) throws Exception;

	public abstract void attackInput(BufferedReader in) throws Exception;

	public abstract void showPrivateInfo();

	public abstract void showPublicInfo();

	public abstract void generateNewData();
}

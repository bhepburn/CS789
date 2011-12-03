import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Test {

	public static void main(String[] args) {

		InputStreamReader stream = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(stream);
		String input = "";

		boolean exit = false;
		while (!exit) {
			System.out
					.print("Please choose an cryptography method by choosing (1) RSA or (2) El Gamal (any other input will exit): ");
			try {
				input = in.readLine();
				int choice = Integer.parseInt(input);
				if (choice == 1) {
					System.out.print("\nRSA chosen: ");
					choice(in);
				} else if (choice == 2) {
					System.out.print("\nEl Gamal chosen: ");
				} else {
					exit = true;
				}
			} catch (Exception e) {
				exit = true;
			}
			System.out.println("");
		}
		System.out.println("Exiting program!");
	}

	public static void choice(BufferedReader in) {
		boolean exit = false;
		while (!exit) {
			try {
				System.out
						.print("Would you like to (1) encrypt, (2) decrypt, (3) generate private/public info, or (4) to exit? ");
				String input = in.readLine();
				int choice = Integer.parseInt(input);
				if (choice == 1) {
					// Get encryption key
					System.out.print("Enter encryption key:");
					input = in.readLine();
					BigInteger encryptionKey = new BigInteger(input);

					// Get value of n (modulus)
					System.out.print("Enter public value of n:");
					input = in.readLine();
					BigInteger modulus = new BigInteger(input);

					// Get the message1
					System.out.print("Enter in message: ");
					input = in.readLine();

					// Perform encryption!
					RSA rsa = new RSA();
					rsa.setPublicInfo(encryptionKey, modulus);
					System.out.println();

					// Encrypt and provide message
					System.out.println("Encrypted Message = "
							+ rsa.encrypt(input));

					// Exit loop
					exit = true;
				} else if (choice == 2) {
					System.out.println("Decrypt");
				} else if (choice == 3) {
					RSA rsa = new RSA();
					System.out.println("Private Info p=" + rsa.getP() + " q="
							+ rsa.getQ());
					System.out.println("Public Info: e="
							+ rsa.getEncryptionKey() + " n=" + rsa.getN());
				} else if (choice == 4) {
					exit = true;
				} else {
					System.out.println("Invalid input!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Invalid input!");
			}
		}
	}
}

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
					System.out.println("Generating initial data...");
					choice(new RSA(), in);
				} else if (choice == 2) {
					System.out.println("Generating initial data...");
					choice(new ElGamal(), in);
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

	public static void choice(CryptographyMethod method, BufferedReader in) {
		boolean exit = false;
		while (!exit) {
			try {
				System.out.print("\n" + method.getClass().getName()
						+ ": Would you like to (1) encrypt, (2) decrypt, "
						+ "(3) display private info, (4) show public info,"
						+ " or (5) to exit? ");
				String input = in.readLine();
				int choice = Integer.parseInt(input);
				if (choice == 1) {
					method.encrypt(in);
				} else if (choice == 2) {
					method.decrypt(in);
				} else if (choice == 3) {
					method.showPrivateInfo();
				} else if (choice == 4) {
					method.showPublicInfo();
				} else if (choice == 5) {
					exit = true;
				} else {
					System.out.println("Invalid input!");
				}
			} catch (Exception e) {
				System.out.println("Invalid input!  " + e.getMessage());
			}
		}
	}
}

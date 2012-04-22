package main;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import methods.cryptography.CryptographyMethod;
import methods.cryptography.RSA;
import methods.cryptography.ElGamal;

import functions.BlumBlumShub;

public class Test {

	public static void main(String[] args) {

		InputStreamReader stream = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(stream);
		String input = "";

		boolean exit = false;
		while (!exit) {
			System.out.print("Please choose an cryptography method "
					+ "by choosing (1) RSA or (2) El Gamal "
					+ "(any other input will exit): ");
			try {
				input = in.readLine();
				int choice = Integer.parseInt(input);
				BlumBlumShub.setBitSize(BlumBlumShub.DEFAULT_BIT_SIZE);
				if (choice == 1) {
					System.out.println("\nGenerating initial "
							+ "private/public data (bit size of "
							+ BlumBlumShub.getBitSize() + ")...");
					choice(new RSA(), in);
				} else if (choice == 2) {
					System.out.println("\nGenerating initial "
							+ "private/public data (bit size of "
							+ BlumBlumShub.getBitSize() + ")...");
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
						+ "(3) display private info, "
						+ "(4) show public info, " + "(5) attack message, "
						+ "(6) generate random private/public data, "
						+ "or (7) to exit? ");
				String input = in.readLine();
				int choice = Integer.parseInt(input);
				if (choice == 1) {
					method.encryptInput(in);
				} else if (choice == 2) {
					method.decryptInput(in);
				} else if (choice == 3) {
					method.showPrivateInfo();
				} else if (choice == 4) {
					method.showPublicInfo();
				} else if (choice == 5) {
					method.attackInput(in);
				} else if (choice == 6) {
					System.out.print("\nEnter a integer size in bits "
							+ "for length of values (anything above "
							+ "64 bits will incur performance problems):");
					input = in.readLine();
					try {
						int size = Integer.parseInt(input);
						BlumBlumShub.setBitSize(size);
					} catch (Exception e) {
						System.out.println("\nUsing current bit size of: "
								+ BlumBlumShub.getBitSize() + " bits");
					}
					System.out.println("\nGenerating random "
							+ "private/public data...");
					method.generateNewData();
					method.showPublicInfo();
				} else if (choice == 7) {
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

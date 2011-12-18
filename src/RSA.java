import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;

public class RSA extends CryptographyMethod {

	private BigInteger p, q, e, n;

	public RSA() {
		generateNewData();
	}

	public void setPrivateInfo(BigInteger p, BigInteger q) throws Exception {
		if (!MillerRabin.testStrongPrime(p))
			throw new Exception("p is not prime!");
		if (!MillerRabin.testStrongPrime(q))
			throw new Exception("q is not prime!");
		this.p = p;
		this.q = q;

		n = p.multiply(q);
	}

	public void setEncryptionKey(BigInteger e) throws Exception {
		if (n == null)
			throw new Exception("p & q OR n must be set before encryption key");
		if (!Euclidean.euclidean(e, n).equals(BigInteger.ONE))
			throw new Exception("Encryption key is not relatively prime to n");
		this.e = e;
	}

	public void setPublicInfo(BigInteger encryptionKey, BigInteger n)
			throws Exception {
		if (!Euclidean.euclidean(encryptionKey, n).equals(BigInteger.ONE))
			throw new Exception("Encryption key is not relatively prime to n");
		this.e = encryptionKey;
		this.n = n;
	}

	public BigInteger getN() {
		return n;
	}

	public BigInteger getEncryptionKey() {
		return e;
	}

	public BigInteger getP() {
		return p;
	}

	public BigInteger getQ() {
		return q;
	}

	private BigInteger phiOfN() {
		return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
	}

	public BigInteger encrypt(BigInteger message) throws Exception {
		if (e == null || n == null) {
			throw new Exception("Missing public information");
		}

		// Make sure message key is smaller than n
		if (message.compareTo(n) != -1) {
			throw new Exception("Message too large for public key!");
		}
		// Make sure encryption key is smaller than n
		if (e.compareTo(n) != -1) {
			throw new Exception("Encryption too large for public n!");
		}

		return FastExponentiation.fastExponentiation(message, e, n);
	}

	public BigInteger decrypt(BigInteger message) throws Exception {
		if (p == null || q == null) {
			throw new Exception("Missing private information");
		} else if (e == null) {
			throw new Exception("Missing public information");
		}

		// Make sure message is small enough
		if (message.compareTo(n) != -1) {
			throw new Exception("Message too large for public n!");
		}
		// Make sure e is small enough
		if (e.compareTo(n) != -1) {
			throw new Exception("Encryption too large for public n!");
		}

		BigInteger d = Euclidean.extendedEuclidean(phiOfN(), e)[1]
				.mod(phiOfN());
		BigInteger result = FastExponentiation
				.fastExponentiation(message, d, n);

		return result;
	}

	@Override
	public void encryptInput(BufferedReader in) throws Exception {
		try {
			System.out.println();
			showPublicInfo();
			System.out.print("To use currently data type anything but 'N':");
			String input = in.readLine();

			// Ask for new input data
			if (input.equals("N")) {
				// Get value of n (modulus)
				System.out.print("Enter public value of n:");
				input = in.readLine();
				BigInteger modulus = new BigInteger(input);

				// Get encryption key
				System.out.print("Enter encryption key:");
				input = in.readLine();
				BigInteger encryptionKey = new BigInteger(input);
				setPublicInfo(encryptionKey, modulus);
			}

			// Get the message
			System.out.print("Enter in message: ");
			input = in.readLine();

			BigInteger message;
			try {
				message = new BigInteger(input);
				// We found an integer lets see if user wants to use ASCII
				System.out.print("Integer entered would you like to "
						+ "convert to ASCII, type 'Y' for ASCII mode? ");
				input = in.readLine();

				// User wanted ASCII so lets convert
				if (input.equals("Y")) {
					message = Util.convertStringToBigInt(message.toString());
					System.out.println("Converting string to integer"
							+ " using ASCII - " + message);
				}
			} catch (Exception e) {
				System.out.println("Converting string to integer using ASCII!");
				message = Util.convertStringToBigInt(input);
			}

			// Encrypt and provide message
			System.out.println("\nEncrypted Message = " + encrypt(message));
		} catch (IOException e) {
			throw new Exception("Bad input");
		}
	}

	@Override
	public void decryptInput(BufferedReader in) throws Exception {
		try {
			System.out.println();
			showPublicInfo();
			System.out.print("To use currently data type anything but 'N':");
			String input = in.readLine();

			// Ask for new input data
			if (input.equals("N")) {
				// Get value of n (modulus)
				System.out.print("Enter private value of p:");
				input = in.readLine();
				BigInteger p = new BigInteger(input);

				System.out.print("Enter private value of q:");
				input = in.readLine();
				BigInteger q = new BigInteger(input);

				// Get encryption key
				System.out.print("Enter encryption key:");
				input = in.readLine();
				BigInteger e = new BigInteger(input);

				setPrivateInfo(p, q);
				setEncryptionKey(e);
			}

			// Get the message
			System.out.print("Enter in encrypted message: ");
			input = in.readLine();

			BigInteger result = decrypt(new BigInteger(input));

			System.out.println("\nDecrypted message: "
					+ "\n\tMessage as number=" + result
					+ "\n\tMessage as text using ASCII="
					+ Util.convertBigIntToString(result));
		} catch (IOException e) {
			throw new Exception("Bad input");
		}
	}

	@Override
	public void attackInput(BufferedReader in) throws Exception {
		// Get value of n (modulus)
		System.out.print("Enter public value of n:");
		String input = in.readLine();
		BigInteger modulus = new BigInteger(input);

		// Get encryption key
		System.out.print("Enter encryption key:");
		input = in.readLine();
		BigInteger encryptionKey = new BigInteger(input);
		setPublicInfo(encryptionKey, modulus);

		// Get the message
		System.out.print("Enter in encrypted message: ");
		input = in.readLine();

		try {
			BigInteger privP = PollardRhoMethod.pollardRhoMethod(modulus);
			BigInteger privQ = modulus.divide(privP);

			setPrivateInfo(privP, privQ);
			setEncryptionKey(encryptionKey);

			BigInteger result = decrypt(new BigInteger(input));

			showPrivateInfo();
			System.out.println("\nBest guess for decrypted message: "
					+ "\n\tMessage as number=" + result
					+ "\n\tMessage as text using ASCII="
					+ Util.convertBigIntToString(result));

		} catch (Exception e) {
			System.out.println("\n" + e.getMessage());
		}

	}

	@Override
	public void showPrivateInfo() {
		System.out.println();
		System.out.println("Private info:" + "\n\tp = " + p + "\n\tq = " + q);
	}

	@Override
	public void showPublicInfo() {
		System.out.println();
		System.out.println("Public info:" + "\n\tn = " + n
				+ "\n\te (encryption key) = " + e);
	}

	@Override
	public void generateNewData() {
		p = BlumBlumShub.randomStrongPrime();
		q = BlumBlumShub.randomStrongPrime();

		n = p.multiply(q);

		// e needs to be relatively prime to phi(n)
		do {
			// Find an encryption key between 1 and n inclusive
			e = Util.randomBigInteger(Util.TWO, n.subtract(BigInteger.ONE));
		} while (!Euclidean.euclidean(e, phiOfN()).equals(BigInteger.ONE));
	}
}

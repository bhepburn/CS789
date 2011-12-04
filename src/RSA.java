import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;

public class RSA extends CryptographyMethod {

	private BigInteger p, q, e, n;

	public RSA() {
		p = BlumBlumShub.randomNumber();
		q = BlumBlumShub.randomNumber();

		n = p.multiply(q);

		// e needs to be relatively prime to phi(n)
		do {
			// Find an encryption key between 1 and n (no inclusive)
			e = Util.randomBigInteger(Util.TWO, n.subtract(BigInteger.ONE));
		} while (!Euclidean.euclidean(e, phiOfN()).equals(BigInteger.ONE));
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

	public BigInteger encrypt(String message) throws Exception {
		if (e == null || n == null) {
			throw new Exception("Missing public information");
		}

		// BigInteger encodedMessage = Util.convertStringToBigInt(message);
		BigInteger encodedMessage = new BigInteger(message);
		if (encodedMessage.compareTo(n) != -1) {
			throw new Exception("Message too large for public key!");
		}
		return FastExponentiation.fastExponentiation(encodedMessage, e, n);
	}

	public String decrypt(String message) throws Exception {
		if (p == null || q == null) {
			throw new Exception("Missing private information");
		} else if (e == null) {
			throw new Exception("Missing public information");
		}

		BigInteger val = Euclidean.extendedEuclidean(phiOfN(), e)[1]
				.mod(phiOfN());
		BigInteger result = FastExponentiation.fastExponentiation(
				new BigInteger(message), val, n);
		// return Util.convertBigIntToString(result);
		return result.toString();
	}

	@Override
	public void encrypt(BufferedReader in) throws Exception {
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

			// Encrypt and provide message
			System.out.println("\nEncrypted Message = " + encrypt(input));
		} catch (IOException e) {
			throw new Exception("Bad input");
		}
	}

	@Override
	public void decrypt(BufferedReader in) throws Exception {
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

			System.out.println("\nDecrypted message = " + decrypt(input));
		} catch (IOException e) {
			throw new Exception("Bad input");
		}
	}

	@Override
	public void showPrivateInfo() {
		System.out.println();
		System.out.println("Private info:" + "\n\tp=" + p + "\n\tq=" + q);
		System.out.println();
	}

	@Override
	public void showPublicInfo() {
		System.out.println();
		System.out.println("Public info:" + "\n\te (encryption key)=" + e
				+ "\n\tn=" + n);
		System.out.println();
	}
}

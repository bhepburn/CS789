import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;

public class ElGamal extends CryptographyMethod {

	private BigInteger cyclicGroup, primitiveRoot, privateKey, publicKey,
			sharedKey;

	public ElGamal() {
		generateNewData();
	}

	public BigInteger getCyclicGroup() {
		return cyclicGroup;
	}

	public BigInteger getPublicKey() {
		return publicKey;
	}

	public BigInteger getPrimitiveRoot() {
		return primitiveRoot;
	}

	public void setPublicInfo(BigInteger cyclicGroup, BigInteger primitiveRoot) {
		this.cyclicGroup = cyclicGroup;
		this.primitiveRoot = primitiveRoot;
		this.publicKey = FastExponentiation.fastExponentiation(primitiveRoot,
				privateKey, cyclicGroup);
	}

	public void setPrivateInfo(BigInteger privateKey) {
		this.privateKey = privateKey;
		this.publicKey = FastExponentiation.fastExponentiation(primitiveRoot,
				privateKey, cyclicGroup);
	}

	public void setSharedKey(BigInteger sharedKey) {
		this.sharedKey = sharedKey;
	}

	public String encrypt(String msg) throws Exception {
		// BigInteger val = Util.convertStringToBigInt(msg);
		BigInteger val = new BigInteger(msg);
		if (val.compareTo(cyclicGroup) != -1) {
			throw new Exception("Message too large for public key!");
		}

		BigInteger encryptionKey = FastExponentiation.fastExponentiation(
				sharedKey, privateKey, cyclicGroup);
		return val.multiply(encryptionKey).mod(cyclicGroup).toString();
	}

	public String decrypt(String msg) {
		BigInteger value = new BigInteger(msg);
		BigInteger decryptionKey = FastExponentiation.fastExponentiation(
				sharedKey,
				cyclicGroup.subtract(BigInteger.ONE).subtract(privateKey),
				cyclicGroup);
		// return Util.convertBigIntToString(value.multiply(decryptionKey).mod(
		// cyclicGroup));
		return value.multiply(decryptionKey).mod(cyclicGroup).toString();
	}

	@Override
	public void encryptInput(BufferedReader in) throws Exception {
		try {
			System.out.println();
			showPublicInfo();
			System.out.print("To use the cyclic group, primitive group, "
					+ "and public key above type anything but 'N':");
			String input = in.readLine();

			// Ask for new input data
			if (input.equals("N")) {
				System.out.print("Enter p (cyclic group):");
				input = in.readLine();
				BigInteger cyclicGroup = new BigInteger(input);

				System.out.print("Enter primitive root:");
				input = in.readLine();
				BigInteger primitiveRoot = new BigInteger(input);

				setPublicInfo(cyclicGroup, primitiveRoot);
			}

			System.out.print("Enter shared public key:");
			input = in.readLine();
			BigInteger sharedKey = new BigInteger(input);
			setSharedKey(sharedKey);

			// Get the message
			System.out.print("Enter in message: ");
			input = in.readLine();

			// Encrypt and provide message and public key
			System.out.println("\nNew public key = " + publicKey);
			System.out.println("Encrypted pessage = " + encrypt(input));
		} catch (IOException e) {
			throw new Exception("Bad input");
		}
	}

	@Override
	public void decryptInput(BufferedReader in) throws Exception {
		try {
			System.out.println();
			showPublicInfo();
			System.out.print("To use the cyclic group, primitive root, "
					+ "and public key above type anything but 'N':");
			String input = in.readLine();

			// Ask for new input data
			if (input.equals("N")) {
				// Get value of n (modulus)
				System.out.print("Enter p (cyclic group):");
				input = in.readLine();
				BigInteger cyclicGroup = new BigInteger(input);

				System.out.print("Enter b (primitive root):");
				input = in.readLine();
				BigInteger primitiveRoot = new BigInteger(input);

				setPublicInfo(cyclicGroup, primitiveRoot);
			}

			System.out.print("Enter shared public key:");
			input = in.readLine();
			BigInteger sharedKey = new BigInteger(input);
			setSharedKey(sharedKey);

			// Get the message
			System.out.print("Enter in encrypted message: ");
			input = in.readLine();

			System.out.println("\nDecrypted message = " + decrypt(input));
		} catch (IOException e) {
			throw new Exception("Bad input");
		}
	}

	@Override
	public void attackInput(BufferedReader in) throws Exception {
		try {
			// Get value of n (modulus)
			System.out.print("Enter p (cyclic group):");
			String input = in.readLine();
			BigInteger cyclicGroup = new BigInteger(input);

			System.out.print("Enter b (primitive root):");
			input = in.readLine();
			BigInteger primitiveRoot = new BigInteger(input);

			System.out.print("Enter shared public key:");
			input = in.readLine();
			BigInteger sharedKey = new BigInteger(input);

			// Get the message
			System.out.print("Enter in encrypted message: ");
			input = in.readLine();

			// Call baby-step giant-step algorithm
			BigInteger privateKey = BabyStepGiantStep.babyStepGiantStep(
					cyclicGroup, primitiveRoot, sharedKey);

			if (privateKey != null) {
				this.cyclicGroup = cyclicGroup;
				this.primitiveRoot = primitiveRoot;
				this.publicKey = sharedKey;
				this.privateKey = privateKey;

				System.out.println("\nBest guess for private key = "
						+ privateKey);
				System.out
						.println("Best guess for message = " + decrypt(input));
			} else {
				System.out.println("Could not find private key");
			}
		} catch (IOException e) {
			throw new Exception("Bad input");
		}
	}

	@Override
	public void showPrivateInfo() {
		System.out.println();
		System.out.println("Private info:" + "\n\tl (private key) = "
				+ privateKey);
		System.out.println();
	}

	@Override
	public void showPublicInfo() {
		System.out.println();
		System.out.println("Public info:" + "\n\tp (cyclic group) = "
				+ cyclicGroup + "\n\tb (primitive root) = " + primitiveRoot
				+ "\n\tmy public key = " + publicKey);
		System.out.println();
	}

	@Override
	public void generateNewData() {
		boolean failed = true;
		while (failed) {
			cyclicGroup = BlumBlumShub.randomStrongPrime();
			do {
				privateKey = BlumBlumShub.randomStrongPrime();
			} while (privateKey.compareTo(cyclicGroup) >= 0);

			// Find a primitive root in the group
			try {
				primitiveRoot = PrimitiveRootSearch
						.primitiveRootSearch(cyclicGroup);

				// Public key is primitive root to the power of private key
				publicKey = FastExponentiation.fastExponentiation(
						primitiveRoot, privateKey, cyclicGroup);
				failed = false;
			} catch (Exception e) {
				// Do nothing lets just generate another cyclic group
			}
		}
	}
}

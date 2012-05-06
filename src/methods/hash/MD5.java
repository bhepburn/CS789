package methods.hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 extends HashMethod {

	@SuppressWarnings("unused")
	private static final int[] r = { 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17,
			22, 7, 12, 17, 22, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9,
			14, 20, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
			6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21 };

	private long[] k = new long[64];

	public MD5() {
		super();
		for (int i = 0; i < 64; i++) {
			k[i] = (long) Math.floor(Math.abs(Math.sin(i + 1))
					* Math.pow(2, 32));
		}
	}

	@Override
	public byte[] calculateDigest() {
		try {
			byte[] bytes = message.getBytes("US-ASCII");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytes);
			return thedigest;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] calculate32BitDigest() {
		byte[] digest = calculateDigest();
		byte[] functionalDigest = new byte[4];
		for (int i = 0; i < 4; i++) {
			functionalDigest[i] = digest[i];
		}
		return functionalDigest;
	}
}

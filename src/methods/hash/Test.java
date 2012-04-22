package methods.hash;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] bytes = new byte[65];
		int size = (int) Math.ceil((bytes.length * 8 + 1) / 512.0);
		System.out.println(size);
	}

}

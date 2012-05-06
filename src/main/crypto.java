package main;


import java.io.*;
import java.math.*;
import java.security.SecureRandom;
import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.math.BigInteger;

// Tyrone Baltimore

public class crypto {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BigInteger i; 
		BigInteger j;
		int TRUE = 1;
			
		// 	====  Works for RSA ====
		// Comment out appropriate input code for operation.
		System.out.println("Pollard-Minus choosen:");
		System.out.println("Enter the pollard value:");
		BigInteger pollard_value;
		BigInteger result_pollard_value;
		pollard_value = BigInteger.valueOf(Long.parseLong(br.readLine()));
		result_pollard_value = programpollard_minus_one_factor(pollard_value); // 3801911
		System.out.println("");
		System.out.println("result_pollard_value : " + result_pollard_value);
			
		// ====  Works for Diffe-Hellman ====
		System.out.println("Baby Step Giant step choosen:");
		//programlog_baby_step_giant_step(BigInteger base, BigInteger residue, BigInteger modulus)
		System.out.println("Enter the Base (Generator) value:");
		BigInteger base_value;
		base_value = BigInteger.valueOf(Long.parseLong(br.readLine()));
		System.out.println("Enter the Residue (Public Key) value:");
		BigInteger residue_value;
		residue_value = BigInteger.valueOf(Long.parseLong(br.readLine()));
		System.out.println("Enter the modulus (Zp) value:");
		BigInteger modulus_value;
		modulus_value = BigInteger.valueOf(Long.parseLong(br.readLine()));
		// BigInteger programlog_baby_step_giant_step(BigInteger base, BigInteger 3, BigInteger modulus)
		// Encrypted data, Zp and Public key is the residue_value
		BigInteger result_bsgs = programlog_baby_step_giant_step(base_value, residue_value, modulus_value);
		System.out.println("Secret Key is  : " + result_bsgs );
			
		double result=0;
		System.out.println("Rabin Miller choosen:");
		System.out.println("Enter an integer 'i': ");
		i = BigInteger.valueOf(Long.parseLong(br.readLine()));
		System.out.println("Enter an integer 'j': ");
		j = BigInteger.valueOf(Long.parseLong(br.readLine()));
			
		//programprime_probability(BigInteger n, int numPasses)
		result = programprime_probability (i, j.intValue());
		if (result == 0)
			System.out.println("Number is a composite");
		else
			System.out.println("Number is Prime");
	
		
		System.out.println(" Part -1");
		System.out.println("Break Diffe-Hellman choosen:");
		System.out.println("Enter an Zp 'Zp': ");
		BigInteger Zp;
		Zp = BigInteger.valueOf(Long.parseLong(br.readLine()));
			
		BigInteger Primitive_root;
		System.out.println("Enter the Generator: ");
		Primitive_root = BigInteger.valueOf(Long.parseLong(br.readLine()));
			
		BigInteger Public_Key;
		System.out.println("Enter an Public Key of the sender: ");
		Public_Key = BigInteger.valueOf(Long.parseLong(br.readLine()));
			
		BigInteger encrypted_data;
		System.out.println("Enter the encrypted_data: ");
		encrypted_data = BigInteger.valueOf(Long.parseLong(br.readLine()));
			
		BigInteger private_key;
		System.out.println("Enter the private key:");
		private_key = BigInteger.valueOf(Long.parseLong(br.readLine()));
			
		BigInteger decrypted_data;
		System.out.println("Decrypted_data: ");
			
		BigInteger intermediate_data;
		intermediate_data = Public_Key.pow(private_key.intValue()).mod(Zp);
			
		System.out.println("inter_data is : " + intermediate_data);

		BigInteger decrypted_data_inverse;
		decrypted_data_inverse = intermediate_data.modInverse(Zp);
		decrypted_data = decrypted_data_inverse.multiply(encrypted_data);
		System.out.println("is " + decrypted_data.mod(Zp));
		
		System.out.println("  Part - 2");
		System.out.println(" Enter Data to send to the reciver: ");
			
		System.out.println("Enter my Zp 'Zp': ");
		BigInteger Zp_my;
		Zp_my = BigInteger.valueOf(Long.parseLong(br.readLine()));
			
		BigInteger Public_receivers_my;
		System.out.println("Enter my Public Key: ");
		Public_receivers_my = BigInteger.valueOf(Long.parseLong(br.readLine()));
			
		BigInteger private_key_my;
		System.out.println("Enter the private key:");
		private_key_my = BigInteger.valueOf(Long.parseLong(br.readLine()));
			
		BigInteger intermediate_data_my;
		intermediate_data_my = Public_receivers_my.pow(private_key_my.intValue()).mod(Zp_my);
			
		BigInteger Primitive_root_my;
		System.out.println("Enter my Generator: ");
		Primitive_root_my = BigInteger.valueOf(Long.parseLong(br.readLine()));
			
		BigInteger data_my;
		System.out.println("Enter my data: ");
		data_my = BigInteger.valueOf(Long.parseLong(br.readLine()));
		System.out.println("encrypted data is :" +  data_my.multiply(intermediate_data_my));			
		
		
		BigInteger p, q;
		// p - 241 , q - 311
		System.out.println("Enter the p: ");
		p = BigInteger.valueOf(Long.parseLong(br.readLine()));
		System.out.println("Enter the q: ");
		q = BigInteger.valueOf(Long.parseLong(br.readLine()));

		int n = (p.intValue() * ( q.intValue()));
		BigInteger bigInt_n = new BigInteger(String.valueOf(n));
			
		int phi_n = (p.intValue()-1) * (q.intValue()-1);
		BigInteger bigInt_n_phi = new BigInteger(String.valueOf(phi_n));
			
		BigInteger encrypted_key ;
		System.out.println("Enter the encrypted key: ");
		encrypted_key = BigInteger.valueOf(Long.parseLong(br.readLine()));
	}
	static BigInteger ZERO 			= 	new BigInteger("0");
	static BigInteger ONE			=	new BigInteger("1");
	static BigInteger TWO			=	new BigInteger("2");
	static BigInteger THREE			=	new BigInteger("3");
	static BigInteger FOUR			=	new BigInteger("4");

	public static double programprime_probability(BigInteger n, int numPasses) {
			//if (numPasses<=0) throw new IllegalArgumentException (“Number of bases must be positive!”);
			BigInteger b,x;
			System.out.println("programprime_probability: Enter ");
			System.out.println("programprime_probability: x:" + n + "   numPass " + numPasses);
			SecureRandom sr=new SecureRandom();
			BigInteger nMinusOne=n.subtract(ONE);
			for (int i=0;i<numPasses;i++) {
				//Choose a random base smaller than n
				b=new BigInteger(n.bitLength()-1,sr);
		
				// programcheck Fermat’s condition first
				x=b.modPow(nMinusOne,n);
				if (!x.equals(ONE)) return 0.0; //not prime
				//Divide n-1 by 2
				BigInteger[] dr=nMinusOne.divideAndRemainder(TWO);
		
				while (dr[1].equals(ZERO)) {
					x=b.modPow(dr[0],n);
					if (x.equals(nMinusOne)) break; //pass
					if (!x.equals(ONE)) return 0.0; //not prime
						dr=dr[0].divideAndRemainder(TWO);
				} // while
			} // for
			
			System.out.println("programprime_probability: Exit ");
			return 1.0-Math.pow(0.25,numPasses);
	}
	
	public class programbig_interger_math {
		// A nonrecursive version of Euclid. It returns an array answer of 3 BigIntegers
		public BigInteger[] euclid (BigInteger a, BigInteger b) {
		//Throw an exception if either argument is not positive
		if (a.compareTo(ZERO)<=0||b.compareTo(ZERO)<=0); 
			BigInteger[] answer	=	new BigInteger[3];
			//Set up all the initial table entries
			BigInteger r0=new BigInteger(a.toByteArray());
			BigInteger r1=new BigInteger(b.toByteArray());
			BigInteger s0=new BigInteger("1");
			BigInteger s1=new BigInteger("0");
			BigInteger t0=new BigInteger("0");
			BigInteger t1=new BigInteger("1");
			BigInteger q1=r0.divide(r1);
			BigInteger r2=r0.mod(r1);
			BigInteger s2,t2;
			//When r2 becomes zero, the previous table entries are the answers
			while (r2.compareTo(ZERO)>0) {
				s2=s0.subtract(q1.multiply(s1)); s0=s1; s1=s2;
				t2=t0.subtract(q1.multiply(t1)); t0=t1; t1=t2;
				r0=r1; r1=r2; q1=r0.divide(r1); r2=r0.mod(r1);
			}
			answer[0]=r1; answer[1]=s1; answer[2]=t1;
			return answer;
		}
	}

	static long expomod(long n, long p, long m) {
		   if (p == 0) 
			   return 1;
		   long nm = n % m;
		   //System.out.println("n % m : " + nm);
		   long  r = expomod(nm, p / 2, m);
		   System.out.println("r : " + r);
		   r = (r * r) % m;
		   //System.out.println(" : " + p);
		   if (p % 2 == 0) return r;
		   return (r * nm) % m;
	}
	
	private static BigInteger gcd(BigInteger first, BigInteger second) {
		BigInteger ZERO 			= 	new BigInteger("0");
		//Make sure both are nonnegative
		first=first.abs();
		second=second.abs();
		//Call the recursive method
		return programrecurse_gcd(first,second);
	}
	
	private static BigInteger programrecurse_gcd(BigInteger x, BigInteger y) {
		BigInteger ZERO 			= 	new BigInteger("0");
		//System.out.println("programrecurse_gcd ");
		if (y.equals(ZERO)) return x;
			else return programrecurse_gcd(y,x.mod(y));
	}
	// Add the class programprime_generator
	public class programprime_generator {
		int minBitLength;
		//certainty is the number of primality tests to pass
		int certainty;
		SecureRandom sr;
		public programprime_generator(int minBitLength, int certainty, SecureRandom sr) {
			if (minBitLength<512)
				//Set the values
				this.minBitLength=minBitLength;
				this.certainty=certainty;
				this.sr=sr;
			}
			public BigInteger programget_stron_prime() {
			
				BigInteger s=new BigInteger(minBitLength/2-8,certainty,sr);
				BigInteger t=new BigInteger(minBitLength/2-8,certainty,sr);
				BigInteger i=BigInteger.valueOf(1);
				BigInteger r;
				do {
					r = TWO.multiply(i).multiply(t).add(ONE);
					i=i.add(ONE);
				} while (!r.isProbablePrime(certainty));
				
				BigInteger z = s.modPow(r.subtract(TWO),r);
				BigInteger pstar = TWO.multiply(z).multiply(s).subtract(ONE);
				BigInteger k=BigInteger.valueOf(1);

				BigInteger p = TWO.multiply(r).multiply(s).add(pstar);
				while (p.bitLength()<=minBitLength) {
					k=k.multiply(TWO);
					p = TWO.multiply(k).multiply(r).multiply(s).add(pstar);
				}
				while (!p.isProbablePrime(certainty)) {
					k=k.add(ONE);
					p=TWO.multiply(k).multiply(r).multiply(s).add(pstar);
				}
				return p;
		}	
	}

	public class programsecure_prime_randomn_generator {
				BigInteger p,q,n,seed;
				
				public programsecure_prime_randomn_generator (byte[] seed) {
					this.seed=new BigInteger(seed);
					SecureRandom sr = new SecureRandom(seed);
					
					programprime_generator pg=new programprime_generator(513,16,sr);
					do {
						p = pg.programget_stron_prime();
					}

					while (!p.mod(FOUR).equals(THREE));
					do {
						q = pg.programget_stron_prime();
					}
					while (!q.mod(FOUR).equals(THREE));
					n=p.multiply(q);
				}
		
				public void programfill_bytes(byte[] array) {
					for (int i=0;i<array.length;i++) {
						//Seed is continually squared
						seed=seed.multiply(seed).mod(n);
						//Least significant byte of residue is the i-th random byte
						byte b=seed.byteValue();
				array[i]=b;
			}
		}
		//Returns a single byte of pseudo-random data
		public byte programget_randomn_byte() {
			seed=seed.multiply(seed).mod(n);
			return seed.byteValue();
		}
	}
	
	/////////////////////// Pollard-minus one  /////////////////////////
	//Computes the least nonnegative residue of b mod m, where m>0.
	public static BigInteger programleast_nonnegative_residue(BigInteger b, BigInteger m) {
	BigInteger answer=b.mod(m);
	return (answer.compareTo(ZERO)<0)?answer.add(m):answer;
	}
	// pollard 
	public static BigInteger programpollard_minus_one_factor(BigInteger n) throws IllegalArgumentException {
		Random rand=new Random();
		BigInteger power=BigInteger.valueOf(1);
		BigInteger residue=programleast_nonnegative_residue(BigInteger.valueOf(rand.nextInt()), n);
		BigInteger test=residue.subtract(ONE);
		BigInteger gcd=test.gcd(n);
		while (true) {
			while (gcd.equals(ONE)) {
				power=power.add(ONE);
				residue=residue.modPow(power,n);
				test=residue.subtract(ONE);
				gcd=test.gcd(n);
			}
			if (gcd.equals(n)) {
				power=BigInteger.valueOf(1);
				residue=programleast_nonnegative_residue(BigInteger.valueOf(rand.nextInt()),n);
				test=residue.subtract(ONE);
				gcd=test.gcd(n);
			} else return gcd;
		}
	}
	// Baby step Giant Step.
	public class programgiant_step {	
		public void BSGS(String args[])  throws NumberFormatException, IOException { 
			BigInteger _biP, _biA, _biG, _biF, _biP2, _biP3, _biK, _biF1, y, _biF2, _biF3;
			long x1 = -1, x2 = 2, i, j, x4, m1, m2;
			int _iC;
			
			// Note works for numbers less than this size.
			BigInteger _biaTempArr[] = new BigInteger[99999];
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
			System.out.println("Enter generator 'g': ");								
			_biG = BigInteger.valueOf(Long.parseLong(br.readLine()));

			System.out.println("Enter prime 'p': ");
			_biP = BigInteger.valueOf(Long.parseLong(br.readLine()));

			System.out.println("Enter 'a': ");
			_biA = BigInteger.valueOf(Long.parseLong(br.readLine()));

			long _BGStart = System.currentTimeMillis();
			_biF1 = _biG.modInverse(_biP);
			
			System.out.println("Inverse =" + _biF1);

			// Square root for m
			BigInteger big = _biP;
			double x = big.doubleValue();
			long m = (long) Math.sqrt(x);
			m = (long) Math.ceil(m);
			if (m % 2 == 1) {
				m = m + 1;
			}
			//System.out.println("Square root = " + m);
			_biF = _biF1.modPow(BigInteger.valueOf(m), _biP);
			m1 = (m / 2);
			_biF2 = _biF1.modPow(BigInteger.valueOf(m1), _biP);
			m2 = m - m1;
			_biF3 = _biF1.modPow(BigInteger.valueOf(m2), _biP);
			_biF = (_biF2.multiply(_biF3).mod(_biP));
			_biP2 = _biP.add(BigInteger.valueOf(x1));
			_biP3 = _biP2.divide(BigInteger.valueOf(x2));
			_biK = (_biA.modPow(_biP3, _biP));
		
			if (_biK.equals(BigInteger.ONE)) {
				x1 = 0;
			} else {
				x1 = 1;
			}
		
			for (i = x1; i < m; i++) {
				_biaTempArr[(int) i] = _biG.modPow(BigInteger.valueOf(i), _biP);
				i++;
			}
			y = _biA;
			_iC = 1;
		
			for (i = 1; i < m * m; i++) {
				y = y.mod(_biP);
				y = y.multiply(_biF).mod(_biP);
				for (j = x1; j <= m; j++) {
					if (y.equals(_biaTempArr[(int) j])) {
						x4 = (m * i) + j;
							i = (int) (m * m) + 2;
							j = (int) m + 2;
							_iC = 0;
					}
					j++;
				}
			}
			if (_iC == 1) {
				System.out.println("Prime is not strong enough.....");
			}
		}
	}

	//
	public static BigInteger sqrt(BigInteger m) {
		int diff=m.compareTo(ZERO);
		if (diff==0) return BigInteger.valueOf(0);
		BigDecimal two=new BigDecimal(TWO);
		//Convert the parameter to a BigDecimal
		BigDecimal n=new BigDecimal(m);
	
		byte[] barray=new byte[m.bitLength()/16+1];
		barray[0]=(byte)255;
		
		BigDecimal r=new BigDecimal(new BigInteger(1,barray));

		r=r.subtract(r.multiply(r).subtract(n).divide
				(r.multiply(two),BigDecimal.ROUND_UP));
		
		while (r.multiply(r).compareTo(n)>0) {
			r=r.subtract(r.multiply(r).subtract(n).divide
				(r.multiply(two),BigDecimal.ROUND_UP));
		}
		return r.toBigInteger();
	}
		
	// Use this to break the Diffe-Hellman
	public static BigInteger programlog_baby_step_giant_step(BigInteger base, BigInteger residue, BigInteger modulus) {
		BigInteger m=sqrt(modulus).add(ONE);
		
		Hashtable h=new Hashtable();
		BigInteger basePow=BigInteger.valueOf(1);
		
		for (BigInteger j=BigInteger.valueOf(0);j.compareTo(m)<0;j=j.add(ONE)) {
			h.put(basePow,j);
			basePow=basePow.multiply(base).mod(modulus);
		}
		
		//Compute an inverse of base^m modulo p
		BigInteger basetotheminv=base.modPow(m,modulus).modInverse(modulus);
		BigInteger y=new BigInteger(residue.toByteArray());
		
		//Search the hash table for a base^j such that y=base^j for some j
		BigInteger target;
		for (BigInteger i=BigInteger.valueOf(0);i.compareTo(m)<0;i=i.add(ONE)) {
			target = (BigInteger)h.get(y);
			if (target!=null) return i.multiply(m).add(target);
			y=y.multiply(basetotheminv).mod(modulus);
		}
		return m;
	}
	public class programextended_gcd {
	public long[] GCD(long x, long y) { // assume not 0 or neg
		long[] u = new long[3];
		long[] v = new long[3];
		long[] t = new long[3];

		u[0] = 1; u[1] = 0; u[2] = x; v[0] = 0; v[1] = 1; v[2] = y;
		System.out.println("q\tu[0]\tu[1]\tu[2]\tv[0]\tv[1]\tv[2]");
		while (v[2] != 0) {
			long q = u[2]/v[2];
			// t = u - v*q;
			t[0] = u[0] -v[0]*q; t[1] = u[1] -v[1]*q; t[2] = u[2] -v[2]*q;
			programcheck(x, y, t);
			// u = v;
			u[0] = v[0]; u[1] = v[1]; u[2] = v[2]; programcheck(x, y, u);
			// v = t;
			v[0] = t[0]; v[1] = t[1]; v[2] = t[2]; programcheck(x, y, v);
			System.out.println(q + "\t"+ u[0] + "\t" + u[1] + "\t" + u[2] + "\t"+ v[0] + "\t" + v[1] + "\t" + v[2]);
		}
		return u;
	} }
	public  void programcheck(long x, long y, long[] w) {
		if (x*w[0] + y*w[1] != w[2]) {
			System.out.println("*** programcheck fails: " + x + " " + y);
			System.exit(1);
		}
	}
}
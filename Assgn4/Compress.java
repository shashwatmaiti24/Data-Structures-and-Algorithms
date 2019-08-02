import javafx.util.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Compress {
	static int N=65536;
	static int R=65537;
	static int f = 128;
	static Pair<String, Integer>[] dic = new Pair[N];
	public static void main(String args []) throws IOException {
		String str = new String(Files.readAllBytes(Paths.get(args[0])));
		OutputStream os = new FileOutputStream(args[1]);
		for(int i=0; i<=127; i++) {
			String s = Character.toString((char)i);
			dic[i] = new Pair<String, Integer>(s, i);
		}
			int i = 0;
			while(i<str.length()) {
				int n = 0;
				for(int j = i+1; j<=str.length(); j++) {
					String s = str.substring(i,j);
					Pair<Boolean, Integer> p = compressadd(hashfunc(s), s);
					if(!p.getKey()) {
						os.write(comp(dic[n].getValue()));
						i = j-1;
						break;
					}
					n =  p.getValue();
					if(j==str.length()) {
						Pair<Boolean, Integer> r = compressadd(hashfunc(s), s);
						os.write(comp(dic[r.getValue()].getValue()));
						i = j;
					}
				}
			}
	os.close();
	}
	static byte[] comp(int i) {
		byte code[] = new byte[2];
		int n = i/256;
		code[0] = (byte) n;
		if(n<128) {
			code[0] = (byte) n;
		}
		else {
			code[0] = (byte) (n-256);
		}
		i = i%256;
		code[1] = (byte) i;
		if(i<128) {
			code[1] = (byte) i;
		}
		else {
			code[1] = (byte) (i-256);
		}
		return code;
	}
	static double hashfunc(String s) {
		double n=0;
		for(int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			n=n*33+(int)c;
		}
		return n;
	}
	static Pair<Boolean, Integer> compressadd(double n, String s) {
		n = n%R;
		n = n%N;
		double r = n;
		if(dic[(int)n]==null) {
			dic[(int)n] = new Pair<String, Integer>(s, f);
			f++;
			return new Pair<Boolean, Integer>(false, (int)n);
		}
		else if(dic[(int)n].getKey().equals(s)) {
			return new Pair<Boolean, Integer>(true, (int)n);
		}
		double i = 1;
		n = (r+i*i)%R;
		n = n%N;
		while(n!=r) {
			if(dic[(int)n]==null) {
				dic[(int)n] = new Pair<String, Integer>(s, f);
				f++;
				return new Pair<Boolean, Integer>(false, (int)n);
			}
			else if(dic[(int)n].getKey().equals(s)) {
				return new Pair<Boolean, Integer>(true, (int)n);
			}
			i++;
			n = (r+i*i)%R;
			n = n%N;
		}
		for(int j = 1; j<65536; j++) {
			if(dic[((int)n+j)%((int)N)]==null) {
				dic[((int)n+j)%((int)N)] = new Pair<String, Integer>(s, f);
				f++;
				return new Pair<Boolean, Integer>(false, (int)n);
			}
			else if(dic[((int)n+j)%((int)N)].getKey().equals(s)) {
				return new Pair<Boolean, Integer>(true, (int)n);
			}
		}
		return new Pair<Boolean, Integer>(false, null);
	}
}

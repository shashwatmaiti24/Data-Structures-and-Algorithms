import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Decompress {
	static int N=65536;
	static int f = 128;
	static String[] dic = new String[N];
	public static void main(String args []) throws IOException {
		for(int i=0; i<=127; i++) {
			dic[i] = Character.toString((char) i);
		}
        PrintStream o = new PrintStream(new File(args[1])); 
        System.setOut(o);
			byte[] fileContent = Files.readAllBytes(Paths.get(args[0]));
			int i=0;
			while(i<fileContent.length/2-1) {
				int curr0 = fileContent[2*i];
				if(curr0 < 0) {
					curr0 = 256+curr0;
				}
				int curr1 = fileContent[2*i+1];
				if(curr1 < 0) {
					curr1 = 256+curr1;
				}
				int current = curr0*256+curr1;
				int nex0 = fileContent[2*(i+1)];
				if(nex0 < 0) {
					nex0 = 256+nex0;
				}
				int nex1 = fileContent[2*(i+1)+1];
				if(nex1 < 0) {
					nex1 = 256+nex1;
				}
				int next = nex0*256+nex1;
				if(f!=N) {
					dicadd(current, next);
				}
				System.out.print(dic[current]);
				i++;
			}
			int cur0 = fileContent[fileContent.length-2];
			if(cur0 < 0) {
				cur0 = 256+cur0;
			}
			int cur1 = fileContent[fileContent.length-1];
			if(cur1 < 0) {
				cur1 = 256+cur1;
			}
			int curent = cur0*256+cur1;
			System.out.print(dic[curent]);
	}
	static void dicadd(int i, int j) {
		if(f==j) {
			dic[f] = dic[i]+dic[i].substring(0,1);
		}
		else {
			dic[f] = dic[i]+dic[j].substring(0,1);
		}
		f++;
	}
}

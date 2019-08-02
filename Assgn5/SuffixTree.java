import java.util.*;
import java.io.*;
public class SuffixTree {
	static Node root;
	static String strng;
	static PrintStream previousConsole;
	public static void main(String args []) throws FileNotFoundException {
	previousConsole = new PrintStream(new FileOutputStream(args[1])); 
    System.setOut(new PrintStream(previousConsole));
	FileInputStream fstream = new FileInputStream (args[0]);
	Scanner s = new Scanner ( fstream );
	strng = s.nextLine(); 
	root = new Node(0, 0, strng.length());
		for(int i=strng.length()-1; i>-1; i--) {
			add(i, root);
		}
		int q = s.nextInt();
		s.nextLine();
		for(int i=0; i<q; i++) {
			query(s.nextLine());
		}
	}
	static void query(String srstr) {
		String[] srlist = srstr.split("\\*");
		if(srlist.length==2) {
			if(srlist[0].equals("")) {
		        ByteArrayOutputStream newConsole1 = new ByteArrayOutputStream();
		        System.setOut(new PrintStream(newConsole1));
				searchall(root, srlist[1], srlist[1].length());
	            System.setOut(previousConsole);
		        String str1 = newConsole1.toString();
		        String[] str1list = str1.split("\n");
		        int[] barr = new int[str1list.length];
		        int[] earr = new int[str1list.length];
		        for(int i=0; i<str1list.length;  i++) {
		        	String[] str1break = str1list[i].split(" ");
		        	if(str1break[0].equals("")) {
		        		return;
		        	}
		        	barr[i] = Integer.parseInt(str1break[0]);
		        	earr[i] = Integer.parseInt(str1break[1].substring(0, str1break[1].length()-1));
		        }
		        Arrays.sort(barr);
		        Arrays.sort(earr);
		        for(int i=0; i<strng.length();  i++) {
			        for(int j = 0; j<barr.length; j++) {
			        	if(barr[j]>=i) {
			        		System.out.println(i+" "+earr[j]);
			        	}
			        }
		        }
			}
			else {
		        ByteArrayOutputStream newConsole0 = new ByteArrayOutputStream();
		        System.setOut(new PrintStream(newConsole0));
				searchall(root, srlist[0], srlist[0].length());
		        String str0 = newConsole0.toString();
		        String[] str0list = str0.split("\n");
		        ByteArrayOutputStream newConsole1 = new ByteArrayOutputStream();
		        System.setOut(new PrintStream(newConsole1));
				searchall(root, srlist[1], srlist[1].length());
		        System.setOut(previousConsole);
		        String str1 = newConsole1.toString();
		        String[] str1list = str1.split("\n");
		        int[] barr0 = new int[str0list.length];
		        int[] earr0 = new int[str0list.length];
		        for(int i=0; i<str0list.length;  i++) {
		        	String[] str0break = str0list[i].split(" ");
		        	if(str0break[0].equals("")) {
		        		return;
		        	}
		        	barr0[i] = Integer.parseInt(str0break[0]);
		        	earr0[i] = Integer.parseInt(str0break[1].substring(0, str0break[1].length()-1));
		        }
		        Arrays.sort(barr0);
		        Arrays.sort(earr0); 
		        int[] barr1 = new int[str1list.length];
		        int[] earr1 = new int[str1list.length];
		        for(int i=0; i<str1list.length;  i++) {
		        	String[] str1break = str1list[i].split(" ");
		        	if(str1break[0].equals("")) {
		        		return;
		        	}
		        	barr1[i] = Integer.parseInt(str1break[0]);
		        	earr1[i] = Integer.parseInt(str1break[1].substring(0, str1break[1].length()-1));
		        }
		        Arrays.sort(barr1);
		        Arrays.sort(earr1);
	            for(int i=0; i<barr0.length; i++) {
	            	for(int j=0; j<str1list.length; j++){
	            		if(barr1[j]>earr0[i]) {
	            			System.out.println(barr0[i]+" "+earr1[j]);
	            		}
	            	}
	            }
			}
		}
		else {
			if(srstr.substring(0,1).equals("*") && srstr.substring(srstr.length()-1, srstr.length()).equals("*")) {
				for(int i=0; i<strng.length();  i++) {
		        	for(int j=i; j<strng.length(); j++) {
		        		System.out.println(i+" "+j);
		        	}
		        }
			}
			else if(srstr.substring(srstr.length()-1, srstr.length()).equals("*")) {
		        ByteArrayOutputStream newConsole0 = new ByteArrayOutputStream();
		        System.setOut(new PrintStream(newConsole0));
				searchall(root, srlist[0], srlist[0].length());
	            System.setOut(previousConsole);
		        String str0 = newConsole0.toString();
		        String[] str0list = str0.split("\n");
		        int[] barr0 = new int[str0list.length];
		        int[] earr0 = new int[str0list.length];
		        for(int i=0; i<str0list.length;  i++) {
		        	String[] str0break = str0list[i].split(" ");
		        	if(str0break[0].equals("")) {
		        		return;
		        	}
		        	barr0[i] = Integer.parseInt(str0break[0]);
		        	earr0[i] = Integer.parseInt(str0break[1].substring(0, str0break[1].length()-1));
		        }
		        Arrays.sort(barr0);
		        Arrays.sort(earr0);
		        for(int j = 0; j<earr0.length; j++) {
		        	for(int i=earr0[j]; i<strng.length();  i++) {
		        		System.out.println(barr0[j]+" "+i);
			        }
		        }
			}
			else {
		        ByteArrayOutputStream newConsole0 = new ByteArrayOutputStream();
		        System.setOut(new PrintStream(newConsole0));
				searchall(root, srstr, srstr.length());
	            System.setOut(previousConsole);
		        String str0 = newConsole0.toString();
		        String[] str0list = str0.split("\n");
		        int[] barr0 = new int[str0list.length];
		        for(int i=0; i<str0list.length;  i++) {
		        	String[] str0break = str0list[i].split(" ");
		        	if(str0break[0].equals("")) {
		        		return;
		        	}
		        	barr0[i] = Integer.parseInt(str0break[0]);
		        }
		        Arrays.sort(barr0);
		        int len = srstr.length();
		        for(int j = 0; j<barr0.length; j++) {
		        	System.out.println(barr0[j]+" "+(barr0[j]+len-1));
		        }
			}
		}
	}
	static void printall(Node rt, int i, int l, int lvl) {
		Node start = rt.fchild;
		if(start==null) {
			System.out.println((rt.e-i-l)+" "+(rt.e-i-1));
		}
		while(start!=null) {
			printall(start, i+start.e-start.b, l, lvl+1);
			start = start.next;
		}
	}
	static void searchall(Node rt, String str, int l) {
			Node start = rt.fchild;
			while(start!=null) {
				int i = 0;
				String str1 = strng.substring(start.b, start.e);
				while(i<str1.length() && i<str.length()) {
					if(!(str1.substring(i,i+1).equals(str.substring(i,i+1))) && !str.substring(i,i+1).equals("?")) {
						break;
					}
					i++;
				}
				if(i==str.length()) {
					int r = str1.length()-i;
					printall(start, r, l, 0);
				}
				else if(i==str1.length()) {
					searchall(start, str.substring(i, str.length()), l);
				}
				start = start.next;
			}
	}
	static void add(int j, Node rt){
		if(rt.fchild==null) {
			Node newone = new Node(j, strng.length(), strng.length());
			newone.next = rt.fchild;
			rt.fchild = newone;
			return;
		}
		Node start = rt.fchild;
		while(start!=null && start.b!=start.e) {
			int i = 2;
			if(strng.substring(start.b, start.b+1).equals(strng.substring(j, j+1))) {
				while(i<=start.e-start.b) {
					if(!(strng.substring(j, j+i)).equals(strng.substring(start.b, start.b+i))) {
						Node bufnode = new Node(start.b+i-1, start.e, strng.length());
						start.e = start.b+i-1;
						Node newnode = new Node(j+i-1, strng.length(), strng.length());
						newnode.next = bufnode;
						bufnode.fchild = start.fchild;
						start.fchild = newnode;
						return;
					}
					i++;
				}
				add(j+i-1, start);
				return;
			}
			start = start.next;
		}
		Node newnode = new Node(j, strng.length(), strng.length());
		newnode.next = rt.fchild;
		rt.fchild = newnode;
	}
}
class Node {
	int b;
	int e;
	Node next;
	Node fchild = null;
	int numchild = 0;
	Node(int begin, int end, int l){
		b = begin;
		e = end;
		fchild = new Node(l, l);
	}
	Node(int begin, int end){
		b = begin;
		e = end;
	}
}

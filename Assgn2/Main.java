import java.util.*;
import java.io.*;
import java.lang.Exception;
class Company{
	static Node start = new Node(null); //adding a dummy node at the top
	static Node root; //this will be the ceo or the root of the company
	Company(String ceo){ //constructor of the company is called with the ceo name
		root = new Node(ceo); //node ceo is created
		start.addchild(root); //now the dummy node has the ceo as its child
		root.boss = start; //the boss of the ceo points to the dummy node
		root.rank = 1; //rank of ceo is 1
		Srtree.srroot = root; //root of the search tree is the ceo(root)
	}
	static void AddEmployee(String S, String S1) throws EmployeeAlreadyPresentException, EmployeeNotPresentException{
		Node fnd = Srtree.search(S, Srtree.srroot); //searching the parent of S in the bst
		if(S.equals(fnd.name)){
			throw new EmployeeAlreadyPresentException(S);
		}
		Node Sn = new Node(S); //making node with name S
		Sn.parent = fnd; //pointing to the parent
		if(S.compareTo(fnd.name)<0){ //adding according to lexography
			fnd.lchild = Sn;
		}
		else if(S.compareTo(fnd.name)>0){
			fnd.rchild = Sn;
		}
		Node S1n = Srtree.search(S1, Company.root); //searching for the node with name S'
		if(!S1.equals(S1n.name)){
			throw new EmployeeNotPresentException(S1);
		}
		S1n.addchild(Sn); //adding the employee to S'
		Sn.boss = S1n; //S points to S' as boss
		Sn.rank = S1n.rank+1; //rank of S is 1 more than that of S'
	}
	static void DeleteEmployee(String S, String S1)throws EmployeeNotPresentException{
		Node Sn = Srtree.search(S, Company.root); //searching name S
		if(!S.equals(Sn.name)){
			throw new EmployeeNotPresentException(S);
		}
		Node S1n = Srtree.search(S1, Company.root); //searching name S'
		if(!S1.equals(S1n.name)){
			throw new EmployeeNotPresentException(S1);
		}
		Node k = Sn.employees.head.next; //now all the employees of S have to point S' as the boss
		while(k.next!=null){
			k.boss = S1n;
			k = k.next;
		}
		S1n.employees.tail.prev.next = Sn.employees.head.next; //concatanating the two linklistes
		Sn.employees.head.next.prev = S1n.employees.tail.prev;
		S1n.employees.tail = Sn.employees.tail;
		Sn.employees.head = S1n.employees.head;
		Sn.prev.next = Sn.next;
		Sn.next.prev = Sn.prev;
		Srtree.delnode(Sn); //finally the node is to be deleted from the bst
	}
	static void LowestCommonBoss(String S, String S1)throws EmployeeNotPresentException{
		Node Sn = Srtree.search(S, Company.root); //searching for S
		if(!S.equals(Sn.name)){
			throw new EmployeeNotPresentException(S);
		}
		Node S1n = Srtree.search(S1, Company.root); //searching for S'
		if(!S1.equals(S1n.name)){
			throw new EmployeeNotPresentException(S1);
		}
		while(Sn.rank<S1n.rank){ //if they have unequal ranks first we need to find the parent of the node having higher rank such that this parent has rank equal to the other node
			S1n = S1n.boss;
		}
		while(Sn.rank>S1n.rank){
			Sn = Sn.boss;
		}
		while(Sn!=S1n){ //finally when the ranks are equal the traversal is reversed and we find the common boss
			Sn = Sn.boss;
			S1n = S1n.boss;
		}
		System.out.println(Sn.name);
	}
	public static void PrintEmployees(){
		MyStack<Node> bosses = new MyStack<Node>(); //making one stack to store the first level of employees
		bosses.push(Company.root); //starting of with the root
		while(!bosses.empty()){
			MyStack<Node> emps = new MyStack<Node>(); //to store the employees of those bosses in orderly fashion
			while(!bosses.empty()){
				Node boss = bosses.pop(); //one boss is popped
				Node emp = boss.employees.head.next;
				while(emp!=boss.employees.tail){ //after popping each employee of the boss that has popped just now is going to be pushed into the emp stack
					emps.push(emp);
					emp = emp.next;
				}
				System.out.println(boss.name); //print the name os the boss and continue
			}
			while(!emps.empty()){ //after the boss stack becomes empty once we again fill it with the employees that were stored
				bosses.push(emps.pop());
			} //after this the names are again printed until all is done
		}
	}
}
class Node{
	String name; //for the company tree****
	Linkdl employees;
	Node boss;
	int rank; //****
	Node next = null; //for the linked list
	Node prev = null; //****
	Node parent = null; //for the bst
	Node lchild = null;
	Node rchild = null; //******
	Node(){
	}
	Node(String nm){
		name = nm;
		employees = new Linkdl();
	}
	void addchild(Node nmc){
		employees.addsib(nmc); //adding sibling to the linked list
	}
}
class Header extends Node{ //making special header and trailer nodes
	Header(){
		employees = null;
	}
}
class Trailer extends Node{
	Trailer(){
		employees = null;
	}
}
class Linkdl{
	Node boss = null;
	int rank = 0;
	Node head = new Header();
	Node tail = new Trailer();
	Linkdl(){
		head.next = tail;
		tail.prev = head;
	}
	void addsib(Node nms){
		nms.next = tail;
		tail.prev.next = nms;
		nms.prev = tail.prev;
		tail.prev = nms;
	}
	void delsib(Node nms){
		nms.prev.next = nms.next;
		nms.next.prev = nms.prev;
		nms.prev = null;
		nms.next = null;
	}
}
class Srtree{
	static Node srroot = null;
	static Node search(String sr, Node srroot){
		Node fnd = srroot; //binary search
		if(sr.compareTo(srroot.name)<0){
			if(srroot.lchild==null){
				return srroot;
			}
			fnd = Srtree.search(sr, srroot.lchild);
		}
		else if(sr.compareTo(srroot.name)>0){
			if(srroot.rchild==null){
				return srroot;
			}
			fnd = Srtree.search(sr, srroot.rchild);
		}
		return fnd;
	}
	static void delnode(Node fnd){
		if(fnd.parent.lchild==fnd){ //taking the parrent of the found node and changing the pointers accordingly
			if(fnd.lchild==null){
				fnd.parent.lchild = fnd.rchild;
				return;
			}
			else if(fnd.rchild==null){
				fnd.parent.lchild = fnd.lchild;
				return;
			}
		}
		else{
			if(fnd.lchild==null){
				fnd.parent.rchild = fnd.rchild;
				return;
			}
			else if(fnd.rchild==null){
				fnd.parent.rchild = fnd.lchild;
				return;
			}
		}
		Node temp = fnd.rchild; //if fnd had two more children the the data of the successor of fnd is to be copied into fnd along with the pointers
		while(temp.lchild!=null){
			temp = temp.lchild;
		}
		temp.next.prev = fnd;
		temp.prev.next = fnd;
		fnd.name = temp.name;
		fnd.boss = temp.boss;
		fnd.rank = temp.rank;
		fnd.employees = temp.employees;
		temp.employees.boss = fnd;
		fnd.next = temp.next;
		fnd.prev = temp.prev;
		delnode(temp); //then that node will itself be deleted
	}
}
class MyStack<E>{
	private Node head = null;
	public void push(E item){
		Node alpha = new Node(item);
		alpha.next = head;
		head = alpha;
	}
	public E pop() {
		if(head != null){
			Node alpha = head;
			head = alpha.next;
			return alpha.getValue();
		}
		return null;
	}
	public Boolean empty(){
		if(head == null){
			return true;
		}
		else{
			return false;
		}
	}
	class Node{
		private E value;
		private Node next = null;
		private Node(E val){
			value = val;
		}
		private E getValue(){
			return value;
		}
	}
}
class EmployeeNotPresentException extends Exception{
	EmployeeNotPresentException(String er){
		System.err.println(er+" not found");
	}
}
class EmployeeAlreadyPresentException extends Exception{
	EmployeeAlreadyPresentException(String er){
		System.err.println(er+" already present");
	}
}
public class Main{
	public static void main(String args[]) throws FileNotFoundException, EmployeeAlreadyPresentException, EmployeeNotPresentException{
		FileInputStream fstream = new FileInputStream(args[0]);
		Scanner s = new Scanner(fstream);
		int n = Integer.parseInt(s.nextLine());
		String str = s.nextLine();
		String[] strargs = str.split(" ", 2);
		new Company(strargs[1]);
		Company.AddEmployee(strargs[0], strargs[1]);
		for(int j=2; j<n; j++){
			str = s.nextLine();
			strargs = str.split(" ", 2);
			Company.AddEmployee(strargs[0], strargs[1]);
		}
		n = Integer.parseInt(s.nextLine());
		for(int j=0; j<n; j++){
			str = s.nextLine();
			strargs = str.split(" ", 3);
			int fn = Integer.parseInt(strargs[0]);
			if(fn==3){
				Company.PrintEmployees();
			}
			else{
				if(fn==0){
					Company.AddEmployee(strargs[1], strargs[2]);
				}
				else if(fn==1){
					Company.DeleteEmployee(strargs[1], strargs[2]);
				}
				else{
					Company.LowestCommonBoss(strargs[1], strargs[2]);
				}
			}
		}
	}
}
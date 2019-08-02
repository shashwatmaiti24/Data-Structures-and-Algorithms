import java.lang.*;
//import javafx.util.Pair;
import java.lang.Exception;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
public class BestFit{
	public static void main(String args[]) throws FileNotFoundException, ObjectTooBigException{
		FileInputStream fstream = new FileInputStream(args[0]);
		Scanner s = new Scanner(fstream);
		String str = s.nextLine();
		String[] strargs = str.split(" ", 3);
		Bin bin = new Bin(Integer.parseInt(strargs[1]), Integer.parseInt(strargs[2]));
		new captreebin(bin);
		new avlbin(bin);

		while(s.hasNext()){			
			str = s.nextLine();			
			System.out.println("-------"+str+"--------");
			if(str.substring(0,1).equals("4") || str.substring(0,1).equals("3")){
				strargs = str.split(" ", 2);
			}
			else{
				strargs = str.split(" ", 3);
			}
			int fn = Integer.parseInt(strargs[0]);
			if(fn==4){
				contents(Integer.parseInt(strargs[1]));
			}
			else if(fn==3){
					delete_object(Integer.parseInt(strargs[1]));
			}
			else{
				if(fn==1){
					add_bin(Integer.parseInt(strargs[1]),Integer.parseInt(strargs[2]));
				}
				else{
					add_object(Integer.parseInt(strargs[1]), Integer.parseInt(strargs[2]));
				}
			}
			captreebin.printInorder(captreebin.root);
		}
	}
	public static void add_bin(int bin_id, int capacity){
		Bin newbin = new Bin(bin_id, capacity);
		captreebin.add_bin(newbin);
		avlbin.add_bin(newbin);
	}
	public static int add_object(int obj_id, int size) throws ObjectTooBigException{
		Obj newobj = new Obj(obj_id, size);
		Bin start = captreebin.root;
		while(start.rchild.capacity!=0){
			start = start.rchild;
		}
		start.addobject(newobj);
		newobj.mybin = start;
		captreebin.mov_bin(start);
		System.out.println(start.id);
		return start.id;
	}
	public static int delete_object(int obj_id){
		Obj delobj = avlobj.search_obj(obj_id);
		delobj.prev.next = delobj.next;
		delobj.next.prev = delobj.prev;
		Bin bin = delobj.mybin;
		bin.capacity = bin.capacity+delobj.size;
		captreebin.mov_bin(bin);
		System.out.println(bin.id);
		return bin.id;
	}
	public static List contents(int bin_id){
	//	ArrayList<Pair<Integer, Integer>> objcts = new ArrayList<Pair<Integer, Integer>>();
		Bin thisbin = avlbin.search(bin_id);
		Obj start = thisbin.objects.head.next;
		while(start!=thisbin.objects.tail){
			System.out.println(start.id+" "+start.size);
	//		objcts.add(new Pair(start.id,start.size));
			start=start.next;
		}
	//	return objcts;
		return null;
	}
}
class Bin{
	int id = -1;
	int capacity = 0; 
	Linkdl objects;
	Bin avlparent;
	Bin avllchild;
	Bin avlrchild;
	int avlheight;
	Bin parent;
	Bin lchild;
	Bin rchild;
	int height; 
	Bin(int idin, int capin){
		objects = new Linkdl();
		avlheight = 1;
		height = 1;
		avllchild = new avlleaf();
		avlrchild = new avlleaf();
		lchild = new capleaf();
		rchild = new capleaf();
		id = idin;
		capacity = capin;
	}
	Bin(){
		return;
	}
	void addobject(Obj newobj) throws ObjectTooBigException{
		objects.addsib(newobj);
		if(capacity<newobj.size){
			throw new ObjectTooBigException();
		}
		capacity = capacity-newobj.size;
		avlobj.add_obj(newobj);
	}
}
class capleaf extends Bin{
	int id;
	int capacity;
	Linkdl objects = null;
	Bin avlparent;
	int avlheight;
	Bin parent;
	int height;
	capleaf(){
		id = -1;
		capacity = 0;
		avlheight = 0;
		height = 0;
		Bin avllchild = null;
		Bin avlrchild = null;
		Bin lchild = null;
		Bin rchild = null;
	}
}
class avlleaf extends Bin{
	int id;
	int capacity;
	Linkdl objects = null;
	Bin avlparent;
	int avlheight;
	Bin parent;
	int height;
	avlleaf(){
		id = -1;
		capacity = 0;
		avlheight = 0;
		height = 0;
		Bin avllchild = null;
		Bin avlrchild = null;
		Bin lchild = null;
		Bin rchild = null;
	}
}
class captreebin{
	static Bin root;
	captreebin(Bin rt){
		root = rt;
	}
	static void printInorder(Bin node) 
    { 
        if (node == null) 
            return; 
        printInorder(node.lchild); 
        printInorder(node.rchild); 
    } 
	static Bin search_bin(int cap){
		Bin parent = root;
		Bin child = root;
		while(child.capacity!=0){
			if(cap<child.capacity){
				parent = child;
				child = child.lchild;			
			}
			else{
				parent = child;
				child = child.rchild;
			}
		}
		return parent;
	}
	static void add_bin(Bin newbin){
		Bin fbin = search_bin(newbin.capacity);
		if(fbin.capacity>newbin.capacity){
			fbin.lchild = newbin;
		}
		else{
			fbin.rchild = newbin;
		}
		newbin.parent = fbin;
		caprestructure(newbin);
	}
	static void mov_bin(Bin movbin){
		Bin fbin = search_bin(movbin.capacity);
		if(movbin==root){
			movbin.parent = new capleaf();
			movbin.parent.lchild = movbin;
		}
		Bin repbin;
		Bin b = movbin.parent;
		if(fbin==movbin){
			if(movbin==root){
				movbin.parent = null;
			}
			return;
		}
		else if(movbin.rchild.id==-1 && movbin.lchild.id==-1){
			if(b.rchild==movbin){
				b.rchild = new capleaf();
			}
			else if(b.lchild==movbin){
				b.lchild = new capleaf();
			}
			if(fbin.capacity>movbin.capacity){
				fbin.lchild = movbin;
			}
			else{
				fbin.rchild = movbin;
			}
			movbin.parent = fbin;
			movbin.height = 1;
			caprestructure(movbin);
				delrestructure(b);
			return;
		}
		else if(movbin.rchild.id==-1){
			if(movbin.parent.rchild==movbin){
				movbin.parent.rchild=movbin.lchild;
				movbin.lchild.parent=movbin.parent;
			}
			else{
				movbin.parent.lchild=movbin.lchild;
				movbin.lchild.parent=movbin.parent;
			}
			if(fbin.capacity>movbin.capacity){
				fbin.lchild = movbin;
			}
			else{
				fbin.rchild = movbin;
			}
			movbin.lchild = new capleaf();
			movbin.parent = fbin;
			movbin.height = 1;
			if(movbin==root){
				if(b.lchild==null){
					b.rchild.parent = null;
					root = b.rchild;
				}
				else{
					b.lchild.parent = null;
					root = b.lchild;
				}
				return;
			}
			caprestructure(movbin);
		
				delrestructure(b);
			return;
		}
		else if(movbin.lchild.id==-1){
			if(movbin.parent.rchild==movbin){
				movbin.parent.rchild=movbin.rchild;
				movbin.rchild.parent=movbin.parent;
			}
			else{
				movbin.parent.lchild=movbin.rchild;
				movbin.rchild.parent=movbin.parent;
			}
			if(fbin.capacity>movbin.capacity){
				fbin.lchild = movbin;
			}
			else{
				fbin.rchild = movbin;
			}
			movbin.rchild = new capleaf();
			movbin.parent = fbin;
			if(movbin==root){
				if(b.lchild==null){
					b.rchild.parent = null;
					root = b.rchild;
				}
				else{
					b.lchild.parent = null;
					root = b.lchild;
				}
				return;
			}
			caprestructure(movbin);
			delrestructure(b);
			
			return;
		}
		Bin shiftbin = movbin.rchild;
		repbin = shiftbin;
		while(repbin.lchild.capacity!=0){
			repbin = repbin.lchild;
		}
		if(repbin==shiftbin){
			if(b.rchild==movbin){
				b.rchild = repbin;
			}
			else{
				b.lchild = repbin;
			}
			repbin.parent = b;
			repbin.lchild = movbin.lchild;
			repbin.lchild.parent = repbin;
		}
		else{
			if(b.rchild==movbin){
				b.rchild = repbin;
			}
			else{
				b.lchild = repbin;
			}
			repbin.parent.lchild = repbin.rchild;
			if(repbin.rchild.capacity!=0){
				repbin.rchild.parent = repbin.parent;
			}
			repbin.parent = b;
			repbin.lchild = movbin.lchild;
			repbin.lchild.parent = repbin;
			repbin.rchild = shiftbin;
			shiftbin.parent = repbin;
		}
		if(fbin.capacity>movbin.capacity){
			fbin.lchild = movbin;
		}
		else{
			fbin.rchild = movbin;
		}
		movbin.parent = fbin;
		movbin.rchild = new capleaf();
		movbin.lchild = new capleaf();
		if(movbin==root){
			repbin.parent = null;
			root = repbin;
		}
		caprestructure(movbin);
		delrestructure(repbin);
	}
	static void delrestructure(Bin repbin){
		Bin z = repbin;
		if(z.lchild.id==-1 && z.rchild.id==-1){
			z = z.parent;
		}
		if(z==null){
			return;
		}
		Bin y;
		Bin x;
		if(z.lchild.height >= z.rchild.height){
			y = z.lchild;
		}
		else{
			y = z.rchild;
		}
		if(y.lchild.id==-1 && y.rchild.id==-1){
			z = z.parent;
		}
		if(z==null){
			return;
		}
		if(z.lchild.height >= z.rchild.height){
			y = z.lchild;
		}
		else{
			y = z.rchild;
		}
		if(y.lchild.height >= y.rchild.height){
			x = y.lchild;
		}
		else{
			x = y.rchild;
		}
		if(y.lchild.id==-1 && y.rchild.id==-1){
			z = z.parent;
		}
		if(z==null){
			return;
		}
		if(z.lchild.height >= z.rchild.height){
			y = z.lchild;
		}
		else{
			y = z.rchild;
		}
		if(y.lchild.height >= y.rchild.height){
			x = y.lchild;
		}
		else{
			x = y.rchild;
		}
		y.height = x.height+1;
		while(true){
			while(Math.abs(z.lchild.height-z.rchild.height)<=1){
				z.height = y.height+1;
				if(z==root){
					return;
				}
				x = y;
				y = z;
				z = z.parent;
			}
			Bin next = z.parent;
			if(z.lchild==y){
				if(y.lchild==x){
					z.lchild = y.rchild;
					y.rchild.parent = z;
					y.rchild = z;
					if(z==captreebin.root){
						captreebin.root = y;
					}
					else{
						if(z.parent.lchild==z){
							z.parent.lchild = y;
						}
						else{
							z.parent.rchild = y;
						}
					}
					y.parent = z.parent;
					z.parent = y;
				}
				else{
					y.rchild = x.lchild;
					x.lchild.parent = y;
					z.lchild = x.rchild;
					x.rchild.parent = z;
					x.lchild = y;
					x.rchild = z;
					if(z==captreebin.root){
						captreebin.root = x;
					}
					else{
						if(z.parent.lchild==z){
							z.parent.lchild = x;
						}
						else{
							z.parent.rchild = x;
						}
					}
					x.parent = z.parent;
					y.parent = x;
					z.parent = x;
				}
			}
			else{
				if(y.rchild==x){
					z.rchild = y.lchild;
					y.lchild.parent = z;
					y.lchild = z;
					if(z==captreebin.root){
						captreebin.root = y;
					}
					else{
						if(z.parent.lchild==z){
							z.parent.lchild = y;
						}
						else{
							z.parent.rchild = y;
						}
					}
					y.parent = z.parent;
					z.parent = y;
				}
				else{
					y.lchild = x.rchild;
					x.rchild.parent = y;
					z.rchild = x.lchild;
					x.lchild.parent = z;
					x.rchild = y;
					x.lchild = z;
					if(z==captreebin.root){
						captreebin.root = x;
					}
					else{
						if(z.parent.lchild==z){
							z.parent.lchild = x;
						}
						else{
							z.parent.rchild = x;
						}
					}
					x.parent = z.parent;
					y.parent = x;
					z.parent = x;
				}
			}
			if(z.lchild.height>z.rchild.height){
				z.height = z.lchild.height+1;
			}
			else{
				z.height = z.rchild.height+1;
			}
			if(y.lchild.height>y.rchild.height){
				y.height = y.lchild.height+1;
			}
			else{
				y.height = y.rchild.height+1;
			}
			if(x.id!=-1){
				if(x.lchild.height>x.rchild.height){
					x.height = x.lchild.height+1;
				}
				else{
					x.height = x.rchild.height+1;
				}
			}
			if(next==null){
				break;
			}
			z = next;
			if(z.lchild.height >= z.rchild.height){
				y = z.lchild;
			}
			else{
				y = z.rchild;
			}
			if(y.lchild.height >= y.rchild.height){
				x = y.lchild;
			}
			else{
				x = y.rchild;
			}
		}
	}
	static void caprestructure(Bin added){
		Bin x = added;
		if(x==root){
			return;
		}
		Bin y = x.parent;
		if(y==root){
			return;
		}
		Bin z = y.parent;
		if(y.lchild.height==y.rchild.height){
			return;
		}
		else{
			y.height = x.height+1;
		}
		while(Math.abs(z.lchild.height-z.rchild.height)<=1){
			z.height = y.height+1;
			if(z.lchild.height==z.rchild.height){
				return;
			}
			if(z==root){
				return;
			}
			x = y;
			y = z;
			z = z.parent;
		}
		if(z.lchild==y){
			if(y.lchild==x){
				z.lchild = y.rchild;
				y.rchild.parent = z;
				y.rchild = z;
				if(z==captreebin.root){
					captreebin.root = y;
				}
				else{
					if(z.parent.lchild==z){
						z.parent.lchild = y;
					}
					else{
						z.parent.rchild = y;
					}
				}
				y.parent = z.parent;
				z.parent = y;
			}
			else{
				y.rchild = x.lchild;
				x.lchild.parent = y;
				z.lchild = x.rchild;
				x.rchild.parent = z;
				x.lchild = y;
				x.rchild = z;
				if(z==captreebin.root){
					captreebin.root = x;
				}
				else{
					if(z.parent.lchild==z){
						z.parent.lchild = x;
					}
					else{
						z.parent.rchild = x;
					}
				}
				x.parent = z.parent;
				y.parent = x;
				z.parent = x;
			}
		}
		else{
			if(y.rchild==x){
				z.rchild = y.lchild;
				y.lchild.parent = z;
				y.lchild = z;
				if(z==captreebin.root){
					captreebin.root = y;
				}
				else{
					if(z.parent.lchild==z){
						z.parent.lchild = y;
					}
					else{
						z.parent.rchild = y;
					}
				}
				y.parent = z.parent;
				z.parent = y;
			}
			else{
				y.lchild = x.rchild;
				x.rchild.parent = y;
				z.rchild = x.lchild;
				x.lchild.parent = z;
				x.rchild = y;
				x.lchild = z;
				if(z==captreebin.root){
					captreebin.root = x;
				}
				else{
					if(z.parent.lchild==z){
						z.parent.lchild = x;
					}
					else{
						z.parent.rchild = x;
					}
				}
				x.parent = z.parent;
				y.parent = x;
				z.parent = x;
			}
		}
		if(z.lchild.height>z.rchild.height){
			z.height = z.lchild.height+1;
		}
		else{
			z.height = z.rchild.height+1;
		}
		if(y.lchild.height>y.rchild.height){
			y.height = y.lchild.height+1;
		}
		else{
			y.height = y.rchild.height+1;
		}
		if(x.lchild.height>x.rchild.height){
			x.height = x.lchild.height+1;
		}
		else{
			x.height = x.rchild.height+1;
		}
	}
}
class avlbin{
	static Bin root;
	avlbin(Bin rt){
		root = rt;
	}
	static Bin search(int uid){
		Bin parent = root;
		Bin child = root;
		while(child.id!=-1){
			if(uid==child.id){
				parent=child;
				break;
			}
			else if(uid<child.id){
				parent = child;
				child = child.avllchild;			
			}
			else{
				parent = child;
				child = child.avlrchild;
			}
		}
		return parent;
	}
	static void add_bin(Bin newbin){
		Bin fbin = search(newbin.id);
		if(fbin.avlrchild.id==-1){
			fbin.avlrchild = newbin;
		}
		else{
			fbin.avllchild = newbin;
		}
		newbin.avlparent = fbin;
		avlrestructure(newbin);
	}
	static void avlrestructure(Bin added){
		Bin x = added;
		if(x.avlparent!=null){
			return;
		}
		Bin y = x.avlparent;
		if(y.avlparent!=null){
			return;
		}
		Bin z = y.avlparent;
		if(y.avllchild.avlheight==y.avlrchild.avlheight){
			return;
		}
		else{
			y.avlheight = x.avlheight+1;
		}
		while(Math.abs(z.avllchild.avlheight-z.avlrchild.avlheight)<=1){
			z.avlheight = y.avlheight+1;
			if(z.avllchild.avlheight==z.avlrchild.avlheight){
				return;
			}
			if(z.avlparent==null){
				break;
			}
			x = y;
			y = z;
			z = y.avlparent;
		}
		if(z.avllchild==y){
			if(y.avllchild==x){
				z.avllchild = y.avlrchild;
				y.avlrchild = z;
				y.avlparent = z.avlparent;
				z.avlparent = y;
			}
			else{
				y.avlrchild = x.avllchild;
				z.avllchild = x.avlrchild;
				x.avllchild = y;
				x.avlrchild = z;
				x.avlparent = z.avlparent;
				y.avlparent = x;
				z.avlparent = x;
			}
		}
		else{
			if(y.rchild==x){
				z.rchild = y.lchild;
				y.lchild = z;
				y.parent = z.parent;
				z.parent = y;
			}
			else{
				y.lchild = x.rchild;
				z.rchild = x.lchild;
				x.rchild = y;
				x.lchild = z;
				x.parent = z.parent;
				y.parent = x;
				z.parent = x;
			}
		}
	}
}
class Obj{
	int id;
	int size;
	Bin mybin; 
	Obj parent;
	int height = 1;
	Obj lchild;
	Obj rchild; 
	Obj next = null;
	Obj prev = null;
	Obj(int idin, int sizein){
		rchild = new Objleaf();
		lchild = new Objleaf();
		id = idin;
		size = sizein;
	}
	Obj(){
		rchild = null; 
		lchild = null;
		id = -1;
		size = 0;
	}
}
class Objleaf extends Obj{
	int id;
	int size;
	Bin mybin; 
	Obj parent;
	int height = 0;
	Obj lchild;
	Obj rchild;
	Obj next = null;
	Obj prev = null;
	Objleaf(){
		rchild = null; 
		lchild = null;
		id = -1;
		size = 0;
	}
}
class avlobj{
	static Obj root;
	avlobj(Obj rt){
		root = rt;
	}
	static Obj search_obj(int uid){
		Obj parent = root;
		Obj child = root;
		while(child.id!=-1){
			if(child.id==uid){
				parent = child;
				break;
			}
			else if(uid<child.id){
				parent = child;
				child = child.lchild;			
			}
			else{
				parent = child;
				child = child.rchild;
			}
		}
		return parent;
	}
	static void add_obj(Obj newobj){
		if(root==null){
			root=newobj;
			return;
		}
		Obj fobj = search_obj(newobj.id);
		if(fobj.rchild.id==-1){
			fobj.rchild = newobj;
		}
		else{
			fobj.lchild = newobj;
		}
		newobj.parent = fobj;
		restructure(fobj);
	}
	static void restructure(Obj added){
		Obj x = added;
		Obj y;
		Obj z;
		if(x.parent!=null){
			y = x.parent;
		}
		else{
			return;
		}
		if(y.parent!=null){
			z = y.parent;
		}
		else{
			return;
		}
		if(y.lchild==x){
			if(y.rchild!=null){
				return;
			}
		}
		else if(y.rchild==x){
			if(y.lchild!=null){
				return;
			}
		}
		else{
			y.height = x.height+1;
		}
		while(Math.abs(z.lchild.height-z.rchild.height)<=1){
			z.height = y.height+1;
			if(z.lchild.height==z.rchild.height){
				return;
			}
			if(z.parent==null){
				return;
			}
			x = y;
			y = z;
			z = y.parent;
		}
		if(z.lchild==y){
			if(y.lchild==x){
				z.lchild = y.rchild;
				y.rchild = z;
				y.parent = z.parent;
				z.parent = y;
			}
			else{
				y.rchild = x.lchild;
				z.lchild = x.rchild;
				x.lchild = y;
				x.rchild = z;
				x.parent = z.parent;
				y.parent = x;
				z.parent = x;
			}
		}
		else{
			if(y.rchild==x){
				z.rchild = y.lchild;
				y.lchild = z;
				y.parent = z.parent;
				z.parent = y;
			}
			else{
				y.lchild = x.rchild;
				z.rchild = x.lchild;
				x.rchild = y;
				x.lchild = z;
				x.parent = z.parent;
				y.parent = x;
				z.parent = x;
			}
		}
	}
}
class Linkdl{
	Obj head = new Obj(0, 0);
	Obj tail = new Obj(0, 0);
	Linkdl(){
		head.next = tail;
		tail.prev = head;
	}
	void addsib(Obj nms){
		nms.next = tail;
		tail.prev.next = nms;
		nms.prev = tail.prev;
		tail.prev = nms;
	}
	void delsib(Obj nms){
		nms.prev.next = nms.next;
		nms.next.prev = nms.prev;
		nms.prev = null;
		nms.next = null;
	}
}
class ObjectTooBigException extends Exception{
}
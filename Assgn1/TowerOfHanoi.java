class TowerOfHanoi{
 	public static void toh_with_recursion(int num_disks, int start_pos, int end_pos){
 		if(start_pos==end_pos){ //if this case arises program has to return without any further execution
 			return;
 		}
		if(num_disks==1){  //base case
			System.out.println(start_pos+" "+end_pos);
		} 		
		else{
			TowerOfHanoi.toh_with_recursion(num_disks-1, start_pos, 6-start_pos-end_pos); //n-1 disks  are moved from start_pos to the auxiliary position
			System.out.println(start_pos+" "+end_pos); //nth disk is moved from start_pos to end_pos
			TowerOfHanoi.toh_with_recursion(num_disks-1, 6-start_pos-end_pos, end_pos); //n-1 disks are then moved from the auxiliary position to the start_pos
		}
 	}
	public static void toh_without_recursion(int num_disks, int start_pos, int end_pos) throws EmptyStackException{
		if(start_pos==end_pos){ //if this case arises program has to return without any further execution
			return;
		}
		MyStack<String> a = new MyStack <String>(); //to store the complete path at the end of the loop stores path. start_pos --> end_pos
		MyStack<String> b = new MyStack <String>(); //auxiliary stack to store the modified path momentarily at any point of time and be sent back to 'a' later. start_pos --> auxiliary_pos
		MyStack<String> c = new MyStack <String>(); //auxiliary stack to store the modified path momentarily at any point of time and be sent back to 'a' later. auxiliary_pos --> end_pos
		a.push(start_pos+" "+end_pos); //storing the base case
		int i;
 		for(i=1; i<num_disks; i++){ //beginning the loop with i=1 as the base case had already been solved
 			while(!a.empty()){ //now the stack is to be emptied and be stored in the other stacks for modifying
	 			String q = ""; //making two new strings to store the modified popped out string
	 			String r = "";
 				String s = a.pop(); //stores the popped out string
 				if(s.substring(0,1).equals(start_pos+"")){ //this part of code modifies the obtained string such that start_pos changes to auxiliary_pos from **********
 					r = r+(6-start_pos-end_pos)+"";
 				}
 				else if(s.substring(0,1).equals((6-start_pos-end_pos)+"")){
 					r = r+start_pos+"";
 				}
 				else{
 					r = r+s.substring(0,1);
 				}
 				r=r+" ";
 				if(s.substring(2,3).equals(start_pos+"")){
 					r = r+(6-start_pos-end_pos)+"";
 				}
 				else if(s.substring(2,3).equals((6-start_pos-end_pos)+"")){
 					r = r+start_pos+"";
 				}
 				else{
 					r = r+s.substring(2,3); //*********
 				}
 				c.push(r); //string the modified string in the stack 'c'
 				if(s.substring(0,1).equals(end_pos+"")){ //this part of code modifies the obtained string such that end_pos changes to auxiliary_pos from **********
 					q = q+(6-start_pos-end_pos)+"";
 				}
 				else if(s.substring(0,1).equals((6-start_pos-end_pos)+"")){
 					q = q+end_pos+"";
 				}
 				else{
 					q = q+s.substring(0,1);
 				}
 				q=q+" ";
 				if(s.substring(2,3).equals(end_pos+"")){
 					q = q+(6-start_pos-end_pos)+"";
 				}
 				else if(s.substring(2,3).equals((6-start_pos-end_pos)+"")){
 					q = q+end_pos+"";
 				}
 				else{
 					q = q+s.substring(2,3); //*********
 				}
 				b.push(q); //storing the modified string in stack 'b'
 			}
 			while(!c.empty()){ //since 'c' stores the path for auxiliary_pos --> end_pos this would be the last part of the path and hence stored first into the stack
 				a.push(c.pop());
 			}
 			a.push(start_pos+" "+end_pos); //here the step for nth disk is being pushed
 			while(!b.empty()){ //since 'b' stores the path for start_pos --> auxiliary_pos this would be the first part of the path and hence stored last into the stack
 				a.push(b.pop());
 			}
 		}
 		while(!a.empty()){ //finally the stack 'a' will pop steps one by one and print it on the screen
 			System.out.println(a.pop());
 		}
	}
}
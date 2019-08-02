class GeneralizedTowerOfHanoi{
 	public static void gtoh_with_recursion(int num_disks, int start_pos, int r, int b){
		if(r==b){ //for this case simply the toh will be called
			toh_with_recursion(num_disks, start_pos, b);
		}
		else if(num_disks==1){ //base case of n=1
			if(start_pos!=b){
				System.out.println(start_pos+" "+b);
			}
			return;
		}
		else if(num_disks>3){
			if(num_disks%2==1){ //if total no. of disks is odd
				if(start_pos!=b){
					if(start_pos!=r){
						toh_with_recursion(num_disks-1, start_pos, r); //since r is available so n-1 disks go to this position
					}
					else{
						toh_with_recursion(num_disks-1, start_pos, 6-b-r); //r is not available so we have to go to this position
					}
					System.out.println(start_pos+" "+b); //the nth disk moves to its assigned position
					if(start_pos==r){ //r was not available at the beginning so the code starts of as a new problem with n-1 disks
						GeneralizedTowerOfHanoi.gtoh_with_recursion(num_disks-1, 6-b-r, r, b);
					}
					else{ //r was available so n-1th disk is also at its position
						toh_with_recursion(num_disks-3, r, 6-b-r); //moving the top n-3 disks to the position which is neither r nor b
						System.out.println(r+" "+b); //moving the n-2th disk to its assigned position
						GeneralizedTowerOfHanoi.gtoh_with_recursion(num_disks-3, 6-b-r, r, b); //staring of a new problem with n-3 disks wich are at the auxiliary_pos
					}
				}
				else{
					toh_with_recursion(num_disks-2, start_pos, 6-b-r); //if however the nth disk is at its position n-2 disks will be moved
					System.out.println(start_pos+" "+r); //n-1th disk moves to the assigned position
					GeneralizedTowerOfHanoi.gtoh_with_recursion(num_disks-2, 6-b-r, r, b); //start of a new problem with n-2 disks
				}

			}
			else{
				if(start_pos!=r){ //similarly the code if disks were even
					if(start_pos!=b){
						toh_with_recursion(num_disks-1, start_pos, b);
					}
					else{
						toh_with_recursion(num_disks-1, start_pos, 6-b-r);
					}
					System.out.println(start_pos+" "+r);
					if(start_pos==b){
						GeneralizedTowerOfHanoi.gtoh_with_recursion(num_disks-1, 6-b-r, r, b);
					}
					else{
						toh_with_recursion(num_disks-3, b, 6-b-r);
						System.out.println(b+" "+r);
						GeneralizedTowerOfHanoi.gtoh_with_recursion(num_disks-3, 6-b-r, r, b);
					}
				}
				else{
					toh_with_recursion(num_disks-2, start_pos, 6-b-r);
					System.out.println(start_pos+" "+b);
					GeneralizedTowerOfHanoi.gtoh_with_recursion(num_disks-2, 6-b-r, r, b);
				}
			}
			return;
		}
		else if(num_disks==3){ //base case for n=3
			if(start_pos!=b){
				if(start_pos!=r){
					toh_with_recursion(num_disks-1, start_pos, r);
				}
				else{
					toh_with_recursion(num_disks-1, start_pos, 6-b-r);
				}
				System.out.println(start_pos+" "+b);
				if(start_pos==r){
					GeneralizedTowerOfHanoi.gtoh_with_recursion(num_disks-1, 6-b-r, r, b);
				}
				else{
					System.out.println(r+" "+b); //here toh for n-3 disks can't be called so directly that has been removed rest of the code is same as was for any odd case
				}
			}
			else{
				toh_with_recursion(num_disks-2, start_pos, 6-b-r);
				System.out.println(start_pos+" "+r);
				GeneralizedTowerOfHanoi.gtoh_with_recursion(num_disks-2, 6-b-r, r, b);
			}
			return;
		}
		else if(num_disks==2){ //for the base case of 2
			if(start_pos!=b){ //if b is avialable
				toh_with_recursion(num_disks-1, start_pos, b); //move the 1st disk to a
			}
			else{
				toh_with_recursion(num_disks-1, start_pos, 6-b-r); //else move it to the auxiliary position
			}
			if(start_pos!=r){
				System.out.println(start_pos+" "+r); //was 2nd disk already in it's position
			}
			if(start_pos==b){ //if b hadn't been available before 1 will have to be moved from the auxiliary position to the b position
				System.out.println((6-b-r)+" "+b);
			}
		}
		return;
 	}
 	public static void gtoh_without_recursion(int num_disks, int start_pos, int r, int b) throws EmptyStackException{
 		if(r==b){ //for this case simply the toh will be called
 			toh_without_recursion(num_disks, start_pos, b);
 			return;
 		}
 		while(num_disks>3){
 		 	if(num_disks%2==1){ //if total no. of disks is odd
 				if(start_pos!=b){
					if(start_pos!=r){
			 			toh_without_recursion(num_disks-1, start_pos, r); //since r is available so n-1 disks go to this position
			 		}
			 		else{
			 			toh_without_recursion(num_disks-1, start_pos, 6-b-r); //r is not available so we have to go to this position
			 		}
			 		System.out.println(start_pos+" "+b); //the nth disk moves to its assigned position
			 		if(start_pos==r){ //r was not available at the beginning so the code starts of as a new problem with n-1 disks and start_pos as the auxiliary_pos
			 			num_disks=num_disks-1;
			 			start_pos=6-b-r;
			 		}
			 		else{ //r was available so n-1th disk is also at its position
			 			toh_without_recursion(num_disks-3, r, 6-b-r); //moving the top n-3 disks to the position which is neither r nor b
			 			System.out.println(r+" "+b); //moving the n-2th disk to its assigned position
			 			num_disks=num_disks-3; //staring of a new problem with n-3 disks wich are at the auxiliary_pos, going back to the beginning of the loop
			 			start_pos=6-b-r;
			 		}
			 	}
			 	else{
			 		toh_without_recursion(num_disks-2, start_pos, 6-b-r); //if however the nth disk is at its position n-2 disks will be moved
					System.out.println(start_pos+" "+r); //n-1th disk moves to the assigned position
					num_disks=num_disks-2; //start of a new problem with n-2 disks, going back to the beginning of the loop
					start_pos=6-b-r;
			 	}
			}
	 		else{ //similarly the code for even value of n
	 			if(start_pos!=r){
					if(start_pos!=b){
	 					toh_without_recursion(num_disks-1, start_pos, b);
	 				}
	 				else{
	 					toh_without_recursion(num_disks-1, start_pos, 6-b-r);
	 				}
	 				System.out.println(start_pos+" "+r);
	 				if(start_pos==b){
	 					num_disks=num_disks-1;
						start_pos=6-b-r;
	 				}
	 				else{
			 			toh_without_recursion(num_disks-3, b, 6-b-r);
			 			System.out.println(b+" "+r);
			 			num_disks=num_disks-3;
						start_pos=6-b-r;
			 		}
			 	}
			 	else{
			 		toh_without_recursion(num_disks-2, start_pos, 6-b-r);
					System.out.println(start_pos+" "+b);
					num_disks=num_disks-2;
					start_pos=6-b-r;
			 	}
	 		}		
 		}
 		if(num_disks==3){ //base case for n=3
			if(start_pos!=b){
				if(start_pos!=r){
 					toh_without_recursion(num_disks-1, start_pos, r);
 				}
 				else{
 					toh_without_recursion(num_disks-1, start_pos, 6-r-b);
 				}
 				System.out.println(start_pos+" "+b);
 				if(start_pos==r){
 					System.out.println((6-b-r)+" "+b); //here instead of goin back to the beginning of the loop the two required steps were just printed out
 					System.out.println((6-b-r)+" "+r);
 				}
 				else{
 					System.out.println(r+" "+b); //here toh for n-3 disks can't be called so directly that has been removed rest of the code is same as was for any odd case
 				}
 			}
 			else{
 				toh_without_recursion(num_disks-2, start_pos, 6-b-r);
				System.out.println(start_pos+" "+r);
 				System.out.println((6-b-r)+" "+b);
 			}	
 			return;	
 		}
 		else if(num_disks==2){ //for the base case of 2
 			if(start_pos!=b){ //if b is avialable
 				toh_without_recursion(num_disks-1, start_pos, b); //move the 1st disk to a
 			}
 			else{
 				toh_without_recursion(num_disks-1, start_pos, 6-b-r); //else move it to the auxiliary position
 			}
 			if(start_pos!=r){
 				System.out.println(start_pos+" "+r); //was 2nd disk already in it's position
 			}
 			if(start_pos==b){
				System.out.println((6-b-r)+" "+b); //if b hadn't been available before 1 will have to be moved from the auxiliary position to the b position
			}
 			return;
 		}
 		else if(num_disks==1){ //base case of n=1
 			System.out.println(start_pos+" "+b);
 			return;
 		}
 	}
 	private static void toh_with_recursion(int num_disks, int start_pos, int end_pos){
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
	private static void toh_without_recursion(int num_disks, int start_pos, int end_pos) throws EmptyStackException{
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
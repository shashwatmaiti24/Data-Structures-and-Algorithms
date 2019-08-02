import java.lang.Exception;
class MyStack<E>{
	private Node head = null;
	public void push(E item){
		Node alpha = new Node(item);
		alpha.next = head;
		head = alpha;
	}
	public E pop() throws EmptyStackException {
		if(head != null){
			Node alpha = head;
			head = alpha.next;
			return alpha.getValue();
		}
		throw new EmptyStackException();
	}
	public E peek() throws EmptyStackException {
		if(head != null){
			return head.getValue();
		}
		throw new EmptyStackException();
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
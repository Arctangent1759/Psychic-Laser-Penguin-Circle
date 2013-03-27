/**
 * An enhanced version of the homework version of DListNode, with generics.
**/

package DList;

public class DList<T>{

	DListNode<T> sentinel;
	int size;

	public DList(){
		this.sentinel = new DListNode<T>(null,null,null,true);
		this.sentinel.prev=this.sentinel;
		this.sentinel.next=this.sentinel;
		this.size=0;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	public void pushFront(T item){
		this.sentinel.next = new DListNode<T>(item, this.sentinel, this.sentinel.next,false);
		this.sentinel.next.next.prev = this.sentinel.next;
		this.size++;
	}

	public void pushBack(T item){
		this.sentinel.prev = new DListNode<T>(item, this.sentinel.prev, this.sentinel,false);
		this.sentinel.prev.prev.next = this.sentinel.prev;
		this.size++;
	}

	public void push(T item){
		this.pushBack(item);
	}

	public T popFront(){
		T out = this.sentinel.next.item();
		this.sentinel.next=this.sentinel.next.next;
		this.sentinel.next.prev=this.sentinel;
		this.size--;
		return out;
	}

	public T popBack(){
		T out = this.sentinel.prev.item();
		this.sentinel.prev=this.sentinel.prev.prev;
		this.sentinel.prev.next=this.sentinel;
		this.size--;
		return out;
	}

	public T pop(){
		return this.popBack();
	}

	public DListNode<T> getFront(){
		if (this.sentinel.next.isSentinel){
			return null;
		}
		return this.sentinel.next;
	}

	public DListNode<T> getBack(){
		if (this.sentinel.prev.isSentinel){
			return null;
		}
		return this.sentinel.prev;
	}

	public T getItem(int index){
		DListNode<T> curr;
		if (index >= size || index < 0){
			throw new IndexOutOfBoundsException("Index " + index + " exceeds the size of the list.");
		}
		if (index < size/2){
			curr = sentinel.next;
			while (index-- > 0){
				curr=curr.next;
			}
			return curr.item();
		}else{
			index=size-index-1;
			curr=sentinel.prev;
			while (index-- > 0){
				curr=curr.prev;
			}
			return curr.item();
		}
	}

	public void appendFront(DList<T> d){
		d.sentinel.next.prev=this.sentinel;
		d.sentinel.prev.next=this.sentinel.next;
		this.sentinel.next.prev=d.sentinel.prev;
		this.sentinel.next=d.sentinel.next;
		this.size+=d.size;
		d.sentinel.next=d.sentinel;
		d.sentinel.next=d.sentinel;
		d.size=0;
	}
	public void appendBack(DList<T> d){
		d.sentinel.prev.next=this.sentinel;
		d.sentinel.next.prev=this.sentinel.prev;
		this.sentinel.prev.next=d.sentinel.next;
		this.sentinel.prev=d.sentinel.prev;
		this.size+=d.size;
		d.sentinel.next=d.sentinel;
		d.sentinel.next=d.sentinel;
		d.size=0;
	}
	public void append(DList<T> d){
		this.appendBack(d);
	}

	public int length(){
		return this.size;
	}

	public String toString(){
		DListNode<T> curr = sentinel.next;
		String out="[";
		while (!(curr.isSentinel)){
			out+=" "+curr.item().toString()+" ";
			curr=curr.next;
		}
		return out+"]";
	}

	public static void main(String[] args){
		DList<Integer> d = new DList<Integer>();
		d.push(0);
		d.push(1);
		d.push(2);
		d.push(3);
		d.push(4);
		d.push(5);
		DList<Integer> e = new DList<Integer>();
		e.push(2);
		d.appendBack(e);
		System.out.println(d);
		System.out.println(e);
	}
}

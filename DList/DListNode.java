/**
 *
 *	A single node in the DList datastructure.
 *	Now with generics!
 *
**/

package DList;
public class DListNode<T>{
	protected DListNode<T> prev;
	protected DListNode<T> next;
	protected T item;
	protected boolean isSentinel;

	/**
	 *
	 *	Constructs a DListNode.
	 *	@param obj the object to be stored in this node
	 *	@param prev the previous node
	 *	@param next the next node
	 *	@param isSentinel whether this node is the sentinel
	 *
	**/
	public DListNode(T obj, DListNode<T> prev, DListNode<T> next, boolean isSentinel){
		this.prev=prev;
		this.next=next;
		this.item=obj;
		this.isSentinel=isSentinel;
	}

	/**
	 *
	 * Returns the item contained by this DListNode.
	 * @return the item contained by this DListNode.
	 *
	**/
	public T item(){
		return item;
	}

	/**
	 *
	 *	Gets the next node.
	 *	@return this node's previous node, or null if this is the sentinel node.
	 *
	**/
	public DListNode<T> prev(){
		if (prev.isSentinel){
			return null;
		}
		return prev;
	}

	/**
	 *
	 *	Gets the previous node.
	 *	@return this node's next node, or null if this is the sentinel node.
	 *
	**/
	public DListNode<T> next(){
		if (next.isSentinel){
			return null;
		}
		return next;
	}
}

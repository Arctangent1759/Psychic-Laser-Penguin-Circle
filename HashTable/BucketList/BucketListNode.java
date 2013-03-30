package HashTable.BucketList;
class BucketListNode{
	protected Object item;
	protected BucketListNode next;
	protected BucketListNode prev;

	/*
	 * Constructs a DListNode object.
	 */
	protected BucketListNode(){
		this.item=null;
		this.prev=null;
		this.next=null;
	}
	public BucketListNode(Object item,BucketListNode prev,BucketListNode next){
		this.item=item;
		this.prev=prev;
		this.next=next;
	}
}

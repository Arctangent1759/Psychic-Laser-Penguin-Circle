package HashTable;
public interface Hashable{
	/**
	*
	*	Gets the hash of the object.
	*	@return the hash of o.
	*
	**/	
	public int getHash();
	/**
	*
	*	Returns the key value of a certain object.
	*	@return the key of the object.
	*
	**/
	public Object getKey();
	/**
	*
	*	Based on the key, return a value.
	*	@return the value corresponding with the key.
	**/
}

public interface Hashable{
	/**
	*
	*	Gets the hash of the object.
	*	@param o is the key.
	*	@return the hash of o.
	*
	**/	
	public Object getHash(Object a);
	/**
	*
	*	Returns the key value of a certain object.
	*	@param o is the object being transofrmed into a key.
	*	@return the key of the object.
	*
	**/
	public Object getKey(Object o);
	/**
	*
	*	Based on the key, return a value.
	*	@param o is the key itself.
	*	@return the value corresponding with the key.
	**/
	public Object getValue(Object o);
}

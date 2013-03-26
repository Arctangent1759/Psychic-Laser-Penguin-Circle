/**
 *
 *	Net represents a single network
 *
**/

package Board;

public class Net{
	protected int player;
	protected int length;

	/**
	 *
	 *	Constructs a new network
	 *	@param player specifies the player that the network belongs to
	 *	@param length sepcifies the length of the network
	 *
	**/
	public Net(int player,int length) {
		this.player=player;
		this.length=length;
	}
}

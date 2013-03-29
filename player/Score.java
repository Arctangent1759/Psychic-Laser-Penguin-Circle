package player;

public class Score {
	int score;
	Move move;
	
	public Score(Move m, int score) {
		move = m;
		this.score = score;
	}
	
	public Score() {
		move = null;
		score = 0;
	}
}
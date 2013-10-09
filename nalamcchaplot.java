
public class nalamcchaplot implements Player, Piece
{
	public board b;	//BOARD TO BE PLAYED ON  
	int size;	//SIZE OF THE BOARD
	int playertype, opponenttype; //DETERMINE TYPE OF PLAYER
	int numMoves;	//NUMBER OF MOVES PLAYED IN THE GAME SO FAR
	/*
	 * Find number of blank spots, white pieces and black pieces
	 */
	public int getWinner()
	{
		int white=b.numpieces(WHITE);
		int black=b.numpieces(BLACK);
		int dead=b.numpieces(DEAD);
		int empty=b.numpieces(EMPTY);
		
		//Base case
		if(empty!=0)
			return 0;
		
		//Normal case
		if(white>black)
			return WHITE;
		else if(black>white)
			return BLACK;
		else
			return DEAD;
	}
	
	/* Function called by referee to initialize the player.
	 *  Return 0 for successful initialization and -1 for failed one.
	 */
	public int init (int n, int p)
	{
		//Create a new board
		b = new board(n);
		playertype = p;
		
		//Assign Player types
		if(playertype==WHITE)
			opponenttype=BLACK;
		else if(playertype == BLACK)
			opponenttype = WHITE;
		if(b == null)
			return INVALID;

		//Set number of moves to zero
		numMoves=0;
		
		return 0;
	}
	
	/* Function called by referee to request a move by the player.
	 *  Return object of class Move
	 *  Use minmax algorithm here to predict bestmove
	 */
	public Move makeMove()
	{
		Move bestMove = minmax(b,playertype, opponenttype, 7);
		b.validMove(bestMove);
		return bestMove;
		
	}
	
	/* Function called by referee to inform the player about the opponent's move
	 *  Return -1 if the move is illegal otherwise return 0
	 */
	public int opponentMove(Move m)
	{
		
	}
	
	
	
}

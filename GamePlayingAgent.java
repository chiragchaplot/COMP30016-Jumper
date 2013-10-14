import java.io.PrintStream;


public class GamePlayingAgent implements Player, Piece
{
	public board b;	//BOARD TO BE PLAYED ON  
	int size;	//SIZE OF THE BOARD
	int playertype, opponenttype; //DETERMINE COLOUR OF PLAYER
	/*
	 * Find number of blank spots, white pieces and black pieces
	 */
	public int getWinner()
	{
		int white=b.numPieces(WHITE);
		int black=b.numPieces(BLACK);
		int dead=b.numPieces(DEAD);
		int empty=b.numPieces(EMPTY);
		
		//No winner yet
		if(empty!=0)
			return 0;
		
		//Winner decided on number of pieces
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
	public int init (int boardSize, int player)
	{
		//Create a new board
		b = new board(boardSize);
		playertype = player;
		size= boardSize;
		
		//Assign Player types
		if(playertype==WHITE)
			opponenttype=BLACK;
		else if(playertype == BLACK)
			opponenttype = WHITE;
		
		if (b==null || playertype!= WHITE || playertype!=BLACK){
			return -1;}
		else{
			return 0;}
	}
	
	/* Function called by referee to request a move by the player.
	 *  Return object of class Move
	 *  Use minmax algorithm here to predict bestmove
	 */
	
	public Move makeMove()
	{
		Move move = SearchAgent.bestMove(b,playertype, opponenttype);
		//System.out.println("makeMove for player"+playertype);
		b.validMove(move);
		//System.out.println("makeMove for player"+playertype);
		return move;
		
	}
	
	/* Function called by referee to inform the player about the opponent's move
	 *  Return -1 if the move is illegal otherwise return 0
	 */
	public int opponentMove(Move m)
	{
		//System.out.println("opponentMove for player"+playertype);
		int moveValid=0;
		if (b.validMove(m)==false){ 
			moveValid= INVALID;
			//System.out.println("invalid");
		}
		return moveValid;
		
	}

	
	public void printBoard(PrintStream output) {
		
		//output.println("board from player"+playertype);
		for(int row=0; row<size; row++){
			for(int col=0; col<size; col++) {
				output.print(b.getBd()[row][col]);
				output.print(' ');
			}
			output.print('\n');
		}
	}
	
	
	
}

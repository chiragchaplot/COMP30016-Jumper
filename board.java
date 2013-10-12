

/**
 * Class for the board object. Has methods for setting the board from a input file,
 * and displaying the board.
 */
public class board 
{
	//W - White Pieces, B- Black Pieces, D = Dead cell Spot, Blank=Blank cells
	public static int size;
	public int W;
	public int B;
	public int X;
	public int Blank;
	//initialize board as a 2D array of chars
	public static int[][]  bd;
	
	/*
	 * Constructor to set up board
	 */
	public board(int boardSize)
	{
		/*this.size=size;
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
			{
				bd[i][j]='-';
			}*/
		
		bd = new int[boardSize][boardSize];
		size= boardSize;
	}
	

/** Method for displaying the current board configuration
 * 
 */
	public void display()
	{
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				System.out.print(bd[i][j]);
				System.out.print(' ');
			}
			System.out.println(' ');
		}
		System.out.println(' ');
	}
	
	//Function to return number of cell types
	int numPieces(int type)
	{
		updatePieceCount();
		if (type==Piece.WHITE)
		{
			return W;
		}
		else if(type==Piece.BLACK)
		{
			return B;
		}
		else if(type==Piece.DEAD)
		{
			return X;
		}
		else if(type==Piece.EMPTY)
		{
			return Blank;
		}
		else
		{
			return Piece.INVALID;
		}
	}
	
	
	public void updatePieceCount(){
		int i,j;
		int black=0;
		int white=0;
		int dead=0;
		int empty=0;
		for(i=0;i<board.size;i++){
			for(j=0;j<board.size;j++){
				if (bd[i][j]==Piece.WHITE){white++;}
				if (bd[i][j]==Piece.BLACK){black++;}
				if (bd[i][j]==Piece.DEAD){dead++;}
				if (bd[i][j]==Piece.EMPTY){empty++;}	
				}
				
			}
		W= white;
		B= black;
		X= dead;
		Blank=empty;
	}


	// Inserts row,col into board with value player
	public void insert(int row, int col, int player) {
		bd[row][col] = player;
	
	}


	
	public int Size() {
		return size;
	}


	/**
	 * @return the bd
	 */
	public int[][] getBd() {
		return bd;
	}


	

}



	


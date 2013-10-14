import java.util.ArrayList;



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
	public int[][]  bd;

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



	public int getSize() {
		return size;
	}


	/**
	 * @return the bd
	 */
	public int[][] getBd() {
		return bd;
	}


	public boolean validMove(Move move)
	{
		//For place move
		if(move.IsPlaceMove)
		{

			if(!correctRowColSize(move)) return false;
			if(isJumpMove(move)) return false;
			if(placeMove(move))return true;
			else return false;
		}

		//For Jump Moves
		else
		{
			if(!correctRowColSize(move)) return false;
			if(!isJumpMove(move)) return false;
			if(checkJumpMove(move)) return true;
			else return false;
		}
	}

	public boolean placeMove(Move move)
	{

		int x=move.RowPositions[0];
		int y=move.ColPositions[0];
		//Check for out of bounds condition
		if(isOnBoard(x,y)== false) return false;

		//If cell is not empty
		if (!cellEmpty(x,y)) return false;

		//It's normal
		else
		{
			bd[x][y]=move.P;
			return true;
		}
	}

	public boolean isOnBoard(int x, int y){
		if(x<0 || x>size-1){
			return false;
		}
		else if(y<0|| y>size-1){
			return false;
		}
		else return true;

	}

	//if empty return true, if not empty return false
	public boolean cellEmpty(int x, int y){
		if(bd[x][y]!=Piece.EMPTY) return false;
		else return true;
	}

	public boolean correctRowColSize(Move move){
		if(move.RowPositions.length==0 || move.ColPositions.length==0 ||move.RowPositions.length!=move.ColPositions.length) return false;
		else return true;
	}

	public boolean isJumpMove(Move move){
		if(move.RowPositions.length<2) return false;
		else return true;
	}

	public boolean checkJumpMove(Move move)
	{
		//System.out.println("checkJumpmove");
		int[] x= move.RowPositions;
		int[] y= move.ColPositions;
		//is jump possible

		for (int i=0; i<x.length-1; i++) 
		{// Check if jump move is into valid position
			if (!jumpMovePossible(x[i],x[i+1],y[i], y[i+1])) 
			{
				return false;	
			}

			// Insert jump move
			bd[x[i]][y[i]] = move.P;
			bd[x[i+1]][y[i+1]] = move.P;

			// piece that was jumped over is the opponent's piece
			int xMiddle= (x[i]+x[i+1])/2;
			int yMiddle= (y[i]+y[i+1])/2;		
			if (bd[xMiddle][yMiddle] != move.P) 
			{
				bd[xMiddle][yMiddle] = Piece.DEAD;	
			}
		}

		return true;
	}


	public boolean jumpMovePossible(int x1, int x2, int y1, int y2)
	{

		//System.out.println("jumpMovePossible functions");
		int xMiddle= (x1+x2)/2;
		int yMiddle= (y1+y2)/2;
		//Check if r1,r2,c1,c2 are valid
		if(!isOnBoard(x1,y1)) return false;
		if(!isOnBoard(x2,y2)) return false;

		//Check if r2 and c2 are empty
		else if(!cellEmpty(x2,y2)) return false;

		//Check if middle cells are in valid 
		//Not dead
		//Not Empty
		else if(cellEmpty(xMiddle, yMiddle)) return false;
		else if(bd[xMiddle][yMiddle]==Piece.DEAD) return false;

		//Check if the pieces are actually one jump away or not


		else if (x1-2==x2 && y1-2==y2)  return true;
		else if (x1==x2 && y1-2==y2)  return true;
		else if (x1+2==x2 && y1-2==y2)  return true;
		else if (x1-2==x2 && y1==y2)  return true;
		else if (x1+2==x2 && y1==y2)  return true;
		else if (x1-2==x2 && y1+2==y2)  return true;
		else if (x1==x2 && y1+2==y2)  return true;
		else if (x1+2==x2 && y1+2==y2)  return true;


		else return false;
	}


	// Returns possible moves given current board state
	public ArrayList<Move> moveOptions(int player) 
	{
		ArrayList<Move> achievableMoves = new ArrayList<Move>();
		achievableMoves = getJumpMoves(player);
		achievableMoves= getPlaceMoves(achievableMoves, player);
		return achievableMoves;
	}

	private ArrayList<Move> getPlaceMoves(ArrayList<Move> achievableMoves, int player) {
		// Searches board for empty places and adds each single
		// move into possible moves
		Move placeMoves;
		for (int x=0; x<size; x++) 
			for (int y=0; y<size; y++) 
			{
				if (bd[x][y] == Piece.EMPTY) {
					int[] xArray = {x};
					int[] yArray = {y};
					placeMoves = new Move(player, true, xArray, yArray);
					achievableMoves.add(placeMoves);
				}
			}

		return achievableMoves;
	}

	/*
	 * Take playertype and board config then returns all possible jump moves
	 * @param: player, board configuration
	 */
	public ArrayList<Move> getJumpMoves(int player)
	{
		ArrayList<Move> jumpMovesArrayList = new ArrayList <Move>();

		//For each player piece, find jump move
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
			{
				if(bd[i][j]==player)
				{
					simpleJump(jumpMovesArrayList,i,j,player);
				}
			}
		return jumpMovesArrayList;
	}

	public void simpleJump(ArrayList<Move> jumpMovesArrayList, int fromX, int fromY, int playertype)
	{
		int[] toX= {fromX-2, fromX, fromX+2, fromX-2, fromX+2, fromX-2, fromX, fromX+2};
		int[] toY= {fromY-2, fromY-2, fromY-2, fromY, fromY, fromY+2, fromY+2, fromY+2,};

		for(int i=0; i<toX.length; i++){

			if(jumpMovePossible(fromX, toX[i], fromY, toY[i])){
				ArrayList<Integer> ColPositions = new ArrayList<Integer>();
				ArrayList<Integer> RowPositions = new ArrayList<Integer>();
				RowPositions.add(fromX);
				RowPositions.add(toX[i]);
				ColPositions.add(fromY);
				ColPositions.add(toY[i]);
				bd[toX[i]][toY[i]]=playertype;
				compoundJump(jumpMovesArrayList, RowPositions, ColPositions, toX[i], toY[i], playertype);
				bd[toX[i]][toY[i]]=Piece.EMPTY;

			}
		}


	}


	public void compoundJump(ArrayList<Move> jumpMovesArrayList, ArrayList<Integer> rowPos,ArrayList<Integer> colPos,int fromX, int fromY, int player)
	{
		int terminal=1;
		int[] toX= {fromX-2, fromX, fromX+2, fromX-2, fromX+2, fromX-2, fromX, fromX+2};
		int[] toY= {fromY-2, fromY-2, fromY-2, fromY, fromY, fromY+2, fromY+2, fromY+2,};


		for(int i=0; i<toX.length; i++){

			if(jumpMovePossible(fromX, toX[i], fromY, toY[i])){
				terminal=0;

				//Add to list of nodes visited
				rowPos.add(toX[i]);
				colPos.add(toY[i]);

				//Insert player
				bd[fromX][fromY]=player;

				//Check for more jumps by recursion
				compoundJump(jumpMovesArrayList, rowPos, colPos, toX[i],toY[i],player);

				//After checking make the piece empty to check for new avenues
				bd[toX[i]][toY[i]]=Piece.EMPTY;

			}
		}

		if(terminal==1)
		{
			//Convert ArrayList to Array
			int[] RowPositions = getArray(rowPos);
			int[] ColPositions = getArray(colPos);

			//Initialize the move
			Move m = new Move(player, false, RowPositions, ColPositions);

			//Add it to move
			jumpMovesArrayList.add(m);
		}

		rowPos.remove(rowPos.size()-1);
		colPos.remove(colPos.size()-1);
	}

	//Get array from array list
	public static int[] getArray(ArrayList<Integer> num)
	{
		int[] temp = new int[num.size()];
		for(int i=0;i<num.size();i++)
		{
			temp[i]=num.get(i);
		}

		return temp;
	}
}







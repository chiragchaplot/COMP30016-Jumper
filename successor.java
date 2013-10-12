import java.util.ArrayList;

public class successor
{
	
	// Returns possible moves given current board state
	public static ArrayList<Move> moveOptions(int player, board board) 
	{
		ArrayList<Move> achievableMoves = new ArrayList<Move>();
		achievableMoves = getJumpMoves(player,board);
		achievableMoves= getPlaceMoves(achievableMoves, player, board);
		return achievableMoves;
	}
	
	private static ArrayList<Move> getPlaceMoves(ArrayList<Move> achievableMoves,
			int player, board board) {
		// Searches board for empty places and adds each single
		// move into possible moves
		Move placeMoves;
		for (int x=0; x<board.size; x++) 
			for (int y=0; y<board.size; y++) 
			{
				if (board.bd[x][y] == Piece.EMPTY) {
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
	public static ArrayList<Move> getJumpMoves(int player, board b)
	{
		ArrayList<Move> jumpMovesArrayList = new ArrayList <Move>();
		
		//For each player piece, find jump move
		for(int i=0;i<board.size;i++)
			for(int j=0;j<board.size;j++)
			{
				if(board.bd[i][j]==player)
				{
					simpleJump(jumpMovesArrayList,i,j,player,b);
				}
			}
		return jumpMovesArrayList;
	}
			
			
	/*
	 * Find the first jump moves from a given index
	 */
	public static void simpleJump(ArrayList<Move> jumpMovesArrayList, int fromX, int fromY, int playertype, board board)
	{
		int[] toX= {fromX-2, fromX, fromX+2, fromX-2, fromX+2, fromX-2, fromX, fromX+2};
		int[] toY= {fromY-2, fromY-2, fromY-2, fromY, fromY, fromY+2, fromY+2, fromY+2,};
		
		for(int i=0; i<toX.length; i++){
		
		if(moveHandler.jumpMovePossible(fromX, toX[i], fromY, toY[i], board)){
			ArrayList<Integer> ColPositions = new ArrayList<Integer>();
			ArrayList<Integer> RowPositions = new ArrayList<Integer>();
			RowPositions.add(fromX);
			RowPositions.add(toX[i]);
			ColPositions.add(fromY);
			ColPositions.add(toY[i]);
			board.bd[toX[i]][toY[i]]=playertype;
			compoundJump(jumpMovesArrayList, RowPositions, ColPositions, toX[i], toY[i], playertype, board);
			board.bd[toX[i]][toY[i]]=Piece.EMPTY;
			
		}
		}
		
		
	}
	
	/*
	 * To account for compound jump moves
	 */
	public static void compoundJump(ArrayList<Move> jumpMovesArrayList, ArrayList<Integer> rowPos,ArrayList<Integer> colPos,int fromX, int fromY, int player, board board)
	{
		int terminal=1;
		int[] toX= {fromX-2, fromX, fromX+2, fromX-2, fromX+2, fromX-2, fromX, fromX+2};
		int[] toY= {fromY-2, fromY-2, fromY-2, fromY, fromY, fromY+2, fromY+2, fromY+2,};
		
		
		for(int i=0; i<toX.length; i++){
			
			if(moveHandler.jumpMovePossible(fromX, toX[i], fromY, toY[i], board)){
				terminal=0;
				
				//Add to list of nodes visited
				rowPos.add(toX[i]);
				colPos.add(toY[i]);
				
				//Insert player
				board.bd[fromX][fromY]=player;
				
				//Check for more jumps by recursion
				compoundJump(jumpMovesArrayList, rowPos, colPos, toX[i],toY[i],player, board);
				
				//After checking make the piece empty to check for new avenues
				board.bd[toX[i]][toY[i]]=Piece.EMPTY;
				
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
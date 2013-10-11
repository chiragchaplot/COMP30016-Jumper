import java.util.ArrayList;

public class successor
{
	
	node expand(node Node, int player)
	{
		findJumpMoves(Node, player);
		findPlaceMoves(Node, player);
	}
	
	// Returns possible moves given current board state
	public ArrayList<Move> achievableMoves(int player, board b) 
	{
		ArrayList<Move> achievableMoves = new ArrayList<Move>();
		ArrayList<Move> jumpMoves = new ArrayList<Move>();
		
		// Get the achievable jump moves
		jumpMoves = getJumpMoves(player,b);
		
		// Inserts jump moves into the achievableMoves array
		if (jumpMoves!=null) {
			for (int i=0; i < jumpMoves.size(); i++) {
				achievableMoves.add(jumpMoves.get(i));
			}
		}
		
		Move singleMoves;
		int[] rowPos = new int[1];
		int[] colPos = new int[1];
		
		// Searches board for empty places and adds each single
		// move into possible moves
		for (int row=0; row<b.size; row++) 
			for (int col=0; col<b.size; col++) 
			{
				if (b.bd[row][col] == Piece.EMPTY) 
				{
					rowPos[0] = row;
					colPos[0] = col;
					singleMoves = new Move(player, true, rowPos, colPos);
					achievableMoves.add(singleMoves);
					rowPos = new int[1];
					colPos = new int[1];
				}
			}

		return achievableMoves;
	}
	
	/*
	 * Take playertype and board config then returns all possible jump moves
	 * @param: player, board configuration
	 */
	public ArrayList<Move> getJumpMoves(int player, board b)
	{
		ArrayList<Move> jumpMoves = new ArrayList <Move>();
		
		//Find every piece of the player
		for(int i=0;i<b.size;i++)
			for(int j=0;j<b.size;j++)
			{
				if(b.bd[i][j]==player)
				{
					firstJump(jumpMoves,i,j,player,b);
				}
			}
		return jumpMoves;
	}
			
			
	/*
	 * Find the first jump moves from a given index
	 */
	public void firstJump(ArrayList<Move> jumpMoves, int r, int c, int playertype, board b)
	{
		for(int i=r-2;i<r+2;i=i+2)
			for(int j=c-2;j<c+2;j=j+2)
			{
				if (i==r && j==c)
				{
					continue;
				}
				
				//check if one can jump into the specified row and column
				if(b.jumpMovePossible(r, c, i, j))
				{
				}
				ArrayList<Integer> colPos = new ArrayList<Integer>();
				ArrayList<Integer> rowPos = new ArrayList<Integer>();
				
				//Add the new row positions
				rowPos.add(r);
				rowPos.add(i);
				
				//Add the column positions
				colPos.add(c);
				colPos.add(j);
				
				//Insert the player
				b.bd[i][j]=playertype;
				
				//Find all successive jumps from this new position
				compoundJump(jumpMoves, rowPos, colPos, r, c, playertype, b);
				
				//After finding all the moves remove the cell to empty
				b.bd[i][j]=Piece.EMPTY;
			}
	}
	
	/*
	 * To account for compound jump moves
	 */
	public void compoundJump(ArrayList<Move> jumpMoves, ArrayList<Integer> rowPos,ArrayList<Integer> colPos,int row, int col, int player, board b)
	{
		int terminal=1;
		
		for(int i=row-2;i<row+2;i=i+2)
			for(int j=col-2;j<col+2;j=j+2)
			{
				if(i==row && j==col)
				{
					continue;
				}
				else if(b.jumpMovePossible(row, col, i, j))
				{
					terminal=0;
					
					//Add to list of nodes visited
					rowPos.add(i);
					colPos.add(j);
					
					//Insert player
					b.bd[row][col]=player;
					
					//Check for more jumps by recrusion
					compoundJump(jumpMoves, rowPos, colPos, i,j,player, b);
					
					//After checking make the piece empty to check for new avenues
					b.bd[i][j]=Piece.EMPTY;					
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
			jumpMoves.add(m);
		}
		
		rowPos.remove(rowPos.size()-1);
		colPos.remove(colPos.size()-1);
	}
	
	//Get array from array list
	public int[] getArray(ArrayList<Integer> num)
	{
		int[] temp = new int[num.size()];
		for(int i=0;i<num.size();i++)
		{
			temp[i]=num.get(i);
		}
		
		return temp;
	}

}
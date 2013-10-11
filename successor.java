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
	/*
	 * // Takes an initial position and calculates jump from there
	// If valid jump, calls currentJump
	// Inserts jump moves from position (row,col)
	private void initialJumps(ArrayList<Move> jumpMoves, int row, int col, int player) {
		
		for (int r = row-2; r <= row+2; r+=2) {
			for (int c = col-2; c <= col+2; c+=2) {
				// skip case of initial jump position
				if (r == row && c == col) {
					continue;
				}
				// checks to see if can jump into r,c
				if (canJump(row, col, r, c)) {
					ArrayList<Integer> rowPositions = new ArrayList<Integer>();
					ArrayList<Integer> colPositions = new ArrayList<Integer>();
					
					rowPositions.add(row);
					rowPositions.add(r);
					colPositions.add(col);
					colPositions.add(c);
					
					// Inserts jumps into board and calls currentJumps
					insert(r,c,player);
					currentJumps(jumpMoves, rowPositions,colPositions,r,c,player);
					// Removes initial jump from board
					remove(r,c);
				}
			}
		}
	}
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
		
	}

}
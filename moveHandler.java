
public class moveHandler implements Piece {
	
	public static boolean validMove(Move move, board board)
	{
		if(!sameRowColSize(move)) return false;
		
		//For place move
		if(move.IsPlaceMove)
		{
			
			if(checkMoveType(move)) return false;
			if(placeMove(move, board))return true;
			else return false;
		}
		
		//For Jump Moves
		else
		{
			if(!checkMoveType(move)) return false;
			if(pushjumpMove(move, board)) return true;
			else return false;
		}
	}
	
	
	public static boolean placeMove(Move move, board board)
	{
		
		int x=move.RowPositions[0];
		int y=move.ColPositions[0];
		//Check for out of bounds condition
		if(isOnBoard(x,y,board)== false) return false;
		
		//If cell is not empty
		if (!cellEmpty(x,y, board)) return false;
		
		//It's normal
		else
		{
			board.bd[x][y]=move.P;
			return true;
		}
	}
	
	public static boolean isOnBoard(int x, int y, board board){
		if(x<0 || x>board.size-1){
			return false;
		}
		else if(y<0|| y>board.size-1){
				return false;
			}
		else return true;
		
	}
	
	public static boolean cellEmpty(int x, int y, board board){
		if(board.bd[x][y]!=EMPTY) return false;
		else return true;
	}
	
	
	public static boolean sameRowColSize(Move move){
		if(move.RowPositions.length!=move.ColPositions.length) return false;
		else return true;
	}
	
	//return true if move type is jump false if move type is place
	public static boolean checkMoveType(Move move){
		if(move.RowPositions.length<2) return false;
		else return true;
	}
	
	public static boolean pushjumpMove(Move move, board board)
	{
		//is jump possible
		int[] x= move.RowPositions;
		int[] y= move.ColPositions;
		for (int i=0; i<x.length-1; i++) 
		{
			
			// Check if jump move is into valid position
			if (!jumpMovePossible(x[i],y[i],x[i+1], y[i+1])) 
			{
				return false;	
			}
			
			// Insert jump move
			board.bd[x[i]][y[i]] = move.P;
			board.bd[x[i+1]][y[i+1]] = move.P;
			
			// piece that was jumped over is the opponent's piece
			if (board.bd[x[i]][y[i]] != move.P) 
			{
				board.bd[x[i]][y[i]] = Piece.DEAD;	
			}
		}
		
		return true;
		}

}

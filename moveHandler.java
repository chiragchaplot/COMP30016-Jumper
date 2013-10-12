
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
		{// Check if jump move is into valid position
			if (!jumpMovePossible(x[i],y[i],x[i+1], y[i+1], board)) 
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

	
	public static boolean jumpMovePossible(int r1, int c1, int r2, int c2, board board)
	{
		//Check if r1,r2,c1,c2 are valid
		if(r1<0||r2<0||c1<0||c2<0)
		{
			return false;
		}
		else if(r1>size||r2>size||c1>size||c2>size)
		{
			return false;
		}
		
		//Check if r2 and c2 are empty
		else if(bd[r2][c2]!=Piece.EMPTY)
		{
			return false;
		}
		
		//Check if middle cells are in valid 
		//Not dead
		//Not Empty
		else if(bd[(r1+r2)/2][(c1+c2)/2]==Piece.EMPTY)
		{
			return false;
		}
		else if(bd[(r1+r2)/2][(c1+c2)/2]==Piece.DEAD)
		{
			return false;
		}
		
		//Check if the pieces are actually one jump away or not
		else
		{
			for(int i=r1-2;i<r1+2;i=i+2)
				for(int j=c1-2;j<c1+2;j=j+2)
				{
					//When reaching r1 and c1 ignore it
					if(i==r1&&j==c1)
					{
						continue;
					}
					//When reaching r2 and c2 return true
					else if(i==r2&&j==c2)
					{
						return true;
					}
				}
		}
		
		return false;
	}
}

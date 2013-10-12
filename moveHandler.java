
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
			if(checkJumpMove(move, board)) return true;
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
			board.getBd()[x][y]=move.P;
			return true;
		}
	}
	
	public static boolean isOnBoard(int x, int y, board board){
		if(x<0 || x>board.Size()-1){
			return false;
		}
		else if(y<0|| y>board.Size()-1){
				return false;
			}
		else return true;
		
	}
	
	//if empty return true, if not empty return false
	public static boolean cellEmpty(int x, int y, board board){
		if(board.getBd()[x][y]!=EMPTY) return false;
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
	
	public static boolean checkJumpMove(Move move, board board)
	{
		int[] x= move.RowPositions;
		int[] y= move.ColPositions;
		//is jump possible
		
		for (int i=0; i<x.length-1; i++) 
		{// Check if jump move is into valid position
			if (!jumpMovePossible(x[i],x[i+1],y[i], y[i+1], board)) 
			{
				return false;	
			}
			
			// Insert jump move
			board.getBd()[x[i]][y[i]] = move.P;
			board.getBd()[x[i+1]][y[i+1]] = move.P;
			
			// piece that was jumped over is the opponent's piece
			int xMiddle= (x[i]+x[i+1])/2;
			int yMiddle= (y[i]+y[i+1])/2;		
			if (board.getBd()[xMiddle][yMiddle] != move.P) 
			{
				board.getBd()[xMiddle][yMiddle] = Piece.DEAD;	
			}
		}
		
		return true;
		}

	
	public static boolean jumpMovePossible(int x1, int x2, int y1, int y2, board board)
	{
		
		int xMiddle= (x1+x2)/2;
		int yMiddle= (y1+y2)/2;
		//Check if r1,r2,c1,c2 are valid
		if(!isOnBoard(x1,y1, board)) return false;
		if(!isOnBoard(x2,y2, board)) return false;
		
		//Check if r2 and c2 are empty
		else if(!cellEmpty(x2,y2, board)) return false;
		
		//Check if middle cells are in valid 
		//Not dead
		//Not Empty
		else if(cellEmpty(xMiddle, yMiddle, board)) return false;
		else if(board.getBd()[xMiddle][yMiddle]==Piece.DEAD) return false;
		
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
}

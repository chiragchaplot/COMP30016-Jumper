
/**
 * Class defining all the legal moves in the game
 */

import java.util.ArrayList;
public class rules 
{
        
	/**
	 * Decides what all moves a piece can make based on position
	 * takes input board configuration from board
	 * iterates over all the board coordinates to find empty spot for place move
	 * returns the number of place moves possible legally (including jump moves)
	 * @param boardConfig
	 * @return placeMoves
	 */
	public static int numPlaceMoves( char[][] boardConfig)
	{
		
		//Number of moves 
		int placeMoves=0;
		
		for(int i=0;i<board.size;i++)
		{
			for(int j=0;j<board.size;j++)
			{
				if(boardConfig[i][j]=='-')
				{
					placeMoves+=1;
				}
			}
		}
		return placeMoves;
	}
	
	/**
	 * A function to decide if a coordinate is on the board
	 * takes two integer inputs for x and y coordinate
	 * returns a boolean true or false
	 * @param x
	 * @param y
	 * @return isOnBoard
	 */
	
	public static boolean isOnBoard(int x, int y)
	{
		if(x<board.size && x>=0 && y<board.size && y>=0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//variable to store number of jumpmoves
	static int jumpMoves;
	
	/**Method to call numJumpMoves() for each of the pieces of a given colour
	 * takes input of a char "B" or "W", return the total number of jump moves for a player
	 * @param colour
	 * @return jumpMoves
	 */
	public static int findJumpMoves(char colour){
		jumpMoves=0;
		for(int i=0;i<board.size;i++)
		{
			for(int j=0;j<board.size;j++)
			{
				if(board.bd[i][j]==colour)
				{
					//System.out.println("calling White numJumpMoves for"+ i+j);
					jumpedcells.clear();
					numJumpMoves(board.bd, i, j);
				}
				
			}
		}
		return jumpMoves;
	}
	
	
	
	/*arraylist used to keep track of the resulting cells in the previous jumps in a sequence, in 
	 * order to prevent ping-pong between two new jump cells in a sequence
	 */
	static ArrayList<pieces> jumpedcells = new ArrayList<pieces>();
	
	
	/**
	 * Method to determine if a given cell in the board has already been explored
	 * takes input of object type pieces, which has the x and y coordinate of the cell
	 * return a boolean true false.
	 * @param p
	 * @param jumpedcells
	 * @return boolean
	 */
	public static boolean notInJumpedcells(pieces p, ArrayList<pieces> jumpedcells){
		for(int k=0;k<jumpedcells.size();k++){
			if(p.getx()==jumpedcells.get(k).getx()){
				if(p.gety()==jumpedcells.get(k).gety()){
					//System.out.println("false");
					return false;
				}
			}
		}
		//System.out.println("true");
		return true;
	}
	
	
	/** Method to recursively determine the number of jump moves possible for a particular piece
	 * to be able to make a jump move with a piece needs to satisfy 2 conditions:
	 * Condition 1: have a piece in one of the adjacent 8 cells
	 * Condition 2: have an empty cell in line with the cell in condition 2
	 * This function takes the board configuration array and the coordinate of a piece as input
	 * checks if the resulting jump coordinate is inside the board
	 * then checks condition 2 first as it has only one check
	 * checks condition 1 then
	 * increments the number of jump moves possible.
	 * and puts the resulting coordinate of the jump move through this function recursively again
	 * returns nothing.
	 * @param boardConfig
	 * @param xCoordinate
	 * @param yCoordinate
	 */
	public static void numJumpMoves(char[][] boardConfig, int xCoordinate, int yCoordinate){
		/*System.out.println("numJumpMoves responding to call for "+ xCoordinate+ yCoordinate);
		*for(int k=0;k<jumpedcells.size();k++){
		*System.out.println("Printing jumpedcells"+jumpedcells.get(k).getx()+ jumpedcells.get(k).gety());
		*}
		*/
		
		pieces p= new pieces(0,0);
		
		//check top left diagonal------------------------------------------------------------------------------------
		p.setx(xCoordinate-2);
		p.sety(yCoordinate-2);
		if(isOnBoard(xCoordinate-2, yCoordinate-2) && boardConfig[xCoordinate-2][yCoordinate-2]=='-'
				&& notInJumpedcells(p, jumpedcells)){
			//System.out.println("top left first if, X="+(xCoordinate-2)+"Y="+(yCoordinate-2));
			if(boardConfig[xCoordinate-1][yCoordinate-1]=='W' || boardConfig[xCoordinate-1][yCoordinate-1]=='B'){
				//System.out.println("top left second if, X="+(xCoordinate-1)+"Y="+(yCoordinate-1));
				jumpedcells.add(p);
				//System.out.println("found a jumped move at left diagonal.... result is"+p.getx()+p.gety());
				jumpMoves+=1;
				numJumpMoves(boardConfig, xCoordinate-2, yCoordinate-2);
			}
		}
		
		//check straight top------------------------------------------------------------------------------------------
		p.setx(xCoordinate-2);
		p.sety(yCoordinate);
		if(isOnBoard(xCoordinate-2, yCoordinate) && boardConfig[xCoordinate-2][yCoordinate]=='-'
				&& notInJumpedcells(p, jumpedcells)){
			//System.out.println("straight top first if, X="+(xCoordinate-2)+"Y="+(yCoordinate));
			if(boardConfig[xCoordinate-1][yCoordinate]=='W' || boardConfig[xCoordinate-1][yCoordinate]=='B')
			{
				//System.out.println("straight top second if, X="+(xCoordinate-1)+"Y="+(yCoordinate));
				jumpedcells.add(p);
				//System.out.println("found a jumped move straight top.... result is"+p.getx()+p.gety());
				jumpMoves+=1;
				numJumpMoves(boardConfig, xCoordinate-2, yCoordinate);
			}
			
		}
		
		//check top right diagonal--------------------------------------------------------------
		p.setx(xCoordinate-2);
		p.sety(yCoordinate+2);
		if(isOnBoard(xCoordinate-2, yCoordinate+2) && boardConfig[xCoordinate-2][yCoordinate+2]=='-'
				&& notInJumpedcells(p, jumpedcells)){
			//System.out.println("top right first if, X="+(xCoordinate-2)+"Y="+(yCoordinate+2));
			if(boardConfig[xCoordinate-1][yCoordinate+1]=='W' || boardConfig[xCoordinate-1][yCoordinate+1]=='B'){
				//System.out.println("top right second if, X="+(xCoordinate-1)+"Y="+(yCoordinate+1));
				jumpedcells.add(p);
				//System.out.println("found a jumped move at top right.... result is"+p.getx()+p.gety());
				jumpMoves+=1;
				numJumpMoves(boardConfig, xCoordinate-2, yCoordinate+2);
			}
		}
		
		//check straight left---------------------------------------------------------------------
		//System.out.println("starting cell is "+xCoordinate+yCoordinate);
		p.setx(xCoordinate);
		p.sety(yCoordinate-2);
		if(isOnBoard(xCoordinate, yCoordinate-2) && boardConfig[xCoordinate][yCoordinate-2]=='-'
				&& notInJumpedcells(p, jumpedcells)){
			//System.out.println("straight left first if, X="+(xCoordinate)+"Y="+(yCoordinate-2));
			if(boardConfig[xCoordinate][yCoordinate-1]=='W' || boardConfig[xCoordinate][yCoordinate-1]=='B'){
				//System.out.println("straight left second if, X="+(xCoordinate)+"Y="+(yCoordinate-1));
				jumpedcells.add(p);
				//System.out.println("found a jumped move at straight left.... result is"+p.getx()+p.gety());
				jumpMoves+=1;
				numJumpMoves(boardConfig, xCoordinate, yCoordinate-2);
			}
		}
		
		//check straight right-------------------------------------------------------------------
		p.setx(xCoordinate);
		p.sety(yCoordinate+2);
		if(isOnBoard(xCoordinate, yCoordinate+2) && boardConfig[xCoordinate][yCoordinate+2]=='-'
				&& notInJumpedcells(p, jumpedcells)){
			//System.out.println("straight right first if, X="+(xCoordinate)+"Y="+(yCoordinate+2));
			if(boardConfig[xCoordinate][yCoordinate+1]=='W' || boardConfig[xCoordinate][yCoordinate+1]=='B'){
				//System.out.println("straight right second if, X="+(xCoordinate)+"Y="+(yCoordinate+1));
				jumpedcells.add(p);
				//System.out.println("found a jumped move straight right.... result is"+p.getx()+p.gety());
				jumpMoves+=1;
				numJumpMoves(boardConfig, xCoordinate, yCoordinate+2);
			}
		}
		
		//check bottom left diagonal----------------------------------------------------
		p.setx(xCoordinate+2);
		p.sety(yCoordinate-2);
		if(isOnBoard(xCoordinate+2, yCoordinate-2) && boardConfig[xCoordinate+2][yCoordinate-2]=='-'
				&& notInJumpedcells(p, jumpedcells)){
			//System.out.println("bottom left first if, X="+(xCoordinate+2)+"Y="+(yCoordinate-2));
			if(boardConfig[xCoordinate+1][yCoordinate-1]=='W' || boardConfig[xCoordinate+1][yCoordinate-1]=='B'){
				//System.out.println("bottom left second if, X="+(xCoordinate+1)+"Y="+(yCoordinate-1));
				jumpedcells.add(p);
				//System.out.println("found a jumped move bottom left.... result is"+p.getx()+p.gety());
				jumpMoves+=1;
				numJumpMoves(boardConfig, xCoordinate+2, yCoordinate-2);
			}
		}
		
		//check straight bottom------------------------------------------------------------------------
		p.setx(xCoordinate+2);
		p.sety(yCoordinate);
		if(isOnBoard(xCoordinate+2, yCoordinate) && boardConfig[xCoordinate+2][yCoordinate]=='-'
				&& notInJumpedcells(p, jumpedcells)){
			//System.out.println("straight bottom first if for"+x+y+", Piece is "+ boardConfig[x][y]);
			
			if(boardConfig[xCoordinate+1][yCoordinate]=='W' || boardConfig[xCoordinate+1][yCoordinate]=='B'){
				//System.out.println("straight bottom second if, X="+(xCoordinate+1)+"Y="+(yCoordinate));
				jumpedcells.add(p);
				//System.out.println("found a jumped move straight bottom.... result is"+p.getx()+p.gety());
				jumpMoves+=1;
				numJumpMoves(boardConfig, xCoordinate+2, yCoordinate);
			}
		}
		
		//check bottom right diagonal-----------------------------------------------------------------
		p.setx(xCoordinate+2);
		p.sety(yCoordinate+2);
		if(isOnBoard(xCoordinate+2, yCoordinate+2) && boardConfig[xCoordinate+2][yCoordinate+2]=='-'
				&& notInJumpedcells(p, jumpedcells)){
			//System.out.println("bottom right first if, X="+(xCoordinate+2)+"Y="+(yCoordinate+2));
			if(boardConfig[xCoordinate+1][yCoordinate+1]=='W' || boardConfig[xCoordinate+1][yCoordinate+1]=='B'){
				//System.out.println("bottom right second if, X="+(xCoordinate+1)+"Y="+(yCoordinate+1));
				jumpedcells.add(p);
				//System.out.println("found a jumped move bottom right.... result is"+p.getx()+p.gety());
				jumpMoves+=1;
				numJumpMoves(boardConfig, xCoordinate+2, yCoordinate+2);
			}
		}
		
		int arraySize= jumpedcells.size()-1;
		if (arraySize>=0) {jumpedcells.remove(arraySize);}
		//System.out.println("Exiting function for "+xCoordinate+yCoordinate+ jumpedcells);
	
		
	}
}

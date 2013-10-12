import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

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
	
	
	/**
	 * Method for setting pieces on the board from the specified input file
	 * @param inputFile
	 * @throws IOException
	 */
	public void setboard(String inputFile) throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader(inputFile));
		String text= in.readLine();
		size= Integer.parseInt(text);	
		W=0;B=0;X=0;Blank=0;
		bd = new int[size][size];
		int i=0;
		while(i<size){
			text = in.readLine();
			StringTokenizer tokenizer = new StringTokenizer(text," ");
			int j=0;
			while(tokenizer.hasMoreTokens()){
				char c= tokenizer.nextToken().charAt(0);
				
				switch (c)
				{
				case 'w':
				case 'W':	W++;
							bd[i][j]=Piece.WHITE;
							j++;
							continue;
							
				case 'b':
				case 'B':	B++;
							bd[i][j]=Piece.BLACK;
							j++;
							continue;
							
				case '-':	Blank++;
							bd[i][j]=Piece.EMPTY;
							j++;
							continue;
					
				case 'x':
				case 'X':	bd[i][j]=Piece.DEAD;
							j++;
							X++;
							continue;
							
				
				case 'q':
				case 'Q':	System.exit(0);
				
				default:	System.out.println("Invalid entry try again");
							System.exit(1);
			}
			}
			i++;
		}
		in.close();
		display();
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
	int numpieces(int type)
	{
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
	
	
	public boolean placeMove(Move move)
	{
		
		int r=move.RowPositions[0];
		int c=move.ColPositions[0];
		//Check for out of bounds condition
		if(r<0 || r>size-1)
			if(c<0||c>size-1)
			{
				return false;
			}
		
		//Cell is not empty
		if(bd[r][c]!=Piece.EMPTY)
		{
			return false;
		}
		
		//It's normal
		else
		{
			bd[r][c]=move.P;
			return true;
		}
	}
	
	/*
	 * Check for validity of a move
	 * 1. Check if it's a place move
	 * 2. Check if it's a jump move
	 * 
	 * @param: move
	 */
	public boolean validMove(Move move)
	{
		//For place move
		if(move.IsPlaceMove)
		{
			if(placeMove(move))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		//For Jump Moves
		else
		{
			if(pushjumpMove(move))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	
	public boolean pushjumpMove(Move move)
	{
		//Valid jump move or not
		if(move.RowPositions.length!=move.ColPositions.length)
		{
			return false;
		}
		//enough rows or columns
		else if(move.RowPositions.length<2)
		{
			return false;
		}
		
		//is jump possible
		for (int i=0; i<move.ColPositions.length-1; i++) 
		{
			
			// Check if jump move is into valid position
			if (!jumpMovePossible(move.RowPositions[i],move.ColPositions[i],move.RowPositions[i+1], move.ColPositions[i+1])) 
			{
				return false;	
			}
			
			// Insert jump move
			bd[move.RowPositions[i]][move.ColPositions[i]] = move.P;
			bd[move.RowPositions[i+1]][move.ColPositions[i+1]] = move.P;
			
			// piece that was jumped over is the opponent's piece
			if (bd[(move.RowPositions[i]+move.RowPositions[i+1])/2][(move.ColPositions[i]+move.ColPositions[i+1])/2] != move.P) 
			{
				bd[(move.RowPositions[i]+move.RowPositions[i+1])/2][(move.ColPositions[i]+move.ColPositions[i+1])/2] = Piece.DEAD;	
			}
		}
		
		return true;
		}


	/*
	 * Checks if jump move is possible or not
	 * @param: Intitial row and column and final row and column		
	 */
	public boolean jumpMovePossible(int r1, int c1, int r2, int c2)
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
	
	/*
	 * What all moves are possible from a certain spot
	 */

}



	


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
	public static int size,W,B,X, Blank;
	//initialize board as a 2D array of chars
	public static int[][]  bd;
	
	/*
	 * Constructor to set up board
	 */
	public board(int n)
	{
		/*this.size=size;
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
			{
				bd[i][j]='-';
			}*/
		
		bd = new int[n][n];
		size = n;
		int i,j;
		i=j=0;
		for(i=0;i<n;i++){
			for(j=0;j<n;j++){
				bd[i][j]= Piece.EMPTY;
			}
		}
	}
	
	
	/**
	 * Method for setting pieces on the board from the specified input file
	 * @param inputFile
	 * @throws IOException
	 */
	public static void setboard(String inputFile) throws IOException
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
	static void display()
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
}

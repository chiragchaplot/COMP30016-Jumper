/*
 * This is the main control center for the whole project
*/

import java.lang.*;
import java.io.*;


public class gameengine 
{
	public static void main(String[] args) throws Exception
	{
		board.setboard("input.txt");
		int placemoves = rules.numPlaceMoves(board.bd);
		int WhitejumpMoves = rules.findJumpMoves('W');
		int BlackjumpMoves= rules.findJumpMoves('B');
		System.out.println("W " + placemoves + " " + WhitejumpMoves);
		System.out.println("B " + placemoves + " " + BlackjumpMoves);
		
	}
}

/*
 * This is the main control center for the whole project
*/

import java.lang.*;
import java.io.*;


public class gameengine 
{
	public static void main(String[] args) throws Exception
	{
		board newBoard= new board();
		board.setboard("input.txt");
		int placemoves = rules.numPlaceMoves(newBoard.bd);
		int WhitejumpMoves = rules.findJumpMoves(Piece.WHITE);
		int BlackjumpMoves= rules.findJumpMoves(Piece.BLACK);
		System.out.println("W " + placemoves + " " + WhitejumpMoves);
		System.out.println("B " + placemoves + " " + BlackjumpMoves);
		
	}
}

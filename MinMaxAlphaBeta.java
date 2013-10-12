import java.util.ArrayList;


public class MinMaxAlphaBeta implements Piece {
	
	private final int maxSearchDepth= 7;
	private static int player;
	private static int opponent;
	
	public static Move bestMove(board board, int playerType, int opponentType) {
		
		player = playerType;
		opponent = opponentType;
		int currentDepth = 0;
		Move result;
		int defaultValue = -2^31;
		
		ArrayList<Move> moveOptions= successor.moveOptions(player, board);
		for (Move move : boardState.possibleMoves(player)) {
			
			Board tempBoard = new Board(boardState.size());
			
			for (int row=0; row<tempBoard.size(); row++) {
				for (int col=0; col<tempBoard.size(); col++) {
					tempBoard.insert(row, col, boardState.get(row, col));
				}
			}
			
			tempBoard.insertMove(move);
			int value = minValue(tempBoard,
					Integer.MIN_VALUE, Integer.MAX_VALUE, depth+1);
			
			if (value > resultValue) {
				result = move;
				resultValue = value;
			}
		}

		return result;
	}
	
	private static int minValue(Board boardState,
			int alpha, int beta, int depth) {
			
		if (isTerminalState(boardState, depth)) {
			return getUtility(boardState);
		}
		
		int value = Integer.MAX_VALUE;
		
		for (Move move : boardState.possibleMoves(OPPONENT)) {
			Board tempBoard = new Board(boardState.size());
			
			for (int row=0; row<tempBoard.size(); row++) {
				for (int col=0; col<tempBoard.size(); col++) {
					tempBoard.insert(row, col, boardState.get(row, col));
				}
			}
			
			tempBoard.insertMove(move);
			value = Math.min(value, maxValue(tempBoard,
					alpha, beta, depth+1));
			
			if(value <= alpha) {
				return value;
			}
			
			beta = Math.min(value, beta);
					
		}
		return value;
	}
	
	private static int maxValue(Board boardState,
			int alpha, int beta, int depth) {
			
		if (isTerminalState(boardState, depth)) {
			return getUtility(boardState);
		}
		
		int value = Integer.MIN_VALUE;
		
		for (Move move : boardState.possibleMoves(PLAYER)) {
			Board tempBoard = new Board(boardState.size());
			
			for (int row=0; row<tempBoard.size(); row++) {
				for (int col=0; col<tempBoard.size(); col++) {
					tempBoard.insert(row, col, boardState.get(row, col));
				}
			}
			
			tempBoard.insertMove(move);
			value = Math.max(value, minValue(tempBoard,
					alpha, beta, depth+1));
			
			if(value >= beta) {
				return value;
			}
			
			alpha = Math.max(value, alpha);
					
		}
		return value;
	}
	
	// Calculates if board state is a terminal state (max depth or no
	// empty positions)
	private static boolean isTerminalState(Board board, int depth) {
		if (board.numEmpty() == 0 || depth == MAXDEPTH) {
			return true;
		}
		
		return false;
	}
	
	// Calculates the utility value of the board state
	private static int getUtility(Board board) {
		if (board.numEmpty() == 0) {
			// Returns max value if win board state
			if (board.numPlayerPieces(PLAYER) > board.numPlayerPieces(OPPONENT)) {
				return Integer.MAX_VALUE-1;
			// Returns min value if lose board state
			} else if (board.numPlayerPieces(OPPONENT)>board.numPlayerPieces(PLAYER)) {
				return Integer.MIN_VALUE+1;
			// Returns 0 if draw
			} else
				return 0;
		}
		// Otherwise returns the amount of player pieces minus the opponent's
		return board.numPlayerPieces(PLAYER)-board.numPlayerPieces(OPPONENT);
	}
	
	// Sorts moves according to move value using quicksort algorithm
	// NOT USED IN THE FINAL CODE
	private static ArrayList<Move> sortMoves(ArrayList<Move> moves, Board boardState) {
		int[] moveValues = new int[moves.size()];
		
		for (int i=0; i < moves.size(); i++) {
			Board tempBoard = new Board(boardState.size());
			
			for (int row=0; row<tempBoard.size(); row++) {
				for (int col=0; col<tempBoard.size(); col++) {
					tempBoard.insertMove(moves.get(i));
					moveValues[i] = getUtility(tempBoard);
				}
			}
		}
		
		int[] sortedValues = QuickSort.sort(moveValues);
		ArrayList<Move> sortedMoves = new ArrayList<Move>();
		
		int i=0;
		
		while (i < moves.size()) {
			if (sortedValues[i] == moveValues[i]) {
				sortedMoves.add(moves.get(i));
				sortedValues[i] = -1000;
				i++;
			}
		}
		
		return sortedMoves;
	}
	
}
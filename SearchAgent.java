import java.util.ArrayList;


public class MinMaxAlphaBeta implements Piece {
	
	private static final int MAX_INT = 2^31;
	private static final int MIN_INT = -2^31;
	private final static int maxSearchDepth= 7;
	private static int player;
	private static int opponent;
	
	public static Move bestMove(board board, int playerType, int opponentType) {
		
		player = playerType;
		opponent = opponentType;
		int currentDepth = 0;
		Move bestMove = null;
		int defaultValue = MIN_INT;
		
		ArrayList<Move> moveOptions= successor.moveOptions(player, board);
		for (int i=0; i<moveOptions.size();i++) {
			
			board tempBoard = new board(board.Size());
			
			for (int row=0; row<tempBoard.Size(); row++) {
				for (int col=0; col<tempBoard.Size(); col++) {
					tempBoard.insert(row, col, board.getBd()[row][ col]);
				}
			}
			
			moveHandler.validMove(moveOptions.get(i), tempBoard);
			int nodeValue = minValue(tempBoard,
					MIN_INT, MAX_INT, currentDepth+1);
			
			if (nodeValue > defaultValue) {
				bestMove = moveOptions.get(i);
				defaultValue = nodeValue;
			}
		}

		return bestMove;
	}
	
	private static int minValue(board board, int alpha, int beta, int currentDepth) {
			
		if (isTerminalState(board, currentDepth) || currentDepth==maxSearchDepth) {
			return getUtility(board);
		}
		
		int nodeValue = MAX_INT;
		
		
		ArrayList<Move> moveOptions= successor.moveOptions(opponent, board);
		for (int i=0; i<moveOptions.size();i++) {
			board tempBoard = new board(board.Size());
			
			for (int row=0; row<tempBoard.Size(); row++) {
				for (int col=0; col<tempBoard.Size(); col++) {
					tempBoard.insert(row, col, board.getBd()[row][ col]);
				}
			}
			
			moveHandler.validMove(moveOptions.get(i), tempBoard);
			nodeValue = Math.min(nodeValue, maxValue(tempBoard, alpha, beta, currentDepth+1));
			
			if(nodeValue <= alpha) {
				return nodeValue;
			}
			
			beta = Math.min(nodeValue, beta);
					
		}
		return nodeValue;
	}
	
	private static int maxValue(board board, int alpha, int beta, int currentDepth) {
			
		if (isTerminalState(board, currentDepth) || currentDepth== maxSearchDepth) {
			return getUtility(board);
		}
		
		int nodeValue = MIN_INT;
		
		ArrayList<Move> moveOptions= successor.moveOptions(player, board);
		for (int i=0; i<moveOptions.size();i++) {
			board tempBoard = new board(board.Size());
			
			for (int row=0; row<tempBoard.Size(); row++) {
				for (int col=0; col<tempBoard.Size(); col++) {
					tempBoard.insert(row, col, board.getBd()[row][col]);
				}
			}
			
			moveHandler.validMove(moveOptions.get(i), tempBoard);
			nodeValue = Math.max(nodeValue, minValue(tempBoard,alpha, beta, currentDepth+1));
			
			if(nodeValue >= beta) {
				return nodeValue;
			}
			
			alpha = Math.max(nodeValue, alpha);
					
		}
		return nodeValue;
	}
	
	// Calculates if board state is a terminal state (max depth or no
	// empty positions)
	private static boolean isTerminalState(board board, int currentDepth) {
		if (board.numPieces(EMPTY) == 0) {
			return true;
		}
		
		return false;
	}
	
	
	// Calculates the utility value of the board state
	private static int getUtility(board board) {
		if (board.numPieces(EMPTY) == 0) {
			// Returns max value if win board state
			if (board.numPieces(player) > board.numPieces(opponent)) {
				return MAX_INT;
			// Returns min value if lose board state
			} else if (board.numPieces(opponent)>board.numPieces(player)) {
				return MIN_INT;
			// Returns 0 if draw
			} else
				return 0;
		}
		// Otherwise returns the amount of player pieces minus the opponent's
		return board.numPieces(player)-board.numPieces(opponent);
	}
	
}
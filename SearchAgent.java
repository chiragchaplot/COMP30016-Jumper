import java.util.ArrayList;


public class SearchAgent implements Piece {
	
	private static final int INFINITY = 2147483647;
	private static final int NEGETIVE_INFINITY = -2147483647;
	private final static int maxSearchDepth= 7;
	private static int player;
	private static int opponent;
	private static int boardSize;
	
	public static Move bestMove(board board, int playerType, int opponentType) {
		
		player = playerType;
		opponent = opponentType;
		int currentDepth = 0;
		Move bestMove = null;
		int defaultValue = NEGETIVE_INFINITY;
		
		ArrayList<Move> moveOptions= board.moveOptions(player);
		for (int i=0; i<moveOptions.size();i++) {
			
			boardSize= board.getSize();
			board tempBoard = new board(boardSize);
			
			for (int row=0; row<boardSize; row++) {
				for (int col=0; col<boardSize; col++) {
					tempBoard.insert(row, col, board.getBd()[row][ col]);
				}
			}
			tempBoard.validMove(moveOptions.get(i));
			int nodeValue = minValue(tempBoard, NEGETIVE_INFINITY, INFINITY, currentDepth+1);
			if (nodeValue > defaultValue) {
				bestMove = moveOptions.get(i);
				defaultValue = nodeValue;
			}
		}

		return bestMove;
	}
	
	private static int minValue(board board, int alpha, int beta, int currentDepth) {
			
		if (isTerminalState(board) || currentDepth==maxSearchDepth) return utilityValue(board);
		
		int nodeValue = INFINITY;
		ArrayList<Move> moveOptions= board.moveOptions(opponent);
		for (int i=0; i<moveOptions.size();i++) {
			board tempBoard = new board(boardSize);
			for (int row=0; row<boardSize; row++) {
				for (int col=0; col<boardSize; col++) {
					tempBoard.insert(row, col, board.getBd()[row][ col]);
				}
			}
			tempBoard.validMove(moveOptions.get(i));
			nodeValue = Math.min(nodeValue, maxValue(tempBoard, alpha, beta, currentDepth+1));
			if(nodeValue<= alpha) return nodeValue;
			beta = Math.min(nodeValue, beta);		
		}
		return nodeValue;
	}
	
	private static int maxValue(board board, int alpha, int beta, int currentDepth) {
			
		if (isTerminalState(board) || currentDepth== maxSearchDepth) return utilityValue(board);
		
		int nodeValue = NEGETIVE_INFINITY;
		ArrayList<Move> moveOptions= board.moveOptions(player);
		for (int i=0; i<moveOptions.size();i++) {
			board tempBoard = new board(board.getSize());
			for (int row=0; row<tempBoard.getSize(); row++) {
				for (int col=0; col<tempBoard.getSize(); col++) {
					tempBoard.insert(row, col, board.getBd()[row][col]);
				}
			}
			tempBoard.validMove(moveOptions.get(i));
			nodeValue = Math.max(nodeValue, minValue(tempBoard,alpha, beta, currentDepth+1));
			if(nodeValue>= beta) return nodeValue;
			alpha = Math.max(nodeValue, alpha);		
		}
		return nodeValue;
	}
	
	// Calculates the utility value of the board state
	private static int utilityValue(board board) {
		if (board.numPieces(EMPTY) == 0) {
			// Returns max value if win board state
			if (board.numPieces(player) > board.numPieces(opponent)) {
				return (boardSize*boardSize);
			// Returns min value if lose board state
			} else if (board.numPieces(opponent)>board.numPieces(player)) {
				return (-boardSize*boardSize);
			// Returns 0 if draw
			} else
				return 0;
		}
		// Otherwise returns the amount of player pieces minus the opponent's
		return board.numPieces(player)-board.numPieces(opponent);
	}
	
	// Calculates if board state is a terminal state (max depth or no
	// empty positions)
	private static boolean isTerminalState(board board) {
		if (board.numPieces(EMPTY) == 0) return true;
		else return false;
	}
	
	

	
}
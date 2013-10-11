/*
 * MINMAX WITH ALPHA-BETA PRUNING FOR SEARCH CONTROL ALGORITHM
 * @author: cchaplot, nalam
 * 
 */

/*class node
{
	private int value; 
	private char boardState[][];
		private Move m;
		
	public node(int v, Move ms, char [][] currBoard)
		{
			value=v;
			m=ms;
			boardState= new char [board.size][board.size];
			//Making a copy of the board configuration
			for(int i=0;i<board.size;i++)
				for (int j=0;j<board.size;j++)
				{
					boardState[i][j]=currBoard[i][j];
				}
		}
}*/

public class minmax 
{
	board b;
	int maxsearchdepth=2;
	int depth=0;
	int player, opponent, nummoveplayed;
	
	Move Decision(node Node){
		Node.value = MaxValue(Node);
		//return the Node.Child.Move in Node.Child with Node.Child.Value=Node.Value
		
	}
	
	
	int utilityValue(node Node, int player){
		int difference= Node.boardState.W-Node.boardState.B;
		if (player==Piece.WHITE)
		{
			Node.setValue(difference);
		}
		else if(player==Piece.BLACK)
		{
			Node.setValue(-difference);
		}
		return Node.value;
	}
	
	
	int MinValue(node Node){
		//for node, test terminal
		//if terminal or max depth is hit, return the utility value
		if (isTerminal(Node) || depth== maxsearchdepth){
			return utilityValue(Node, player);
		}
		
		//else, find all successors
		successor.expand(Node,opponent);
		int i=0;
		int childSpace= Node.child.size();
		int v = 2^-31;
		depth ++;
		//for each successors,do
		for (i=0; i <childSpace; i++){
			//maximum (Min-Value(successor))
			v= Math.max(v, MinValue(Node.child.get(i)));
		}
		return v;
	}
	
	int MaxValue(node Node){
		//for node, test terminal
		//if terminal of max depth is hit, return the utility value for the node and return node
		if (isTerminal(Node) || depth==maxsearchdepth){
			return utilityValue(Node, player);
		}
		//else, find all successors
		successor.expand(Node, player);
		int i=0;
		int childSpace= Node.child.size();
		int v = 2^-31;
		depth++;
		//for each successors,do
		for (i=0; i <childSpace; i++){
			//maximum (Min-Value(successor))
			v= Math.max(v, MinValue(Node.child.get(i)));
		}
		return v;
	}
	
	node maximum(node Node){
		//go to the successors of the node
		//choose the node with maximum value and return the node
		return Node;
	}
	
	node minimum(node Node){
		//go to the successors of the node
		//choose the node with minimum value and return the node
		return Node;
	}
	
	boolean isTerminal(node Node){
		if (Node.boardState.Blank==0 || (Node.boardState.Blank+Node.boardState.W)< Node.boardState.B || (Node.boardState.Blank+Node.boardState.B)< Node.boardState.W){
			return true;
		}
		else {return false;}
		
	}
	
	
}

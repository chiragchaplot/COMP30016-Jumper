/*
 * This is the node class that represents a board configuration.
 * @author: cchaplot
 */
import java.util.ArrayList;
public class node 
{
	public int value; 
	public board boardState;
	private node parent;
	public ArrayList<node> child;
	public Move move;
		
	public node(Move ms, board boardObject)
		{
			move=ms;
			boardState= new board();
			boardState.Blank= boardObject.Blank;
			boardState.W= boardObject.W;
			boardState.B= boardObject.B;
			
			//Making a copy of the board configuration
			for(int i=0;i<board.size;i++)
				for (int j=0;j<board.size;j++)
				{
					boardState.bd[i][j]=boardObject.bd[i][j];
				}
		}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public node getParent() {
		return parent;
	}

	public void setParent(node parent) {
		this.parent = parent;
	}

	public ArrayList<node> getChild() {
		return child;
	}

	public void setChild(ArrayList<node> child) {
		this.child = child;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move m) {
		this.move = move;
	}
}

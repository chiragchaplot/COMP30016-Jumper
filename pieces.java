/**
 * This is to store the x & y coordinate of the piece and dead cells in PART B
 */
public class pieces 
{
	int x;
	int y;
	boolean dead;
	
	public pieces(int i, int j) {
		x=i;
		y=j;
	}

	void setx(int n)
	{
		x=n;
	}
	
	void sety(int n)
	{
		y=n;
	}
	
	int getx()
	{
		return x;
	}
	
	int gety()
	{
		return y;
	}
	
}

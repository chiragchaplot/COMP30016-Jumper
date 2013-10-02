/*
 * This is for the HUMAN PLAYER for partA and partB
 */
public class player 
{
	private
	char name[];
	levels l[];
	pieces p[];
	matches m;
	
	public
	
	//Calculate player efficiency
	void successrate(matches m)
	{
	}
	
	//Add player to database
	void addplayer()
	{
		
	}
	
	void setname(char c[])
	{
		int i=0;
		while(c[i]!='\0')
		{
			name[i]=c[i];
			i++;
		}
	}
	
	char []getname()
	{
		return name;
	}
	
}

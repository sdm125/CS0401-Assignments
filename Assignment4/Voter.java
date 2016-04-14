import java.util.ArrayList;

public class Voter
{
	private String[] strParts;
	private int id;
	private String name;
	private boolean hasVoted;
	private static int line = 0;

	public Voter(ArrayList<String> voterFileContents)
	{
		strParts = voterFileContents.get(line++).split(":");
		id = Integer.parseInt(strParts[0]);
		name = strParts[1];
		hasVoted = Boolean.parseBoolean(strParts[2]);
	}

	// Returns voter id.
	public int getId(){
		return this.id;
	}

	// Returns voter name.
	public String getName(){
		return this.name;
	}

	// Returns whether user has voted or not.
	public boolean getHasVoted(){
		return hasVoted;
	}

	// Sets to true once user has voted.
	public void setVoted(boolean voted)
	{
		this.hasVoted = voted;
	}

}
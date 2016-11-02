package project6;

public class FeedBackStatus {
	private int BlackCount = 0;
	private int WhiteCount = 0;
	
	void addBlack()
	{
		this.BlackCount++;
	}
	
	void removeWhite(int value)
	{
		this.WhiteCount -= value;
	}
	
	void addWhite()
	{
		this.WhiteCount++;
	}
	
	int getBlackCount()
	{
		return BlackCount;
	}
	
	int getWhiteCount()
	{
		return WhiteCount;
	}
}

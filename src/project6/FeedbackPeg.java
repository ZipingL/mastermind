package project6;

public abstract class FeedbackPeg extends Peg {

	public static FeedBackStatus feedBackPegsToFeedBackStatus(FeedbackPeg[] parsedFeedBackPegs) {
		
		FeedBackStatus hint = new FeedBackStatus();
		
		for(int i = 0; i < parsedFeedBackPegs.length; i++)
		{
			if(parsedFeedBackPegs[i] != null)
			{
			if(parsedFeedBackPegs[i].color().equals(Peg.PegColor.BLACK))
				hint.addBlack();
			else
				hint.addWhite();
			}
		}
		
		return hint;
	}
//	@Override
//	PegColor color()
//	{
//		return PegColor.NONE;
//	}
	
	
}


package project6;

import java.util.ArrayList;

import javafx.scene.image.Image;

public abstract class Peg {
	
	abstract PegColor color();
	final public static String[] toStringPegColor = {
			"B",
			"G",
			"O",
			"P",
			"R",
			"Y",
			"FW",
			"FB",
			"NA"		
	};
	
	public static enum PegColor 
	{
		BLUE,
		GREEN,
		ORANGE,
		PURPLE,
		RED,
		YELLOW,
		WHITE,
		BLACK,
		NONE
	}
	
	
	public static Peg[] StringToPeg(String[] guesses)
	{
		Peg[] pegGuesses = new Peg[guesses.length];
		int i = 0;
		for(String guess : guesses)
		{
			if(validStringColor(guess))
			{
				pegGuesses[i] = createRespectivePeg(guess, 0);
				i++;
			}
			else
			{
				return null;
			}
		}
		
		return pegGuesses;
	}
	
	public static Peg createRespectivePeg(String guess, int guessInNumberRepresentation)
	{
		int color = 0;
		if(guess != null)
		for(int i = 0; i < toStringPegColor.length; i++)
		{
			if(toStringPegColor[i].equals(guess))
			{
				color = i;
				break;
			}
		}
		
		if(guess == null)
			color = guessInNumberRepresentation;
		switch(color)
		{
		case 0:
			return new BluePeg();
		case 1:
			return new GreenPeg();
		case 2:
			return new OrangePeg();
		case 3:
			return new PurplePeg();
		case 4:
			return new RedPeg();
		case 5:
			return new YellowPeg();
		case 6:
			return new WhitePeg();
		case 7:
			return new BlackPeg();
		}
		
		return null;
	}
	
	public static boolean validStringColor(String guess)
	{
		boolean status = false;
		
		if(guess != null)
		for(String color : toStringPegColor)
		{
			if(guess.equals(color))
			{
				status = true;
				break;
			}
		}
		
		return status;
	}
	

	public boolean equals(Object other)
	{
		try
		{
			if(this.color().equals( ( (Peg) other).color()))
				return true;
		} 
		
		catch(ClassCastException e)
		{
			return false;
		}
	
		return false;
	}
	
	
	
	static FeedBackStatus checkPegGuessValidity() {
		
		FeedBackStatus hint = new FeedBackStatus();
		Peg[] userGuess = null;
		if(Data.UserGuesses.size() != 0)
			userGuess = Data.UserGuesses.get(Data.UserGuesses.size() - 1);
		
		if(userGuess != null)
		{
			for(int i = 0; i < Data.Parameter.PEGCOUNT; i++)
			{
				if(Data.Solution[i].equals(userGuess[i]))
				{
					hint.addBlack();
				}
			}
			
			ArrayList<Integer> confirmedIndices = new ArrayList<>();
			for(Peg solution : Data.Solution)
			{
				boolean exists = false;
				ATLEASTGETACOLORRIGHT:
				for(int i = 0; i < Data.Parameter.PEGCOUNT; i++)
				{
					// Check if current index was already checked by a previous one
					// only if the color matches
					for(Integer index : confirmedIndices)
					{
						if(solution.equals(userGuess[i]) && index.equals(i))
						{
							continue ATLEASTGETACOLORRIGHT;
						}
					}
					if(solution.equals(userGuess[i]))
					{
						exists = true;
						confirmedIndices.add(i);
						break ATLEASTGETACOLORRIGHT;
					}
				}
				
				if(exists)
				{
					hint.addWhite();
				}
			}
			
			hint.removeWhite(hint.getBlackCount());
		}
		
		ArrayList<FeedbackPeg> feedbackPegs = new ArrayList<FeedbackPeg>();
		for(int i = 0; i < hint.getBlackCount(); i++)
		{
			feedbackPegs.add(new BlackPeg());
		}
		
		for(int i = 0; i < hint.getWhiteCount(); i++)
		{
			feedbackPegs.add(new WhitePeg());
		}
		
		Data.updateHint(feedbackPegs);
		
		return hint;
	}

	public static FeedbackPeg[] pegsToFeedbackPegs(Peg[] feedbackPegs) 
	{
		FeedbackPeg[] returnValue = new FeedbackPeg[feedbackPegs.length];
		int i = 0;
		for(Peg peg : feedbackPegs)
		{
			returnValue[i] = (FeedbackPeg) peg;
			i++;
		}
		return returnValue;
	}

	public static boolean checkFeedbackValidity(FeedbackPeg[] parsedFeedBackPegs) 
	{
		if(parsedFeedBackPegs.length != Data.hint.get(Data.hint.size() -1).length)
			return false;
		
		for(int i = 0; i < parsedFeedBackPegs.length; i++)
		{
			if(!parsedFeedBackPegs[i].equals(Data.hint.get(Data.hint.size() -1)[i]))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public abstract Image returnColorImage();

	public static boolean uniqueGuess(Peg[] aSmartGuess) {
		
		for(Peg[] guesses : Data.UserGuesses)
		{
			int count = 0;
			int i = 0;
			for(Peg guess : guesses)
			{
				if(guess.equals(aSmartGuess[i]))
					count++;
				i++;
			}
			
			if(count == Data.Parameter.PEGCOUNT)
			{
				return false;
			}
		}
		
		return true;
	}
	
	

}



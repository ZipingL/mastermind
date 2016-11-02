package project6;

import java.util.ArrayList;

public class Data {
	static Peg[] Solution = null;
	static ArrayList<Peg[]> UserGuesses = null;
	static int guessCount = 0;
	static ArrayList<FeedbackPeg[]> hint = new ArrayList<>();
	public static ArrayList<FeedbackPeg[]> userHint = new ArrayList<>();
	
	public static class Parameter 
	{
		final static int GUESSLIMIT = 12;
		static int PEGCOUNT = 4;
		static int AI_DIFFICULTY = 0;
		final static int PEG_COLOR_NUMBER = 6;
	}
	
	public static void addGuessCount()
	{
		guessCount++;
	}
	
	public static void addSolution(Peg[] solution)
	{
		Solution = solution;
	}
	
	public static void updateHint(ArrayList<FeedbackPeg> hint)
	{
		FeedbackPeg[] array = new FeedbackPeg[hint.size()];
		Data.hint.add(hint.toArray(array));
	}
	
	public static void addUserGuess(Peg[] userGuess)
	{
		UserGuesses.add(userGuess);
	}
	
	public static void incrementGuessCount()
	{
		Data.guessCount++;
	}
	
	public static boolean ifGuessLimitReached()
	{
		if((Data.Parameter.GUESSLIMIT - Data.guessCount) == 0)
		{
			return true;
		}
		
		return false;
	}

	public static void clearHint() {
		Data.hint = null;	
	}

	public static void removeUserGuess() {
		Data.UserGuesses.remove(UserGuesses.size() - 1);
		
	}

	public static void removeSingleUserGuess() {
		
		Peg[] changedGuess = UserGuesses.get(UserGuesses.size() -1);
		Peg[] removedGuess = new Peg[changedGuess.length -1];
		
		for(int i = 0; i < removedGuess.length; i++)
		{
			removedGuess[i] = changedGuess[i];
		}
		
		UserGuesses.remove(UserGuesses.size() -1);
		
		if(removedGuess.length != 0)
		Data.addUserGuess(removedGuess);
		else
		{
			Gui.newGuessSet = true;
		}
		
	}

	public static void removeUserHint() {
		Data.userHint.remove(Data.userHint.size()-1);
		return;
		
	}

	public static void addUserHint(FeedbackPeg[] clues) {
		userHint.add(clues);
		
	}

	public static void removeSingleUserHint() {
		FeedbackPeg[] changedGuess = userHint.get(userHint.size() -1);
		FeedbackPeg[] removedGuess = new FeedbackPeg[changedGuess.length -1];
		for( int i = 0; i < removedGuess.length; i++)
		{
			removedGuess[i] = changedGuess[i];
		}
		
		removeUserHint();
		if(removedGuess.length != 0)
		addUserHint(removedGuess);
		else
			Gui.newGuessSet = true;
		
	}

	public static void resetData()
	{
		guessCount = 0;
		Solution = null;
		UserGuesses = new ArrayList<>();
		hint = new ArrayList<>();
		userHint = new ArrayList<>();
	}
}

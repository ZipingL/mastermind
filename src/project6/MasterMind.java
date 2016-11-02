package project6;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class MasterMind {
	private static java.util.Random rand = new java.util.Random();
	public static String aboutMe = new String(
			  "MasterMind Game version 1.0\n"
			+ "Written by Ziping Liu zl3858\n"
			+ "EE 422C Chase, TA: Jeho Oh\n"
			+ "Fall 2015");
	public static String cRulesMessage = new String(
			"Not finished");
					
	final static String gRulesMessage = new String(
			  "Welcome to Mastermind.  Here are the rules if you are the code guesser.\n "
			+ "The computer will think of a secret code. The code consists of 4 colored pegs.\n "
			+ "The pegs MUST be one of six colors: blue, green, orange, purple, red, or yellow.\n "
			+ "A color may appear more than once in the code. You try to guess what colored pegs are\n "
			+ "in the code and what order they are in.   After you make a valid guess the result\n "
			+ "(feedback) will be displayed. The result consists of a black peg for each peg you have guessed exactly correct\n "
			+ "(color and position) in your guess.  For each peg in the guess that is the correct color,\n "
			+ "but is out of position, you get a white peg.  For each peg, which is fully incorrect,\n "
			+ "you get no feedback. ");
	
	final static String welcomeMessage = new String("Welcome to MasterMind");
	final static String goodbyeMessage = new String("Run the game when you are ready then, bye");
	final static String winMessage = new String("Congratuations, you've won");
	final static String loseMessage = new String("You are out of guesses, game over!");
	static int difficulty = 0;
	
	public static void console() {
		setup();
		boolean status = true;
		Scanner myScanner = new Scanner(System.in);
		while(status)
		{
			System.out.print(welcomeMessage +" Console\n" + "Ready to Play? (Y/N) or enter H for help> ");
			String input = myScanner.nextLine();
			System.out.print("\n");
			input = new String(input.toLowerCase());
			if(input.equals("y"))
				status = false;
			if(input.equals("n"))
			{
				System.out.println(goodbyeMessage);
				myScanner.close();
				return;
			}
			if(input.equals("h"))
			{
				System.out.println(
			  gRulesMessage
			+ "When entering guesses you only need to enter the first character of each color as a\n "
			+ "capital letter.\n"
			+ "Sample input: RRRR\n"
			+ "Here the input has 4 red pegs\n"
			+ "Sample feedback output: Black Black White White \n"
			+ "Here the feedback has two black pegs, and two white pegs\n"
			+ "Which means you have two pegs with the right color and position\n"
			+ "And to pegs with the right color but incorrect position");
			}
		}
		
		while(true)
		{
			System.out.println("Play as code guesser (1) or Play as the code maker (2) ? (1/2)> ");
			String input = myScanner.nextLine();
			if(input.equals("1"))
			{
				playAsGuesser();
				myScanner.close();
				return;
			}
			
			if(input.equals("2"))
			{
				playAsCoder();
				myScanner.close();
				return;
			}
			
			else
			{
				System.out.println("Incorrect input, try again");
			}
		}
		

	}
	
	static void playAsCoder()
	{
		Scanner myScanner = new Scanner(System.in);
		while(true)
		{
			System.out.print("Generate your code. Valid colors are  ");
			for(int i = 0; i < Data.Parameter.PEG_COLOR_NUMBER; i++)
			{
				System.out.print(Peg.toStringPegColor[i] + " ");
			}
			System.out.println("");
			System.out.println("Enter " + Data.Parameter.PEGCOUNT + " Colors > ");

			String userCode = myScanner.nextLine();
			userCode = new String(userCode.toUpperCase());
			Peg[] code = parseInput(userCode);			
			Data.addSolution(code);
			
			if(code == null)
			{
				System.out.println("Invalid Code Entered, Please try again");
			}
			else
			{
				break;
			}		
		}
		
		System.out.println("The computer will now attempt to guess your code.");
		
		boolean guessStatus = false;
		FeedBackStatus hint = null;
		while(guessStatus == false)
		{
			/*
			try {
			    Thread.sleep(2000);               
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			} */
			
			if(Data.ifGuessLimitReached())
			{
				System.out.println("The AI Has run out of guesses, you win!");
				break;
			}
			easyAI(hint);
			Peg.checkPegGuessValidity();
			
			System.out.print("AI's Guess " + (Data.guessCount >= 10 ? "" : " ") + (Data.guessCount)  + " of " + Data.Parameter.GUESSLIMIT + ": ");

			printPegs(Data.UserGuesses.get(Data.UserGuesses.size() -1 ));
			System.out.println("");
			boolean hintStatus = true;
			GETUSERHINT:
			while(hintStatus)
			{
				System.out.print("Please enter up to " + Data.Parameter.PEGCOUNT + " white (W) or black (B) pegs or nothing for the hint, (e.g. BW) > ");
				String feedback = myScanner.nextLine();
				feedback = new String(feedback.toUpperCase());
				Peg[] feedbackPegs = parseFeedBackInput(feedback);
				if(feedbackPegs == null && (Data.hint.size() != 0 || Data.hint != null))
				{
					System.out.println("Computer has detected your input is invalid (Not W or B Pegs), try again.");
					continue GETUSERHINT;
				}
				
				FeedbackPeg[] parsedFeedBackPegs = Peg.pegsToFeedbackPegs(feedbackPegs);
				
				if(!Peg.checkFeedbackValidity(parsedFeedBackPegs))
				{
					while(true)
					{
						System.out.print("Computer has detected your hint is incorrect. You may continue on (1) or fix your hint (2)> ");
						String input = myScanner.nextLine();
						if(input.equals("1"))
						{
							hintStatus = false;
							break;
						}
						
						if(input.equals("2"))
						{
							continue GETUSERHINT;
						}
						
						else
						{
							System.out.println("Invalid response, try again.");
						}
					}
				}
				
				hint = FeedbackPeg.feedBackPegsToFeedBackStatus(parsedFeedBackPegs);
				hintStatus = false;
			}
			
			if(hint.getBlackCount() == Data.Parameter.PEGCOUNT)
			{
				guessStatus = true;
			}
			

		}
		
		if(guessStatus == true)
		{
			System.out.println("AI Has guessed you code successfully. You lose!");	
		}
		
		myScanner.close();
		
	}
	
	public static void runAI(FeedBackStatus hint)
	{
		if(difficulty == 3)
			mediumAI(hint);
		else
			easyAI(hint);
	}
	
	
	private static Peg[] parseFeedBackInput(String feedback) 
	{
		
		if(feedback.length() > Data.Parameter.PEGCOUNT) return null;
		
		String[] feedbackString = new String[feedback.length()];
		for(int i = 0; i < feedback.length(); i++)
		{
			feedbackString[i] = new String(String.valueOf(feedback.charAt(i)));
			
			if(feedbackString[i].equals("W"))
			{
				feedbackString[i] = "FW";
			}
			
			else if(feedbackString[i].equals("B"))
			{
				feedbackString[i] = "FB";
			}
			
			else
			{
				return null;
			}
		}
		return Peg.StringToPeg(feedbackString);
	}

	static void printPegs(Peg[] pegs)
	{
		for(Peg peg : pegs)
		{
			System.out.print(peg);
		}
	}
	
	static void easyAI(FeedBackStatus hint)
	{
		String guess = new String("");
		for(int i = 0; i < Data.Parameter.PEGCOUNT; i++)
		{
			int color = rand.nextInt(Data.Parameter.PEG_COLOR_NUMBER);
			guess += Peg.toStringPegColor[color];
		}
		
		Peg[] solutionGuess = parseInput(guess);
		Data.addUserGuess(solutionGuess);	
		Data.addGuessCount();
	}
	
	static ArrayList<AIColor> colors = new ArrayList<>();
	static int lastColorSubmission = 0;
	static int blackCount = 0;
	static ArrayList<Peg> smartGuesses = new ArrayList<>();
	static void mediumAI(FeedBackStatus hint)
	{
		if(Data.UserGuesses != null && Data.UserGuesses.size() > 0)
		{
			if(hint.getBlackCount() > 0)
			{
				colors.add(new AIColor(hint, lastColorSubmission - 1));
				blackCount += hint.getBlackCount();
			}
			
			if(blackCount == Data.Parameter.PEGCOUNT)
			{
				smartGuess();
			}
			
			else
			{
				String guess = new String("");
				for(int i = 0; i < Data.Parameter.PEGCOUNT; i++)
				{
					guess += Peg.toStringPegColor[lastColorSubmission];
				}
				lastColorSubmission++;
				Peg[] solutionGuess = parseInput(guess);
				Data.addUserGuess(solutionGuess);	
				Data.addGuessCount();
			}
		}
		
		else
		{
			String guess = new String("");
			for(int i = 0; i < Data.Parameter.PEGCOUNT; i++)
			{
				guess += Peg.toStringPegColor[lastColorSubmission];
			}
			lastColorSubmission++;
			Peg[] solutionGuess = parseInput(guess);
			Data.addUserGuess(solutionGuess);	
			Data.addGuessCount();
		}
	}
	
	private static void smartGuess() {
		// Get the constant Guess of pegs
		String constantGuess = "";
		for(int k = 0; k < colors.size(); k++)
		{
			for(int j = 0; j < colors.get(k).weight.getBlackCount(); j++)
			{
				constantGuess += Peg.toStringPegColor[colors.get(k).color]; 
			}
		}

		Peg[] constantPegGuess = parseInput(constantGuess);
		
		while(true)
		{
			ArrayList<Integer> guessIndexes = new ArrayList<>();
			for(int i = 0; i < Data.Parameter.PEGCOUNT; i++)
			{
				guessIndexes.add(i);
			}
			
			Peg[] aSmartGuess = new Peg[Data.Parameter.PEGCOUNT];
			for(int i = 0; i < Data.Parameter.PEGCOUNT; i++)
			{
				int index = rand.nextInt(guessIndexes.size());
				aSmartGuess[guessIndexes.get(index)] = constantPegGuess[i];
				guessIndexes.remove(index);
			}
			
			if(Peg.uniqueGuess(aSmartGuess))
			{
				Data.addUserGuess(aSmartGuess);
				break;
			}
		}
	}

	static void playAsGuesser() 
	{
		Scanner myScanner = new Scanner(System.in);
		generateCode();
		System.out.println("Computer has generated a code, time to play.");
		for(Peg code : Data.Solution)
		{
			System.out.print(code);
		}
		
		System.out.println("");
		
		boolean winStatus = false;
		while(winStatus == false)
		{

			if(Data.ifGuessLimitReached())
			{
				System.out.println(loseMessage);
			}
			System.out.print("Enter a guess with " + Data.Parameter.PEGCOUNT 
					           +" Pegs | " + (Data.Parameter.GUESSLIMIT - Data.guessCount) + " Guesses Left> ");
			String input = myScanner.nextLine();
			Peg[] parsedInput = parseInput(input);
			if(parsedInput == null)
			{
				System.out.println("Invalid Input, Try again");
				continue;
			}
			Data.addUserGuess(parsedInput);
			FeedBackStatus hint = Peg.checkPegGuessValidity();
			if(hint.getBlackCount() == Data.Parameter.PEGCOUNT)
			{
				winStatus = true;
			}

			System.out.println("Result: ");
			for(FeedbackPeg feedbackPeg : Data.hint.get(Data.hint.size()-1))
			{
				System.out.print(feedbackPeg);
			}
			System.out.println("");
			
			Data.incrementGuessCount();
			
			if(winStatus == true)
			{
				System.out.println(winMessage);
			}
			
			

		}
		
		myScanner.close();
		return;
	}
	
	public static Peg[] parseInput(String input)
	{
		input = new String(input.toUpperCase());
		String[] guesses = new String[Data.Parameter.PEGCOUNT];
		for(int i = 0; i < guesses.length; i++)
		{
			guesses[i] = new String(String.valueOf(input.charAt(i)));
		}
		return Peg.StringToPeg(guesses);
	}
	
	
	
	public static boolean setup()
	{
		boolean initializerStatus = false;
		if(Data.Solution == null)
		{
			Data.Solution = new Peg[Data.Parameter.PEGCOUNT];
			initializerStatus = true;
		}
		
		if(Data.UserGuesses == null)
		{
			Data.UserGuesses = new ArrayList<>();
			initializerStatus = true;
		}
		
		return initializerStatus;
	}
	

	public static void generateCode()
	{
		// Generates code with 6 random colors, no guarantee for how many guaranteed unique colors
		if(difficulty == 2)
		for(int i = 0; i < Data.Parameter.PEGCOUNT; i++)
		{
			int color = rand.nextInt(Data.Parameter.PEG_COLOR_NUMBER);
			
			Data.Solution[i] = Peg.createRespectivePeg(null, color);
		}
		
		// Generates code only with 3 random colors, no guarantee for how many unique colors
		else if(difficulty == 1)
		{
			ArrayList<Integer> colors = new ArrayList<>();
			while(colors.size() < 3)
			{
				int color = rand.nextInt(Data.Parameter.PEG_COLOR_NUMBER);
				if(!colors.contains(color))
				{
					colors.add(color);
				}
			}
			
			for(int i = 0; i < Data.Parameter.PEGCOUNT; i++)
			{
				int color = colors.get(rand.nextInt(3));
				Data.Solution[i] = Peg.createRespectivePeg(null, color);
			}
		}
		
		else if(difficulty == 0)
		{
			ArrayList<Integer> colors = new ArrayList<>();
			while(colors.size() < 2)
			{
				int color = rand.nextInt(Data.Parameter.PEG_COLOR_NUMBER);
				if(!colors.contains(color))
				{
					colors.add(color);
				}
			}
			
			for(int i = 0; i < Data.Parameter.PEGCOUNT; i++)
			{
				int color = colors.get(rand.nextInt(2));
				Data.Solution[i] = Peg.createRespectivePeg(null, color);
			}
		}

	}
	
	public static enum Commands
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
	
	public static void resetMasterMind() {
		Data.resetData();
		// Reset AI
		colors = new ArrayList<>();
		lastColorSubmission = 0;
		blackCount = 0;
		Data.Solution = new Peg[Data.Parameter.PEGCOUNT];
		smartGuesses = new ArrayList<>();
		generateCode();		
	}
	




}

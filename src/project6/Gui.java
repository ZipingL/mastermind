package project6;


import java.awt.Toolkit;
import java.net.URL;
import java.util.*;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.scene.control.*;

// this tutorial comes from -- http://docs.oracle.com/javafx/2/canvas/jfxpub-canvas.htm


public class Gui extends Application {
	
	static final int PegSpace = 60;
	static final double pegScale = 60;
	private static double welcomeX = 400;
	private static double welcomeY = 400;
	Label turnCount = new Label("Turn " + ((Data.guessCount+1) < Data.Parameter.GUESSLIMIT ? (Data.guessCount+1) : Data.Parameter.GUESSLIMIT) +" of " + Data.Parameter.GUESSLIMIT + " ");
	Stage window;
	Scene choosePlayType, chooseGuiPlayType, masterMindGui, chooseCode, masterMindCoder;
	ScrollPane pegSP = new ScrollPane();
	ArrayList<Peg> userChoices = new ArrayList<>();
	
    Group root2 = new Group();
   Canvas canvas2 = new Canvas(820, 720);
      GraphicsContext gc2 = canvas2.getGraphicsContext2D();

    Group root = new Group();
    Canvas canvas = new Canvas(820, 720);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    public static boolean newGuessSet = true;
	private boolean winStatus = false;
    public static void main(String[] args) {
    		MasterMind.setup();
    		launch(Gui.class, args);
    }
    
    private void clear(Canvas canvas, GraphicsContext gc) {
    	gc = canvas.getGraphicsContext2D();
    	gc.setFill(Color.rgb(240, 240, 240));
    	gc.fillRect(0,  0,  canvas.getWidth(), canvas.getHeight());
    }
 
    @Override
    public void start(Stage primaryStage) {
    	window = primaryStage;
    	javafx.scene.image.Image appImage = null;
    	URL imageURL = Gui.class.getResource("/GameIcon.png");
    	appImage = new Image(imageURL.toString());
    	window.getIcons().add(appImage);
    	imageURL = Gui.class.getResource("/Gamelogo.png");
    	appImage = new Image(imageURL.toString());
        ImageView iv2 = new ImageView();
        iv2.setImage(appImage);
        iv2.setFitWidth(180);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);
    	Label label1 = new Label("\nPlease choose a type of format you would\nlike to play the game in.");
    	Button console = new Button("GUI Version");
    	console.setOnAction(e-> window.setScene(chooseGuiPlayType));
    	
    	Button gui = new Button("Console Version");
    	gui.setOnAction(e -> MasterMind.console());
       
    	VBox layout1 = new VBox(20);
    	layout1.getChildren().addAll(iv2, label1, console, gui);
    	layout1.setAlignment(Pos.CENTER);
        choosePlayType = new Scene(layout1, Gui.welcomeX, Gui.welcomeY);
        
        HBox codeChooser = new HBox(20);
        Label chooseCode1 = new Label("Choose your peg code");
        Button pegBlue1 = new Button();
        pegBlue1.setGraphic(new ImageView(new BluePeg().returnColorImage()));
        pegBlue1.setOnAction(e -> {
        	addUserChoice(new BluePeg(), true);
        });
       
        Button pegRed1 = new Button();
        pegRed1.setGraphic(new ImageView(new RedPeg().returnColorImage()));
        pegRed1.setOnAction(e -> {
        	addUserChoice(new RedPeg(), true);
        });
        
        Button pegOrange1 = new Button();
        pegOrange1.setGraphic(new ImageView(new OrangePeg().returnColorImage()));
        pegOrange1.setOnAction(e -> {
        	addUserChoice(new OrangePeg(), true);
        });
        
        Button pegYellow1 = new Button();
        pegYellow1.setGraphic(new ImageView(new YellowPeg().returnColorImage()));
        pegYellow1.setOnAction(e -> {
        	addUserChoice(new YellowPeg(), true);
        });
        
        Button pegGreen1 = new Button();
        pegGreen1.setGraphic(new ImageView(new GreenPeg().returnColorImage()));
        pegGreen1.setOnAction(e -> {
        	addUserChoice(new GreenPeg(), true);
        });
        
        Button pegPurple1 = new Button();
        pegPurple1.setGraphic(new ImageView(new PurplePeg().returnColorImage()));
        pegPurple1.setOnAction(e -> {
        	addUserChoice(new PurplePeg(), true);
        });
        

        Button submit1 = new Button("Submit");
        submit1.setOnAction( e -> {
        	Data.Solution = userChoices.toArray(Data.Solution);
        	userChoices = new ArrayList<>();
        	newGuessSet = true;
        	Data.UserGuesses.remove(Data.UserGuesses.size() -1);
        	Data.guessCount = 0;
        	MasterMind.runAI(null);
        	window.setScene(masterMindCoder);
        	drawShapes(gc2, true);
        });
        
        codeChooser.getChildren().addAll(chooseCode1, pegBlue1, pegRed1, pegOrange1, pegYellow1, pegGreen1, pegPurple1, submit1);
        codeChooser.setAlignment(Pos.CENTER);
        chooseCode = new Scene(codeChooser,1200, 300);
        
        VBox chooseGuiType = new VBox(20);
        Label chooseGuiTypeLabel = new Label("Please choose a playing mode.");
        Button coder = new Button("Play as guesser");
        coder.setOnAction(e -> {
        	MasterMind.generateCode();
        	window.setScene(masterMindGui);
        	});
        
        
        
        Button guesser = new Button("Play as coder");
        guesser.setOnAction(e -> window.setScene(chooseCode));
        chooseGuiType.getChildren().addAll(chooseGuiTypeLabel, coder, guesser);
        chooseGuiType.setAlignment(Pos.CENTER);
        chooseGuiPlayType = new Scene(chooseGuiType, welcomeX, welcomeY);
        

        
        
// Implement Guessing Part
        // Implement top menu
        MenuBar menuBar = new MenuBar();
        Menu menuEdit = new Menu("Edit");
        Menu menuSettings = new Menu("Settings");
        Menu menuHelp = new Menu("Help");
        MenuItem helpAdd = new MenuItem("How To Play");
        helpAdd.setOnAction(
        	e -> {
        		boolean input = false;
        		input = HighAlertWindow.display("Instructions", "Choose a rule set to read", "How to play as guesser", "How to play as code maker");
        		if(input)
        			HighAlertWindow.display("Guesser", MasterMind.gRulesMessage, "Okay",null);
        		else
        			HighAlertWindow.display("Coder", MasterMind.cRulesMessage, "Okay", null);
        	}
        	);
        menuHelp.getItems().addAll(helpAdd);
        helpAdd = new MenuItem("About");
        helpAdd.setOnAction(e -> HighAlertWindow.display("About", MasterMind.aboutMe, "Okay", null));
        menuHelp.getItems().addAll(helpAdd);
        
        helpAdd = new MenuItem("Undo");
        helpAdd.setOnAction(e -> {
        	if(userChoices.size() > 0)
        	{
        	userChoices.remove(userChoices.size() -1);
        	Data.removeSingleUserGuess();
        	drawShapes(gc, true);
        	}
        	
        	else
        	{
        		HighAlertWindow.display("Alert", "You cannot undo when there are no more pegs.", "Okay", null);
        	}
        });
        helpAdd.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        
        menuEdit.getItems().addAll(helpAdd);
        
        helpAdd = new MenuItem("Reset Game");
        helpAdd.setOnAction(e-> {
        	boolean status = HighAlertWindow.display("Caution", "Are you sure you want to reset the game?", "Yes", "No");
        	if(status)
        		resetGuiGame();
        });
        menuSettings.getItems().addAll(helpAdd);
        
        Menu menuDifficulty = new Menu("Set Difficulty");
        
        helpAdd = new MenuItem("Very Easy");
        helpAdd.setOnAction(e->{
        	
        	if(MasterMind.difficulty == 0)
        	{
        		HighAlertWindow.display("Alert", "Difficulty already set to this level. You will now be returned back to the current game.", "Okay", null);
        		return;
        	}
        	
        	
        	boolean status = HighAlertWindow.display("Warning", "Setting a different difficulty will restart the game. Default is normal.", "Proceed", "Go back");
        	if(status)
        	{
        		MasterMind.difficulty = 0;
        		resetGuiGame();
        	}
        });
        
        menuDifficulty.getItems().addAll(helpAdd);
        
        helpAdd = new MenuItem("Easy");
        helpAdd.setOnAction(e->{
        	
        	if(MasterMind.difficulty == 1)
        	{
        		HighAlertWindow.display("Alert", "Difficulty already set to this level. You will now be returned back to the current game.", "Okay", null);
        		return;
        	}
        	
        	
        	boolean status = HighAlertWindow.display("Warning", "Setting a different difficulty will restart the game. Default is normal.", "Proceed", "Go back");
        	if(status)
        	{
        		MasterMind.difficulty = 1;
        		resetGuiGame();
        	}
        });
        
        menuDifficulty.getItems().addAll(helpAdd);
        helpAdd = new MenuItem("Normal");
        helpAdd.setOnAction(e->{
        	
        	if(MasterMind.difficulty == 2)
        	{
        		HighAlertWindow.display("Alert", "Difficulty already set to this level. You will now be returned back to the current game.", "Okay", null);
        		return;
        	}
        	
        	
        	boolean status = HighAlertWindow.display("Warning", "Setting a different difficulty will restart the game. Default is normal.", "Proceed", "Go back");
        	if(status)
        	{
        		MasterMind.difficulty = 2;
        		resetGuiGame();
        	}
        });
        
        menuDifficulty.getItems().addAll(helpAdd);
        

        
        menuSettings.getItems().addAll(menuDifficulty);
        
        menuBar.getMenus().addAll(menuEdit, menuSettings, menuHelp);
        
        
        StackPane menuBarPane = new StackPane();
        menuBarPane.getChildren().addAll(menuBar);
        
        
        
        
        // Implement mastermind game view
// Done as fields:
//        Group root = new Group();
//        Canvas canvas = new Canvas(300, 250);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(240, 240, 240));
        
        drawShapes(gc, true);
        root.getChildren().add(canvas);

        
        VBox gameView = new VBox();
        gameView.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(pegSP, Priority.ALWAYS);
        pegSP.setVmax(600);
        pegSP.setPrefSize(115, 600);
        pegSP.setContent(root);
        gameView.getChildren().addAll(pegSP);
        

        

        
        
        
        
        // Implement mastermind buttons
        
        Button pegBlue = new Button();
        pegBlue.setGraphic(new ImageView(new BluePeg().returnColorImage()));
        pegBlue.setOnAction(e -> {
        	addUserChoice(new BluePeg(), true);
        });
       
        Button pegRed = new Button();
        pegRed.setGraphic(new ImageView(new RedPeg().returnColorImage()));
        pegRed.setOnAction(e -> {
        	addUserChoice(new RedPeg(), true);
        });
        
        Button pegOrange = new Button();
        pegOrange.setGraphic(new ImageView(new OrangePeg().returnColorImage()));
        pegOrange.setOnAction(e -> {
        	addUserChoice(new OrangePeg(), true);
        });
        
        Button pegYellow = new Button();
        pegYellow.setGraphic(new ImageView(new YellowPeg().returnColorImage()));
        pegYellow.setOnAction(e -> {
        	addUserChoice(new YellowPeg(), true);
        });
        
        Button pegGreen = new Button();
        pegGreen.setGraphic(new ImageView(new GreenPeg().returnColorImage()));
        pegGreen.setOnAction(e -> {
        	addUserChoice(new GreenPeg(), true);
        });
        
        Button pegPurple = new Button();
        pegPurple.setGraphic(new ImageView(new PurplePeg().returnColorImage()));
        pegPurple.setOnAction(e -> {
        	addUserChoice(new PurplePeg(), true);
        });
        

        Button submit = new Button("Submit");
        submit.setOnAction( e -> {
        	nextTurn();
        });
        
        
        HBox pegChooser = new HBox();
        pegChooser.getChildren().addAll(pegRed, pegOrange, pegYellow, pegGreen, pegBlue, pegPurple, submit);
        pegChooser.setAlignment(Pos.BASELINE_CENTER);
        
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(menuBarPane);
        mainLayout.setCenter(pegSP);
        mainLayout.setBottom(pegChooser);
        masterMindGui = new Scene(mainLayout, 800, 600);
        
        
        
// Set up the guesser implementation
        setUpGuesserImplementation();
        
        window.setScene(choosePlayType);
        window.setTitle("Master Mind");
        window.show();
        
        

        

        

        /* the timer class (below) shows a simple way to get animation
         * You can clear a canvas by drawing a white rectangle (see clear below)
         * and then redraw all your objects, and you can trigger that behavior inside the 
         * timer's handle method.
         * 
         * You can stop a timer by calling the stop method and start it with the start method
         * (starting and stopping an animation are good things to do with buttons, push
         * a button to start the timer, push another to stop it).
         * 
         * Anyway, this is included to get your ideas flowing
         */
//        Timer timer = new Timer();
//        timer.start();
    }
    
    private void resetGuiGame()
    {
    	MasterMind.resetMasterMind();
    	newGuessSet = true;
    	winStatus = false;
    	userChoices = new ArrayList<>();
    	clear(canvas, gc);
    	clear(canvas2, gc2);
		drawShapes(gc, true);
		drawShapes(gc2, false);
    	
    }

    
    private void setUpGuesserImplementation() {
		
    	 MenuBar menuBar = new MenuBar();
         Menu menuEdit = new Menu("Edit");
         Menu menuSettings = new Menu("Settings");
         Menu menuHelp = new Menu("Help");
         MenuItem helpAdd = new MenuItem("How To Play");
         helpAdd.setOnAction(
         	e -> {
         		boolean input = false;
         		input = HighAlertWindow.display("Instructions", "Choose a rule set to read", "How to play as guesser", "How to play as code maker");
         		if(input)
         			HighAlertWindow.display("Guesser", MasterMind.gRulesMessage, "Okay",null);
         		else
         			HighAlertWindow.display("Coder", MasterMind.cRulesMessage, "Okay", null);
         	}
         	);
         menuHelp.getItems().addAll(helpAdd);
         helpAdd = new MenuItem("View Solution");
         helpAdd.setOnAction(e -> {
        	 String message = "Your Solution: ";
        	 for(Peg peg : Data.Solution)
        	 {
        		 message += peg.toString();
        	 }
        	 HighAlertWindow.display("Solution", message, "Okay", null);
         });
         menuHelp.getItems().addAll(helpAdd);
         helpAdd = new MenuItem("About");
         helpAdd.setOnAction(e -> HighAlertWindow.display("About", MasterMind.aboutMe, "Okay", null));
         menuHelp.getItems().addAll(helpAdd);
         
         helpAdd = new MenuItem("Undo");
         helpAdd.setOnAction(e -> {
         	if(userChoices.size() > 0)
         	{
         	userChoices.remove(userChoices.size() -1);
         	Data.removeSingleUserHint();
         	drawShapes(gc2, false);
         	}
         	
         	else
         	{
         		HighAlertWindow.display("Alert", "You cannot undo when there are no more pegs.", "Okay", null);
         	}
         });
         helpAdd.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
         
         menuEdit.getItems().addAll(helpAdd);
         
         helpAdd = new MenuItem("Reset Game");
         helpAdd.setOnAction(e-> {
         	boolean status = HighAlertWindow.display("Caution", "Are you sure you want to reset the game?", "Yes", "No");
         	if(status)
         	{
         		resetGuiGame();
         		window.setScene(chooseCode);
         	}
         });
         menuSettings.getItems().addAll(helpAdd);
         
         
         menuBar.getMenus().addAll(menuEdit, menuSettings, menuHelp);
         
         StackPane menuBarPane = new StackPane();
         menuBarPane.getChildren().addAll(menuBar);
         
         
         
         
         // Implement mastermind game view
 // Done as fields:

         gc2.setFill(Color.rgb(240, 240, 240));
         drawShapes(gc2, false);
         root2.getChildren().add(canvas2);

         ScrollPane pegSP = new ScrollPane();
         VBox gameView = new VBox();
         gameView.setAlignment(Pos.TOP_CENTER);
         VBox.setVgrow(pegSP, Priority.ALWAYS);
         pegSP.setVmax(600);
         pegSP.setPrefSize(115, 600);
         pegSP.setContent(root2);
         gameView.getChildren().addAll(pegSP);
         

         

         
         
         
         
         // Implement mastermind buttons
         
         Button pegBlack = new Button();
         pegBlack.setGraphic(new ImageView(new BlackPeg().returnColorImage()));
         pegBlack.setOnAction(e -> {
         	addUserChoice(new BlackPeg(), false);
         });
        
         Button pegWhite = new Button();
         pegWhite.setGraphic(new ImageView(new WhitePeg().returnColorImage()));
         pegWhite.setOnAction(e -> {
         	addUserChoice(new WhitePeg(), false);
         });
         
 
         

         Button submit = new Button("Submit");
         submit.setOnAction( e -> {
         	boolean status = nextTurn2();
         	if(!status)
         		return;
         });
         
         
         HBox pegChooser = new HBox();
         pegChooser.getChildren().addAll(turnCount, pegBlack, pegWhite, submit);
         pegChooser.setAlignment(Pos.BASELINE_CENTER);
         
         BorderPane mainLayout = new BorderPane();
         mainLayout.setTop(menuBarPane);
         mainLayout.setCenter(pegSP);
         mainLayout.setBottom(pegChooser);
         masterMindCoder = new Scene(mainLayout, 800, 600);
	}
    boolean changedInput = false;
    private boolean nextTurn2() {
    	if(userChoices.size() <= Data.Parameter.PEGCOUNT + 1)
    	{
			newGuessSet = true;
			if(userChoices.size() == 0)
			{
				Data.addUserHint(new FeedbackPeg[0]);
			}
			userChoices = new ArrayList<>();

			turnCount = new Label("Turn " + ((Data.guessCount+1) > Data.Parameter.GUESSLIMIT ? (Data.guessCount+1) : Data.Parameter.GUESSLIMIT) +" of " + Data.Parameter.GUESSLIMIT);
			FeedBackStatus computerHint = Peg.checkPegGuessValidity();
			FeedBackStatus userMadeHint = FeedbackPeg.feedBackPegsToFeedBackStatus(Data.userHint.get(Data.userHint.size() -1));
			
			if(changedInput == false)
			 MasterMind.runAI(userMadeHint);
			else
				changedInput = true;
			 if(!Peg.checkFeedbackValidity( Data.userHint.size() > 0 ? Data.userHint.get(Data.userHint.size()-1): null ))
			 {
				boolean status =  HighAlertWindow.display("Caution", "Your feedback input is incorrect. ", "Proceed Anyways", "Change Input");

				if(!status)
				{
					Data.userHint.remove(Data.userHint.size() -1);
					Data.removeUserGuess();
					drawShapes(gc2, false);
					return true;
				}
			 }
			 
			 
			if(userMadeHint.getBlackCount() == Data.Parameter.PEGCOUNT)
			{
				winStatus  = true;
			}
			
			drawShapes(gc2, false);
			
			if(winStatus)
			{
				HighAlertWindow.display("Sorry", "The AI Guessed your code, you lose. ", "Okay", null);
				return false;
			}
			
			else if(Data.guessCount >= Data.Parameter.GUESSLIMIT + 1)
			{
				HighAlertWindow.display("Congratulations", "The AI didn't guess your code within the limit of turns. You Win!", "Okay", null);
				return false;
			}
    	}
    	else
    	{
    		HighAlertWindow.display("Alert", "Not enough pegs selected", "Okay", null);
    	}
    	
    	return true;
    }

	private void nextTurn() {
    	
    	if(userChoices.size() == Data.Parameter.PEGCOUNT)
    	{
			newGuessSet = true;
			userChoices = new ArrayList<>();
			Data.guessCount++;
			turnCount = new Label("Turn " + ((Data.guessCount+1) > Data.Parameter.GUESSLIMIT ? (Data.guessCount+1) : Data.Parameter.GUESSLIMIT) +" of " + Data.Parameter.GUESSLIMIT);
			FeedBackStatus hint = Peg.checkPegGuessValidity();
			if(hint.getBlackCount() == Data.Parameter.PEGCOUNT)
			{
				winStatus  = true;
			}
			
			drawShapes(gc, true);
			
			if(winStatus)
			{
				HighAlertWindow.display("Congratuations", MasterMind.winMessage, "Okay", null);
			}
			
			else if(Data.guessCount == Data.Parameter.GUESSLIMIT)
			{
				HighAlertWindow.display("Sorry", MasterMind.loseMessage, "Okay", null);
			}
    	}
    	else
    	{
    		HighAlertWindow.display("Alert", "Not enough pegs selected", "Okay", null);
    	}
		
	}

	private boolean addUserChoice(Peg peg, boolean forGuess) {

    	if(userChoices.size() < Data.Parameter.PEGCOUNT)
    	{

    		userChoices.add(peg);
    		if(forGuess)
    		{
    			Peg[] guesses = new Peg[userChoices.size()];
    			if(newGuessSet == false)
    				Data.removeUserGuess();
    			else{
    				newGuessSet = false;
    			}
    			guesses = userChoices.toArray(guesses);
    			Data.addUserGuess(guesses);	
    			drawShapes(gc, true);
    		}
    		
    		else
    		{
    			FeedbackPeg[] clues = new FeedbackPeg[userChoices.size()];
    			if(newGuessSet == false)
    				Data.removeUserHint();
    			else
    				newGuessSet= false;
    			clues = userChoices.toArray(clues);
    			Data.addUserHint(clues);
    			drawShapes(gc2, false);
    		}
    		
    		return true;
    	}
    	
    	else
    	{
    		HighAlertWindow.display("Alert", "You cannot choose anymore pegs. Limit is " +  
    	                            Data.Parameter.PEGCOUNT + ".", "Okay" , null);
    	}
    	
    	return false;
    	
    		
	}

	/* work with the examples below.
     * Change the size and colors of the shapes
     * Draw a triangle 
     */
    private void drawShapes(GraphicsContext gc, boolean forGuess) {
    if(forGuess)
    clear(canvas, gc);
    else
    	clear(canvas2, gc);
    
	javafx.scene.image.Image tempImage = null;
	URL imageURL = Gui.class.getResource("/Empty.png");
	tempImage = new Image(imageURL.toString(), Gui.pegScale, Gui.pegScale, true, true);
    
    
	int erow = 0;
	for(int i = 0; i < Data.Parameter.GUESSLIMIT; i++)
	{	
		int ecol = 200;

		for(int j = 0; j < Data.Parameter.PEGCOUNT; j++)
		{
			gc.drawImage(tempImage, ecol, erow);
			ecol += PegSpace;
		}
		
		
//		if(i < hints.size())
//		for(FeedbackPeg peg: hints.get(i))
//		{
//			gc.drawImage(peg.returnColorImage(), ecol, erow);
//			ecol += PegSpace;
//		}
		erow += PegSpace;
	}
    
    
    	int row = 0;
    	for(int i = 0; i < Data.UserGuesses.size(); i++)
    	{	
			int col = 200;
			for(Peg peg : Data.UserGuesses.get(i))
			{
				gc.drawImage(peg.returnColorImage(), col, row);
				col += PegSpace;
			}
			
			ArrayList<FeedbackPeg[]> hints = null;
			if(!forGuess)
				hints = Data.userHint;
			else
				hints = Data.hint;
			
			if(i < hints.size())
			for(FeedbackPeg peg: hints.get(i))
			{
				gc.drawImage(peg.returnColorImage(), col, row);
				col += PegSpace;
			}
			row += PegSpace;
    			
    		
    	}

    	
    	
//        gc.setFill(Color.GREEN);
//        gc.setStroke(Color.BLUE);
//        gc.setLineWidth(5);
//        gc.strokeLine(40, 10, 10, 40);
//        gc.fillOval(10, 60, 30, 30);
//        gc.strokeOval(60, 60, 30, 30);
//        gc.setLineWidth(1);
//        gc.strokeOval(150, 60, 10, 10);
//        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
////        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
//        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
////        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
////        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
////        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
////        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
////        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
//        gc.fillPolygon(new double[]{10, 40, 10, 40},
//                       new double[]{210, 210, 240, 240}, 4);
////        gc.strokePolygon(new double[]{60, 90, 60, 90},
////                         new double[]{210, 210, 240, 240}, 4);
////        gc.strokePolyline(new double[]{110, 140, 110, 140},
////                          new double[]{210, 210, 240, 240}, 4);
    }
    

    
}

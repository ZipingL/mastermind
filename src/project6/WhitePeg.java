package project6;

import java.net.URL;

import javafx.scene.image.Image;
import project6.Peg.PegColor;

public class WhitePeg extends FeedbackPeg {
	PegColor color()
	{
		return PegColor.WHITE;
	}

	
	public String toString()
	{
		return "White ";
	}


	@Override
	public Image returnColorImage() {
    	javafx.scene.image.Image tempImage = null;
    	URL imageURL = Gui.class.getResource("/White.png");
    	tempImage = new Image(imageURL.toString(), Gui.pegScale, Gui.pegScale, true, true);
    	return tempImage;
	}
}

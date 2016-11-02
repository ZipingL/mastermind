package project6;

import java.net.URL;

import javafx.scene.image.Image;
import project6.Peg.PegColor;

public class BlackPeg extends FeedbackPeg {
	PegColor color()
	{
		return PegColor.BLACK;
	}
	
	
	public String toString()
	{
		return "Black ";
	}


	@Override
	public Image returnColorImage() {
    	javafx.scene.image.Image tempImage = null;
    	URL imageURL = Gui.class.getResource("/Black.png");
    	tempImage = new Image(imageURL.toString(), Gui.pegScale, Gui.pegScale, true, true);
    	return tempImage;
	}
}

package project6;

import java.net.URL;

import javafx.scene.image.Image;
import project6.Peg.PegColor;

public class YellowPeg extends Peg {
	PegColor color()
	{
		return PegColor.YELLOW;
	}
	
	public String toString()
	{
		return "Y";
	}

	@Override
	public Image returnColorImage() {
    	javafx.scene.image.Image tempImage = null;
    	URL imageURL = Gui.class.getResource("/Yellow.png");
    	tempImage = new Image(imageURL.toString(), Gui.pegScale, Gui.pegScale, true, true);
    	return tempImage;
	}
}

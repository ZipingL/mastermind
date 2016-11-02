package project6;

import java.net.URL;

import javafx.scene.image.Image;
import project6.Peg.PegColor;

public class PurplePeg extends Peg {
	PegColor color()
	{
		return PegColor.PURPLE;
	}
	
	public String toString()
	{
		return "P";
	}

	@Override
	public Image returnColorImage() {
    	javafx.scene.image.Image tempImage = null;
    	URL imageURL = Gui.class.getResource("/Purple.png");
    	tempImage = new Image(imageURL.toString(), Gui.pegScale, Gui.pegScale, true, true);
    	return tempImage;
	}
}

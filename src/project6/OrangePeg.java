package project6;

import java.net.URL;

import javafx.scene.image.Image;
import project6.Peg.PegColor;

public class OrangePeg extends Peg {
	PegColor color()
	{
		return PegColor.ORANGE;
	}
	
	public String toString()
	{
		return "O";
	}

	@Override
	public Image returnColorImage() {
    	javafx.scene.image.Image tempImage = null;
    	URL imageURL = Gui.class.getResource("/Orange.png");
    	tempImage = new Image(imageURL.toString(), Gui.pegScale, Gui.pegScale, true, true);
    	return tempImage;
	}
}

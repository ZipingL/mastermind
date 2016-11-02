package project6;

import java.net.URL;

import javafx.scene.image.Image;
import project6.Peg.PegColor;

public class GreenPeg extends Peg {
	PegColor color()
	{
		return PegColor.GREEN;
	}
	
	public String toString()
	{
		return "G";
	}

	@Override
	public Image returnColorImage() {
    	javafx.scene.image.Image tempImage = null;
    	URL imageURL = Gui.class.getResource("/Green.png");
    	tempImage = new Image(imageURL.toString(), Gui.pegScale, Gui.pegScale, true, true);
    	return tempImage;
	}
}

package net.chronakis.school.timetables.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Util {

	/** Loads an fxml when controller is supplied by the xml  */
	public static Parent loadController(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
	}

	/** Loads an fxml form by supplying an already created controller */
	public static Parent loadController(String fxml, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        fxmlLoader.setController(controller);
        return fxmlLoader.load();
	}
}

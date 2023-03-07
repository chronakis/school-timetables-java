package net.chronakis.school.timetables.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.chronakis.school.timetables.core.TTSheet;

public class PreviewController implements Initializable {

    @FXML private Label titleLabel;
    @FXML private TextArea previewArea;
    @FXML private Button closeButton;
    @FXML private Button savePdfButton;
    @FXML private VBox rootNode;

    private TTSheet sheet;

    public PreviewController(TTSheet sheet) {
		super();
		this.sheet = sheet;
	}    
    
    @FXML
    void saveClick(ActionEvent event) {

    }

    @FXML
    void closeClick(ActionEvent event) {
        Node source = (Node) event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }


	@Override
	@FXML
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Initialized: " + this.sheet);
		titleLabel.setText(sheet.getTitle());
		String text = sheet.getQuestions().stream().map(q -> q.dump("__", " \u00D7 ", " \u00F7 ")).collect(Collectors.joining("\n"));
		previewArea.setStyle("-fx-font-family: monospace; -fx-font-weight: bold; -fx-font-size: 15");
		previewArea.setText(text);
	}
}

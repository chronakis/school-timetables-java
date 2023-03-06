package net.chronakis.school.timetables.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.chronakis.adrian.ttgen.TTSheet;

public class TTGeneratorController implements Initializable {
	private static final Integer DEF_QUESTION_COUNT = 20;
	private static final double DEF_DIV_PERCENT = 50.0;
	private static final double DEF_EQU_PERCENT = 50.0;
	private static int MIN_QUEST = 10;
	private static int MAX_QUEST = 200;
	private static int QUESTS_PER_SHEET = 20;
	
	@FXML HBox rootNode;
	@FXML TextField questionCountField;
	@FXML TextField sheetCountField;
	@FXML Slider divSlider;
	@FXML Slider equSlider;
	
	ToggleButton [] numberToggles = new ToggleButton[12];
	
	
	@FXML
    private void generateSheet() throws IOException {
    	String txt = questionCountField.getText();
    	int questCount = Integer.parseInt(txt);
    	double divProb = divSlider.getValue()/100;
    	double equProb = equSlider.getValue()/100;
    	List<Integer> numbers = new ArrayList<>();
    	for (int i = 0 ; i < 12 ; i++) {
    		if (numberToggles[i].isSelected())
    			numbers.add(i+1);
    	}
    	
    	if (questCount < MIN_QUEST || questCount > MAX_QUEST) {
    		showInfoMessage("Number of questions has to be between " + MIN_QUEST + " and " + MAX_QUEST);
    		return;
    	}
    	if (numbers.size() == 0) {
    		showInfoMessage("You must select at least one number from the left");
    		return;
    	}
    	
        // Launch a controller with the content
        TTSheet sheet = new TTSheet(numbers, questCount, divProb, equProb);
        //sheet.getQuestions().forEach(q -> System.out.println(q.dump()));
        
        openPreview(sheet);
    }

	void openPreview(TTSheet sheet) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("preview.fxml"));
        fxmlLoader.setController(new PreviewController(sheet));
        Parent parent = fxmlLoader.load();
        PreviewController previewController = fxmlLoader.<PreviewController>getController();
        
        System.out.println("Setting sheet");

        Scene scene = new Scene(parent, 800, 600);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
	}
	
	void showInfoMessage(String text) {
		Alert alert = new Alert(AlertType.INFORMATION, "Please correct the error and try again", ButtonType.OK);
		alert.setHeaderText(text);
		alert.showAndWait();
	}
	
	
    @FXML
    private void resetSheet() throws IOException {
        for (ToggleButton toggle : numberToggles) {
			toggle.setSelected(false);
		}
        questionCountField.setText(DEF_QUESTION_COUNT.toString());
        divSlider.setValue(DEF_DIV_PERCENT);
        equSlider.setValue(DEF_EQU_PERCENT);        
    }
    
    @FXML
    private void questionCountAction() throws IOException {
        //System.out.println("Question count");
    }

    
	@FXML
	public void initialize(URL location, ResourceBundle resources) {
		questionCountField.setTextFormatter(new TextFormatter<>(integerFilter));
		sheetCountField.setTextFormatter(new TextFormatter<>(integerFilter));

		for (int i = 0 ; i < 12 ; i ++ ) {
			String id = "#bn" + (i+1);
			numberToggles[i] = (ToggleButton) rootNode.lookup(id);
		}
		try {
			resetSheet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void sheetKeyTyped() {
		try {
			int sheets = Integer.parseInt(sheetCountField.getText());
			Integer questions = sheets * QUESTS_PER_SHEET;
			questionCountField.setText(questions.toString());
			
		} catch (NumberFormatException e) {
			System.err.println("Cannot parse sheets as integer");
		}
	}

	@FXML
	void questKeyTyped() {
		try {
			int questions = Integer.parseInt(questionCountField.getText());
			Integer sheets = questions / QUESTS_PER_SHEET + (questions % QUESTS_PER_SHEET > 0 ? 1 : 0);
			sheetCountField.setText(sheets.toString());
			
		} catch (NumberFormatException e) {
			System.err.println("Cannot parse sheets as integer");
		}
	}

	UnaryOperator<Change> integerFilter = change -> {
	    String newText = change.getControlNewText();
	    if (newText.matches("-?([1-9][0-9]*)?")) { 
	    	return change;
	    }
	    return null;
	};
}

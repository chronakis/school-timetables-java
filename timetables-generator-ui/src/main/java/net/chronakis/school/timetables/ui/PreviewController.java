package net.chronakis.school.timetables.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import net.chronakis.school.timetables.core.TTSheet;

public class PreviewController implements Initializable {

    @FXML private Label titleLabel;
    @FXML private TextArea previewArea;
    @FXML private Button closeButton;
    @FXML private Button savePdfButton;
    @FXML private VBox rootNode;

    private TTSheet sheet;
    private PdfGenerator pdfGenerator = null;
    private FileChooser fileChooser = null;
    
    public PreviewController(TTSheet sheet) {
		super();
		this.sheet = sheet;
	}    
    
    @FXML
    void saveClick(ActionEvent event) {
    	if (pdfGenerator == null)
			pdfGenerator = new PdfGenerator(App.config);
    	
    	if (fileChooser == null) {
    		fileChooser = new FileChooser();
    		fileChooser.setTitle("Save pdf file as...");
    		File initial = detectDownloadsDir();
    		if (initial != null)
    			fileChooser.setInitialDirectory(initial);
    		fileChooser.getExtensionFilters().add(new ExtensionFilter("PDF documents", "*.pdf"));
    	}
    	
    	File selectedFile = fileChooser.showSaveDialog(rootNode.getScene().getWindow());
    	if (selectedFile == null)
    		return;
    	
    	try {
    		pdfGenerator.writePdf(sheet, selectedFile);
		} catch (Exception e) {
			new Alert(
				AlertType.ERROR,
				"Could write PDF file: " + e.getLocalizedMessage(),
				ButtonType.OK)
			.showAndWait();
		}
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
	
	private File detectDownloadsDir() {
		String saveFolder = App.config.savefolder;
		String home = System.getProperty("user.home");
		File [] places = {
				new File(saveFolder),
				new File(home, "Desktop"),
				new File(home, "desktop"),
				new File(home),
				new File("."),
		};
		
		for (File dir: places)
			if (dir.exists())
				return dir;
		
		return null;
	}
}

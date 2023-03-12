package net.chronakis.school.timetables.ui;

import com.itextpdf.io.font.constants.StandardFonts;

public class Config {
	public int questsPerSheet;
	public int minQuestions;
	public int maxQuestions;
	public Integer questionCount;
	public double divPercent;
	public double equPercent;

	public int numberPadding;
	public String emptyString;
	
	public String titleFontName;
	public String questFontName;
	
	public float titleFontSize;
	public float title2FontSize;
	public float footerFontSize;
	public float questFontSize;
	public float questVMargins;
	
	public String savefolder;
	
	public Config() {
		reset();
	}
	
	public void reset() {
		questsPerSheet = 20;
		minQuestions = 20;
		maxQuestions = 200;
		questionCount = 20;
		divPercent = 25;
		equPercent = 25;
		
		numberPadding = 3;
		emptyString = "-".repeat(numberPadding);
		
		titleFontName = StandardFonts.HELVETICA;
		questFontName = StandardFonts.COURIER;
		//questFontName = "src/main/resources/fonts/ChivoMono-VariableFont_wght.ttf";

		titleFontSize = 24.0f;
		title2FontSize = 12.0f;
		footerFontSize = 12.0f;
		questFontSize = 22.0f;
		questVMargins = 19.0f;
		
		savefolder = System.getProperty("user.home");
	}
}

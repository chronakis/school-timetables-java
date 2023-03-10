package net.chronakis.school.timetables.ui;

import com.itextpdf.io.font.FontConstants;

public class Config {
	private static final double DEF_DIV_PERCENT = 50.0;
	private static final double DEF_EQU_PERCENT = 50.0;
	private static final int QUESTS_PER_SHEET = 24;
	private static final int MIN_QUEST = QUESTS_PER_SHEET;
	private static final int MAX_QUEST = QUESTS_PER_SHEET * 10;
	private static final Integer DEF_QUESTION_COUNT = QUESTS_PER_SHEET;

	public int questsPerSheet = QUESTS_PER_SHEET;
	public int minQuestions = MIN_QUEST;
	public int maxQuestions = MAX_QUEST;
	public Integer questionCount = DEF_QUESTION_COUNT;
	public double divPercent = DEF_DIV_PERCENT;
	public double equPercent = DEF_EQU_PERCENT;
	
	public String titleFontName;
	public String questFontName;
	
	public float titleFontSize = 12.0f;
	public float footerFontSize = 12.0f;
	public float questFontSize = 22.0f;
	public float questVMargins = 11.0f;
	
	public String savefolder;
	
	public Config() {
		reset();
	}
	
	public void reset() {
		questsPerSheet = QUESTS_PER_SHEET;
		minQuestions = MIN_QUEST;
		maxQuestions = MAX_QUEST;
		questionCount = DEF_QUESTION_COUNT;
		divPercent = DEF_DIV_PERCENT;
		equPercent = DEF_EQU_PERCENT;
		
		titleFontName = FontConstants.HELVETICA;
		questFontName = "src/main/resources/fonts/ChivoMono-VariableFont_wght.ttf";

		titleFontSize = 12.0f;
		footerFontSize = 12.0f;
		questFontSize = 22.0f;
		questVMargins = 11.0f;
		
		savefolder = System.getProperty("user.home");
	}
}

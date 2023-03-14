package net.chornakis.school.timetables.pdf;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.io.font.constants.StandardFonts;

import net.chronakis.school.timetables.core.TTSheet;
import net.chronakis.school.timetables.pdf.Config;
import net.chronakis.school.timetables.pdf.PdfGenerator;

public class PdfGeneratorTest {
	
	private Config config;
	@Before
	public void before() {
		config = new Config();
		config.questsPerSheet = 20;
		config.numberPadding = 3;
		config.emptyString = "-".repeat(config.numberPadding);
		
		config.titleFontName = StandardFonts.HELVETICA;
		config.questFontName = StandardFonts.COURIER;

		config.titleFontSize = 24.0f;
		config.title2FontSize = 12.0f;
		config.footerFontSize = 12.0f;

		config.questFontSize = 22.0f;
		config.questVMargins = 19.0f;		
	}
	
	@Test
	public void testAccptance() throws Exception {
		int [] sizes = {config.questsPerSheet/2,
						config.questsPerSheet,
						config.questsPerSheet + config.questsPerSheet/2,
						config.questsPerSheet * 2,
						config.questsPerSheet * 3,
						};
		//Integer [] nums = {3, 4, 5};
		Integer [] nums = {7};
		PdfGenerator pdfGenerator = new PdfGenerator(config);
		
		for (int size: sizes) {
			TTSheet sheet = new TTSheet(nums, size, 0.5, 0.5);
			pdfGenerator.writePdf(sheet, new File("sheet" + size + ".pdf"));
		}
	}
}

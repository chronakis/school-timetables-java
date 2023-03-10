package net.chronakis.school.timetables.ui;

import java.io.IOException;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import net.chronakis.school.timetables.core.TTQuestion;
import net.chronakis.school.timetables.core.TTSheet;

public class PdfGenerator {
	Config config;
	PdfFont titleFont;
	PdfFont questFont;
	
	public PdfGenerator(Config config) throws IOException {
		this.config = config;
		init();
	}
	
	private void init() throws IOException {
		questFont = PdfFontFactory.createFont(config.questFontName, PdfEncodings.WINANSI, true, true);
		titleFont = PdfFontFactory.createFont(config.titleFontName, PdfEncodings.WINANSI, true, true);
	}
	
	public void writePdf(TTSheet sheet, String destFile) throws IOException {
		Style titleStyle = new Style();
		titleStyle
			.setFont(titleFont)
			.setFontSize(config.titleFontSize)
			.setPaddings(0f, 8.0f, 0f, 8.0f)
			.setBorderBottom(new SolidBorder(1.0f))
			;
		Style footerStyle = new Style();
		footerStyle
			.setFont(titleFont)
			.setFontSize(config.footerFontSize)
			.setPaddingTop(4.0f)
			.setBorderTop(new SolidBorder(0.5f))
			.setTextAlignment(TextAlignment.RIGHT)
			;
		
		Style questStyle = new Style();
		questStyle.setFont(questFont).setFontSize(config.questFontSize);
		
		PdfWriter writer = new PdfWriter(destFile);

		PdfDocument pdfDoc = new PdfDocument(writer);
		Document doc = new Document(pdfDoc);
		
		// Title
		Text title = new Text(sheet.getTitle());
		doc.add(new Paragraph(title).addStyle(titleStyle));
		
		// Table
		Table table = new Table(UnitValue.createPercentArray(new float [] {50.0f, 50.0f}))
				.addStyle(questStyle)
				//.setBorder(Border.NO_BORDER)
				.useAllAvailableWidth()
				.setMargins(30, 20, 0, 20)
				;
		
		// Build the questions
		int cellCount = 0;
		for (TTQuestion quest: sheet.getQuestions()) {
			String leading = String.format("%3d", quest.left).replace(" ", "\u00A0");
			String questText = String.format("%s %s%3d = %3d", leading, quest.sign.toString(), quest.right, quest.result);

			Cell cell = new Cell()
					.setTextAlignment(cellCount % 2 == 0 ? TextAlignment.LEFT : TextAlignment.RIGHT)
					.setMargins(config.questVMargins, 0, config.questVMargins, 0)
					.setBorder(Border.NO_BORDER)
					.add(questText)
					;
			table.addCell(cell);
			cellCount++;
			
			if (cellCount % config.questsPerSheet == 0) {
				doc.add(table);
				doc.add(new Paragraph("Page " + (cellCount/config.questsPerSheet + 1)).addStyle(footerStyle));
			}
		}
		
		
		doc.close();		
	}
}

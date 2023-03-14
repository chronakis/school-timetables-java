package net.chronakis.school.timetables.pdf;

import java.io.File;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import net.chronakis.school.timetables.core.TTQuestion;
import net.chronakis.school.timetables.core.TTSheet;

public class PdfGenerator {
	Config config;
	
	public PdfGenerator(Config config){
		this.config = config;
	}
	
	
	public void writePdf(TTSheet sheet, File destFile) throws Exception {		
		PdfFont questFont = PdfFontFactory.createFont(config.questFontName, PdfEncodings.WINANSI, EmbeddingStrategy.PREFER_EMBEDDED, true);
		PdfFont titleFont = PdfFontFactory.createFont(config.titleFontName, PdfEncodings.WINANSI, EmbeddingStrategy.PREFER_EMBEDDED, true);

		Style titleStyle = new Style()
			.setFont(titleFont)
			.setFontSize(config.titleFontSize)
			.setTextAlignment(TextAlignment.CENTER)
			.setPaddings(0f, 8.0f, 0f, 8.0f)
			.setBorderTop(new SolidBorder(1.0f))
			.setBorderBottom(new SolidBorder(1.0f))
			.setMarginBottom(0.0f)
			.setBackgroundColor(ColorConstants.LIGHT_GRAY, 0.3f)
			;

		Style title2Style = new Style()
			.setFont(titleFont)
			.setFontSize(config.title2FontSize)
			.setTextAlignment(TextAlignment.RIGHT)
			.setPaddings(0f, 8.0f, 0f, 8.0f)
			.setBorderBottom(new SolidBorder(1.0f))
			.setMarginBottom(config.titleFontSize - config.title2FontSize)
			;

		Style footerStyle = new Style()
			.setFont(titleFont)
			.setFontSize(config.footerFontSize)
			.setPaddingTop(4.0f)
			.setBorderTop(new SolidBorder(0.5f))
			.setTextAlignment(TextAlignment.RIGHT)
			;
		
		try (PdfWriter writer = new PdfWriter(destFile.getPath())){

			PdfDocument pdfDoc = new PdfDocument(writer);
			Document doc = new Document(pdfDoc);
			
			// First page title and table are slightly different
			Text title = new Text(sheet.getTitle());
			doc.add(new Paragraph(title).addStyle(titleStyle));
			Table table = createTable(questFont);
			int cellCount = 0;
			for (TTQuestion quest: sheet.getQuestions()) {				
				String left = quest.getLeft(config.emptyString);
				String leadPad = "-".repeat(config.numberPadding - left.length());
				Text leadText = new Text(leadPad).setOpacity(0f);
				Text leftText = new Text(left);
				if (quest.left == null)
					leftText.setBorderBottom(new SolidBorder(0.3f)).setFontColor(ColorConstants.WHITE);
				
				Text signText = new Text(quest.getSign(" \u00D7 ", " \u00F7 ")).setTextAlignment(TextAlignment.CENTER);
				Text rightText = new Text(String.format("%" + config.numberPadding + "s", quest.getRight(config.emptyString)));
				if (quest.right == null)
					rightText.setBorderBottom(new SolidBorder(0.3f)).setFontColor(ColorConstants.WHITE);
				Text equalText = new Text(" = ");
				Text resultText = new Text(String.format("%" + config.numberPadding + "s", quest.getResult(config.emptyString)));
				if (quest.result == null)
					resultText.setBorderBottom(new SolidBorder(0.3f)).setFontColor(ColorConstants.WHITE);
				
				//String questText = quest.dump("__", "\u00D7", "\u00F7");
				// Get the leading and put in a different para.
				//int leadNum = questText.length() - questText.trim().length();
				
				
				Paragraph questPara = new Paragraph()
						.add(leadText)
						.add(leftText)
						.add(signText)
						.add(rightText)
						.add(equalText)
						.add(resultText)
						;
				
				Cell cell = new Cell()
						.setTextAlignment(cellCount % 2 == 0 ? TextAlignment.LEFT : TextAlignment.RIGHT)
						.setPaddings(config.questVMargins, 0, config.questVMargins, 0)
						.setBorder(Border.NO_BORDER)
						.add(questPara)
						;
				table.addCell(cell);
				
				cellCount++;
				if (cellCount % config.questsPerSheet == 0) {
					// Page done. Add stuff 
					doc.add(table);
					int page = cellCount/config.questsPerSheet;
					Paragraph footerPara = new Paragraph("Page " + page).addStyle(footerStyle); 
					doc.add(footerPara);
					
					if (cellCount < sheet.getQuestions().size()) {
						// We have a next page
						doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
						table = createTable(questFont);
						doc.add(new Paragraph(title).addStyle(title2Style));
					}
				}
			}
			doc.close();			
		}
	}
	
	/**
	 * Shorthand for creating a table
	 */
	private Table createTable(PdfFont questFont) {
		Style questStyle = new Style()
				.setFont(questFont)
				.setFontSize(config.questFontSize);
		
		return new Table(UnitValue.createPercentArray(new float [] {50.0f, 50.0f}))
				.addStyle(questStyle)
				//.setBorder(Border.NO_BORDER)
				.useAllAvailableWidth()
				.setMargins(20f, 8f, 0f, 8f)
				;		
	}
}

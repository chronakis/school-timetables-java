package net.chornakis.school.timetables.pdf;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
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

public class TestPdf {

	@Test
	public void testPdf() throws FileNotFoundException {
		String dest = "testPdf.pdf";
		PdfWriter writer = new PdfWriter(dest);

		PdfDocument pdfDoc = new PdfDocument(writer);
		Document document = new Document(pdfDoc);
		document.add(new Paragraph("Hello world from the first paragraph"));
		document.add(new Paragraph("Hello world from the second paragraph"));
		document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		document.add(new Paragraph("Hello world from the third paragraph"));
		document.add(new Paragraph("Hello world from the fourth paragraph"));

		// Closing the document
		document.close();
		System.out.println("PDF Created");
	}

	@Test
	public void testWithFont() throws IOException {
		String fontFile = "src/main/resources/fonts/ChivoMono-VariableFont_wght.ttf";
		
        //FontProgram fontProgram = FontProgramFactory.createFont(REGULAR);
        PdfFont questFont2 = PdfFontFactory.createFont(fontFile, PdfEncodings.WINANSI, EmbeddingStrategy.PREFER_EMBEDDED, true);
        
		
		String dest = "testWithFont.pdf";
		PdfWriter writer = new PdfWriter(dest);

		PdfDocument pdfDoc = new PdfDocument(writer);
		Document doc = new Document(pdfDoc);

		PdfFont titleFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
		PdfFont questFont = PdfFontFactory.createFont(StandardFonts.COURIER);

		Text title = new Text("5, 6 & 7 timetables. 20 Questions.").setFont(titleFont);
		doc.add(new Paragraph(title));

		// Build the questions
		for (int i = 0; i < 10; i++) {
			int num = (int) (10 * Math.random()) + 1;
			Text text = new Text(String.format("%3d x %3d = %3d", num, i, num * i));
			doc.add(new Paragraph(text.setFont(questFont2).setFontSize(18)));
		}

		doc.close();
	}
	
	@Test
	public void testTables() throws IOException {
		String fontFile = "src/main/resources/fonts/ChivoMono-VariableFont_wght.ttf";
        //PdfFont questFont = PdfFontFactory.createFont(fontFile, PdfEncodings.WINANSI, true, true);
        PdfFont questFont = PdfFontFactory.createFont(StandardFonts.COURIER);
		PdfFont titleFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
		
//		PdfFont [] questFonts = new PdfFont[9];
//		for (int i = 0 ;  i < 9 ; i ++) {
//			questFonts[i] = PdfFontFactory.createTtcFont(fontFile, i, PdfEncodings.WINANSI, true, true);			
//		}

		Style titleStyle = new Style();
		titleStyle
			.setFont(titleFont)
			.setFontSize(24.0f)
			.setPaddings(0f, 8.0f, 0f, 8.0f)
			.setBorderBottom(new SolidBorder(1.0f))
			;
		Style footerStyle = new Style();
		footerStyle
			.setFont(titleFont)
			.setFontSize(12.0f)
			.setPaddingTop(4.0f)
			.setBorderTop(new SolidBorder(0.5f))
			.setTextAlignment(TextAlignment.RIGHT)
			;
		
		Style questStyle = new Style();
		questStyle.setFont(questFont).setFontSize(22.0f);
		
		String dest = "testTables.pdf";
		PdfWriter writer = new PdfWriter(dest);

		PdfDocument pdfDoc = new PdfDocument(writer);
		Document doc = new Document(pdfDoc);


		Text title = new Text("5, 6 & 7 timetables. 20 Questions.");
		doc.add(new Paragraph(title).addStyle(titleStyle));
		
		// Build the questions
		Table table = new Table(UnitValue.createPercentArray(new float [] {50.0f, 50.0f}))
				.addStyle(questStyle)
				//.setBorder(Border.NO_BORDER)
				.useAllAvailableWidth()
				.setMargins(30, 20, 0, 20)
				;
		
		for (int i = 0; i < 48; i++) {
			int num = (int) (10 * Math.random()) + 1;
			String leading = String.format("%3d", num).replace(" ", "\u00A0");
			String questText = String.format("%s x%3d = %3d", leading, i, num * i);
			//Text text = new Text(questText).addStyle(questStyle);
			// doc.add(new Paragraph().add(dot).add(text));
			Cell cell = new Cell()
					.setTextAlignment(i % 2 == 0 ? TextAlignment.LEFT : TextAlignment.RIGHT)
					.setMargins(0, 0, 22.0f, 0)
					.setBorder(Border.NO_BORDER)
					.add(new Paragraph(questText))
					;
			table.addCell(cell);
			
			if ((i+1) % 24 == 0) {
				doc.add(table);
				int page = (i + 1) / 24;
				System.out.println("Page " + page);
				doc.add(new Paragraph("Page " + page).addStyle(footerStyle));
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				table = new Table(UnitValue.createPercentArray(new float [] {50.0f, 50.0f}))
						.addStyle(questStyle)
						//.setBorder(Border.NO_BORDER)
						.useAllAvailableWidth()
						.setMargins(30, 20, 0, 20)
						;
			}
		}
		
		
		doc.close();
	}
}

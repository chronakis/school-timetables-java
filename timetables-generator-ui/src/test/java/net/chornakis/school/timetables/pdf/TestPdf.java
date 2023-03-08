package net.chornakis.school.timetables.pdf;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;

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
        PdfFont questFont2 = PdfFontFactory.createFont(fontFile, PdfEncodings.WINANSI, true, true);
        
		
		String dest = "testWithFont.pdf";
		PdfWriter writer = new PdfWriter(dest);

		PdfDocument pdfDoc = new PdfDocument(writer);
		Document doc = new Document(pdfDoc);

		PdfFont titleFont = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
		PdfFont questFont = PdfFontFactory.createFont(FontConstants.COURIER);

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
	
	private Text textWithWidth(String text, PdfFont font, float width) {
		Text result = new Text(text)
		        .setFont(font)
		        .setTextRenderingMode(PdfCanvasConstants.TextRenderingMode.FILL_STROKE)
		        .setStrokeWidth(width)
		        //.setStrokeColor(DeviceGray.BLACK);
		        ;
		return result;
	}
}

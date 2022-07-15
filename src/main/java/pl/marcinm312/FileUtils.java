package pl.marcinm312;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

	private FileUtils() {

	}

	public static void saveDocumentsToTextFile(List<SimpleDocument> listOfDocuments) throws IOException {

		File file = new File("files/dokumenty.txt");
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for (SimpleDocument simpleDocument : listOfDocuments) {
			bufferedWriter.write("Dokument\n");
			bufferedWriter.write(simpleDocument.getTitle() + "\n");
			bufferedWriter.write(simpleDocument.getContent() + "\n");
		}
		bufferedWriter.close();
		System.out.println("Documents have been saved to the file");
	}

	public static void createInvoicePdfFile() {

		Double x = 1234.56;
		Double y = 345.88;
		Double w = 550.84;
		Double z = x + y + w;
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("files/Faktura.pdf")));
			document.open();
			// create content
			Phrase invoiceNumberPhrase = new Phrase("Faktura nr 1234/IOZ/2009",
					FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD, Color.RED));
			com.lowagie.text.Image image = Image.getInstance("logo_placeholder.gif");
			Phrase invoiceBuyerPhrase = new Phrase("Kupiec sp. z o.o.\nJan Kowalski\nul. Miodna 33\n12-345 Gdziekolwiek",
					FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, Color.BLACK));
			PdfPTable pdfPTable = new PdfPTable(3);
			PdfPCell cell = new PdfPCell(new Paragraph("Pozycje na fakturze",
					FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, Color.BLACK)));
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setMinimumHeight(20);
			pdfPTable.addCell(cell);
			pdfPTable.addCell("Lorem ipsum dolor sit amet");
			pdfPTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			pdfPTable.addCell("" + x);
			pdfPTable.addCell("22%");
			pdfPTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			pdfPTable.addCell("Aliquam euismod est suscipit mauris");
			pdfPTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			pdfPTable.addCell("" + y);
			pdfPTable.addCell("7%");
			pdfPTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			pdfPTable.addCell("Aliquam euismod est suscipit mauris");
			pdfPTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			pdfPTable.addCell("" + w);
			pdfPTable.addCell("22%");

			Paragraph invoiceTotalParagraph = new Paragraph(z + "",
					FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, Color.BLUE));
			invoiceTotalParagraph.setAlignment(Element.ALIGN_RIGHT);

			// put content into the document
			document.add(invoiceNumberPhrase);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(invoiceBuyerPhrase);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			int[] widths = {400, 75, 75};
			pdfPTable.setWidths(widths);
			pdfPTable.setTotalWidth(550);
			pdfPTable.setLockedWidth(true);
			document.add(pdfPTable);

			document.add(invoiceTotalParagraph);

			image.setAbsolutePosition(400, 650);
			document.add(image);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createSamplePdfFile() {

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("files/HelloWorld1.pdf")));
			document.open();
			document.add(new Paragraph("Hello World"));
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveDocumentsToOnePdfFile(List<SimpleDocument> listOfDocuments) throws IOException {

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("files/WszystkieDokumenty.pdf")));
			document.open();
			for (SimpleDocument simpleDocument : listOfDocuments) {
				document.addTitle(simpleDocument.getTitle());
				document.add(new Paragraph("Dokument\n"));
				document.add(new Paragraph(simpleDocument.getTitle() + "\n"));
				document.add(new Paragraph(simpleDocument.getContent() + "\n"));
			}
			document.close();
			System.out.println("Documents have been saved to the file");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private static void saveDocumentToTextFile(SimpleDocument simpleDocument, int i) throws IOException {

		File file1 = new File("files/" + i + "_" + simpleDocument.getTitle() + ".txt");
		FileWriter fileWriter = new FileWriter(file1);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		bufferedWriter.write("Dokument\n");
		bufferedWriter.write(simpleDocument.getTitle() + "\n");
		bufferedWriter.write(simpleDocument.getContent() + "\n");
		bufferedWriter.close();
		System.out.println("Document " + i + " have been saved to the file");
	}

	public static void saveDocumentsToManyTextFiles(List<SimpleDocument> listOfDocuments) throws IOException {

		for (int i = 0; i < listOfDocuments.size(); i++) {
			saveDocumentToTextFile(listOfDocuments.get(i), i);
		}
	}

	public static void saveDocumentsToManyPdfFiles(List<SimpleDocument> listOfDocuments) throws IOException, DocumentException {

		for (int i = 0; i < listOfDocuments.size(); i++) {
			saveDocumentToPdfFile(listOfDocuments.get(i), i);
		}
	}

	private static void saveDocumentToPdfFile(SimpleDocument simpleDocument, int i) throws IOException, DocumentException {

		Document document = new Document();
		PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("files/" + i + "_" + simpleDocument.getTitle() + ".pdf")));
		document.open();
		document.addTitle(simpleDocument.getTitle());
		document.add(new Paragraph("Dokument\n"));
		document.add(new Paragraph(simpleDocument.getTitle()));
		document.add(new Paragraph(simpleDocument.getContent()));

		document.close();
	}

	public static void createSamplePdfFileWithDifferentPageSizes() {

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("files/HelloWorld2.pdf")));
			document.open();
			document.add(new Paragraph("The default PageSize is DIN A4."));
			document.setPageSize(PageSize.A3);
			document.newPage();
			document.add(new Paragraph("This PageSize is DIN A3."));
			document.setPageSize(PageSize.A2);
			document.newPage();
			document.add(new Paragraph("This PageSize is DIN A2."));
			document.setPageSize(PageSize.A1);
			document.newPage();
			document.add(new Paragraph("This PageSize is DIN A1."));
			document.setPageSize(PageSize.A0);
			document.newPage();
			document.add(new Paragraph("This PageSize is DIN A0."));
			document.setPageSize(PageSize.A5);
			document.newPage();
			document.add(new Paragraph("This PageSize is DIN A5."));
			document.setPageSize(PageSize.A6);
			document.newPage();
			document.add(new Paragraph("This PageSize is DIN A6."));
			document.setPageSize(PageSize.A7);
			document.newPage();
			document.add(new Paragraph("This PageSize is DIN A7."));
			document.setPageSize(PageSize.A8);
			document.newPage();
			document.add(new Paragraph("This PageSize is DIN A8."));
			document.setPageSize(PageSize.LETTER);
			document.newPage();
			document.add(new Paragraph("This PageSize is LETTER."));
			document.add(new Paragraph("A lot of other standard PageSizes are available."));
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createSamplePdfFileWithSpecifiedMargins() {

		try {
			Document document = new Document(PageSize.A5, 100f, 100f, 100f, 100f);
			PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("files/HelloWorld3.pdf")));
			document.open();
			document.add(new Paragraph(
					"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor auctor ipsum. Ut et nibh. Praesent facilisis quam non est. Donec massa. In accumsan nunc nec metus pharetra dapibus. In nunc. Quisque commodo, elit id fermentum adipiscing, turpis dui ornare tortor, eu interdum metus nulla vitae dolor. Morbi adipiscing, nibh sed luctus feugiat, libero mi mattis sapien, vel tristique nisl metus id lorem. Cras nunc tellus, tempor quis, ultrices sodales, pretium sit amet, est. Donec rhoncus tempus sapien. Aliquam sagittis feugiat arcu. Aenean pulvinar ultricies nunc. Mauris rhoncus, pede ac dapibus ornare, augue ipsum varius ipsum, ut ornare tellus erat quis est. Aliquam metus tellus, vestibulum quis, porta non, aliquam ut, pede. Phasellus lobortis nulla eget sem. Aliquam bibendum lectus non orci."));
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createSamplePdfFileWithAdditionalMetadata() {

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("files/HelloWorld4.pdf")));
			document.open();
			document.addTitle("Hello World example with metadata");
			document.addAuthor("Marcin Michalczyk");
			document.addSubject("This example explains how to add metadata.");
			document.addKeywords("iText, Hello World, metadata");
			document.addCreator("My program using iText");
			document.add(new Paragraph("Hello World with metadata"));
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createSamplePdfFileWithGraphics() {

		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("files/Graphics.pdf")));
			document.open();

			// we create a fontMapper and read all the fonts in the font directory
			DefaultFontMapper mapper = new DefaultFontMapper();
			FontFactory.registerDirectories();
			mapper.insertDirectory("c:\\windows\\fonts");

			// we create a template and a Graphics2D object that corresponds with it
			float w = 150;
			float h = 150;
			PdfContentByte cb = writer.getDirectContent();
			PdfTemplate tp = cb.createTemplate(w, h);
			Graphics2D g2 = tp.createGraphics(w, h, mapper);
			tp.setWidth(w);
			tp.setHeight(h);
			double ew = w / 2;
			double eh = h / 2;
			Ellipse2D.Double circle;
			Ellipse2D.Double oval;
			Ellipse2D.Double leaf;
			Ellipse2D.Double stem;
			Area circleArea;
			Area ovalArea;
			Area leafArea1;
			Area leafArea2;
			Area stemArea1;
			Area stemArea2;
			circle = new Ellipse2D.Double();
			oval = new Ellipse2D.Double();
			leaf = new Ellipse2D.Double();
			stem = new Ellipse2D.Double();
			g2.setColor(Color.green);

			// Creates the first leaf by filling the intersection with two Area objects
			// created from an ellipse.
			leaf.setFrame(ew - 16, eh - 29, 15.0, 15.0);
			leafArea1 = new Area(leaf);
			leaf.setFrame(ew - 14, eh - 47, 30.0, 30.0);
			leafArea2 = new Area(leaf);
			leafArea1.intersect(leafArea2);
			g2.fill(leafArea1);

			// Creates the second leaf.
			leaf.setFrame(ew + 1, eh - 29, 15.0, 15.0);
			leafArea1 = new Area(leaf);
			leafArea2.intersect(leafArea1);
			g2.fill(leafArea2);

			g2.setColor(Color.black);

			// Creates the stem by filling the Area resulting from the subtraction of two
			// Area objects created from an ellipse.
			stem.setFrame(ew, eh - 42, 40.0, 40.0);
			stemArea1 = new Area(stem);
			stem.setFrame(ew + 3, eh - 47, 50.0, 50.0);
			stemArea2 = new Area(stem);
			stemArea1.subtract(stemArea2);
			g2.fill(stemArea1);

			g2.setColor(Color.yellow);

			// Creates the pear itself by filling the Area resulting from the union of two
			// Area objects created by two different ellipses.
			circle.setFrame(ew - 25, eh, 50.0, 50.0);
			oval.setFrame(ew - 19, eh - 20, 40.0, 70.0);
			circleArea = new Area(circle);
			ovalArea = new Area(oval);
			circleArea.add(ovalArea);
			g2.fill(circleArea);

			g2.setColor(Color.black);
			java.awt.Font thisFont = new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 18);
			g2.setFont(thisFont);
			String pear = "Pear";
			FontMetrics metrics = g2.getFontMetrics();
			int width = metrics.stringWidth(pear);
			g2.drawString(pear, (w - width) / 2, 20);
			g2.dispose();
			cb.addTemplate(tp, 50, 600);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

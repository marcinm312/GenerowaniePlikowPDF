import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class Runner {

	public static void main(String[] args) throws DocumentException {
		ArrayList<Dokument> lista = new ArrayList<Dokument>();
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String tmp = "";

		System.out.println();
		System.out.println("What do you want to do?");
		System.out.println("create - utworz dokument");
		System.out.println("show - wyswietl dokumenty");
		System.out.println("save - zapis do pliku tekstowego");
		System.out.println("load - odczyt z pliku tekstowego");
		System.out.println("exit - wyjscie z aplikacji");
		System.out.println("sample - przyklad dokumentu PDF");
		System.out.println("sizes - rozmiary dokumentu PDF");
		System.out.println("margins - marginesy dokumentu PDF");
		System.out.println("metadata - metadane dokumentu PDF");
		System.out.println("invoice - faktura w dokumencie PDF");
		System.out.println("graphics - grafika w dokumencie PDF");
		System.out.println("saveTo1PDF - zapis obecnych dokumentów do 1 pliku pdf");
		System.out.println("savemanyPDF - zapis obecnych dokumentów do osobnych plików PDF");
		System.out.println("saveTXT - zapis obecnych dokumentów do osobnych plików txt");

		while (true) {
			try {
				// read the user input
				tmp = input.readLine();

				// act accordingly
				if (tmp.equals("create"))
					create(lista, input);
				if (tmp.equals("show"))
					show(lista);
				if (tmp.equals("save"))
					save(lista);
				if (tmp.equals("load"))
					load(lista);
				if (tmp.equals("exit"))
					break;
				if (tmp.equals("sample"))
					pdfSample();
				if (tmp.equals("sizes"))
					pdfSizes();
				if (tmp.equals("margins"))
					pdfMargins();
				if (tmp.equals("metadata"))
					pdfMetadata();
				if (tmp.equals("invoice"))
					pdfInvoice();
				if (tmp.equals("graphics"))
					pdfGraphics();
				if (tmp.equals("saveTo1PDF"))
					saveTo1PDF(lista);
				if (tmp.equals("savemanyPDF"))
					saveToManyPDFs(lista);
				if (tmp.equals("saveTXT"))
					saveToManyTXTs(lista);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void create(ArrayList<Dokument> lista, BufferedReader input) throws IOException {
		System.out.println("Enter title: ");
		String title = input.readLine();
		System.out.println("Enter content: ");
		String content = input.readLine();
		Dokument mojDokumentTekstowy = new Dokument(title, content);
		lista.add(mojDokumentTekstowy);
		System.out.println("The document has been created and added to the list");
	}

	private static void show(ArrayList<Dokument> lista) throws IOException {
		for (int i = 0; i < lista.size(); i++) {
			System.out.print("Dokument\n");
			System.out.print(lista.get(i).getTytul() + "\n");
			System.out.print(lista.get(i).getTresc() + "\n");
		}
	}

	private static void save(ArrayList<Dokument> lista) throws IOException {
		File file = new File("dokumenty.txt");
		FileWriter fwriter = new FileWriter(file);
		BufferedWriter bwriter = new BufferedWriter(fwriter);
		for (int i = 0; i < lista.size(); i++) {
			bwriter.write("Dokument\n");
			bwriter.write(lista.get(i).getTytul() + "\n");
			bwriter.write(lista.get(i).getTresc() + "\n");
		}
		bwriter.close();
		System.out.println("Documents have been saved to the file");
	}

	private static void load(ArrayList<Dokument> lista) throws IOException {
		File file = new File("dokumenty.txt");
		FileReader freader = new FileReader(file);
		BufferedReader breader = new BufferedReader(freader);
		lista.clear();
		String line;
		while ((line = breader.readLine()) != null) {
			if (line.equals("Dokument")) {
				String tytul = breader.readLine();
				String tresc = breader.readLine();
				Dokument mojDokumentTekstowy = new Dokument(tytul, tresc);
				lista.add(mojDokumentTekstowy);
			}
		}
		breader.close();
		System.out.println("Documents have been loaded from the file");
	}

	private static void pdfInvoice() {
		Double x = 1234.56;
		Double y = 345.88;
		Double w = 550.84;
		Double z = x + y + w;
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("Faktura.pdf"));
			document.open();
			// create content
			Phrase fTitle = new Phrase("Faktura nr 1234/IOZ/2009",
					FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD, Color.RED));
			Image logo = Image.getInstance("src/logo_placeholder.gif");
			Phrase fBuyer = new Phrase("Kupiec sp. z o.o.\nJan Kowalski\nul. Miodna 33\n12-345 Gdziekolwiek",
					FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, Color.BLACK));
			PdfPTable fTable = new PdfPTable(3);
			PdfPCell cell = new PdfPCell(new Paragraph("Pozycje na fakturze",
					FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, Color.BLACK)));
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setMinimumHeight(20);
			fTable.addCell(cell);
			fTable.addCell("Lorem ipsum dolor sit amet");
			fTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			fTable.addCell("" + x);
			fTable.addCell("22%");
			fTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			fTable.addCell("Aliquam euismod est suscipit mauris");
			fTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			fTable.addCell("" + y);
			fTable.addCell("7%");
			fTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			fTable.addCell("Aliquam euismod est suscipit mauris");
			fTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			fTable.addCell("" + w);
			fTable.addCell("22%");

			Paragraph fTotal = new Paragraph(z + "",
					FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, Color.BLUE));
			fTotal.setAlignment(Element.ALIGN_RIGHT);

			// put content into the document
			document.add(fTitle);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(fBuyer);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			int[] szerokosci = { 400, 75, 75 };
			fTable.setWidths(szerokosci);
			fTable.setTotalWidth(550);
			fTable.setLockedWidth(true);
			document.add(fTable);

			document.add(fTotal);

			logo.setAbsolutePosition(400, 650);
			document.add(logo);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void pdfSample() {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("HelloWorld1.pdf"));
			document.open();
			document.add(new Paragraph("Hello World"));
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveTo1PDF(ArrayList<Dokument> lista) throws IOException {

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("WszystkieDokumenty.pdf"));
			document.open();
			for (int i = 0; i < lista.size(); i++) {
				document.addTitle(lista.get(i).getTytul());
				document.add(new Paragraph("Dokument\n"));
				document.add(new Paragraph(lista.get(i).getTytul() + "\n"));
				document.add(new Paragraph(lista.get(i).getTresc() + "\n"));
			}
			document.close();
			System.out.println("Documents have been saved to the file");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void savemetotxt(Dokument dokument, int i) throws IOException {

		File file1 = new File(+i + "_" + dokument.getTytul() + ".txt");
		FileWriter fwriter = new FileWriter(file1);
		BufferedWriter bwriter = new BufferedWriter(fwriter);

		bwriter.write("Dokument\n");
		bwriter.write(dokument.getTytul() + "\n");
		bwriter.write(dokument.getTresc() + "\n");
		bwriter.close();
		System.out.println("Document " + i + " have been saved to the file");

	}

	private static void saveToManyTXTs(ArrayList<Dokument> lista) throws IOException {

		for (int i = 0; i < lista.size(); i++) {
			savemetotxt(lista.get(i), i);
		}

	}

	private static void saveToManyPDFs(ArrayList<Dokument> lista) throws IOException, DocumentException {
		for (int i = 0; i < lista.size(); i++) {
			savemetopdf(lista.get(i), i);
		}

	}

	private static void savemetopdf(Dokument dokument, int i) throws IOException, DocumentException {

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(+i + "_" + dokument.getTytul() + ".pdf"));
		document.open();
		document.addTitle(dokument.getTytul());
		document.add(new Paragraph("Dokument\n"));
		document.add(new Paragraph(dokument.getTytul()));
		document.add(new Paragraph(dokument.getTresc()));

		document.close();

	}

	private static void pdfSizes() {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("HelloWorld2.pdf"));
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

	private static void pdfMargins() {
		try {
//			Document document = new Document(PageSize.A5);
			Document document = new Document(PageSize.A5, 100f, 100f, 100f, 100f);
			PdfWriter.getInstance(document, new FileOutputStream("HelloWorld3.pdf"));
			document.open();
			document.add(new Paragraph(
					"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor auctor ipsum. Ut et nibh. Praesent facilisis quam non est. Donec massa. In accumsan nunc nec metus pharetra dapibus. In nunc. Quisque commodo, elit id fermentum adipiscing, turpis dui ornare tortor, eu interdum metus nulla vitae dolor. Morbi adipiscing, nibh sed luctus feugiat, libero mi mattis sapien, vel tristique nisl metus id lorem. Cras nunc tellus, tempor quis, ultrices sodales, pretium sit amet, est. Donec rhoncus tempus sapien. Aliquam sagittis feugiat arcu. Aenean pulvinar ultricies nunc. Mauris rhoncus, pede ac dapibus ornare, augue ipsum varius ipsum, ut ornare tellus erat quis est. Aliquam metus tellus, vestibulum quis, porta non, aliquam ut, pede. Phasellus lobortis nulla eget sem. Aliquam bibendum lectus non orci."));
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void pdfMetadata() {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("HelloWorld4.pdf"));
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

	private static void pdfGraphics() {
		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Graphics.pdf"));
			document.open();

			// we create a fontMapper and read all the fonts in the font directory
			DefaultFontMapper mapper = new DefaultFontMapper();
			FontFactory.registerDirectories();
			mapper.insertDirectory("c:\\windows\\fonts");

			/*
			 * Map map = mapper.getMapper(); for (Iterator i = map.keySet().iterator();
			 * i.hasNext();) { String name = (String) i.next(); System.out.println(name +
			 * ": " + ((DefaultFontMapper.BaseFontParameters) map.get(name)).fontName); }
			 */

			// we create a template and a Graphics2D object that corresponds with it
			int w = 150;
			int h = 150;
			PdfContentByte cb = writer.getDirectContent();
			PdfTemplate tp = cb.createTemplate(w, h);
			Graphics2D g2 = tp.createGraphics(w, h, mapper);
			tp.setWidth(w);
			tp.setHeight(h);
			double ew = w / 2;
			double eh = h / 2;
			Ellipse2D.Double circle, oval, leaf, stem;
			Area circ, ov, leaf1, leaf2, st1, st2;
			circle = new Ellipse2D.Double();
			oval = new Ellipse2D.Double();
			leaf = new Ellipse2D.Double();
			stem = new Ellipse2D.Double();
			circ = new Area(circle);
			ov = new Area(oval);
			leaf1 = new Area(leaf);
			leaf2 = new Area(leaf);
			st1 = new Area(stem);
			st2 = new Area(stem);
			g2.setColor(Color.green);

			// Creates the first leaf by filling the intersection of two Area objects
			// created from an ellipse.
			leaf.setFrame(ew - 16, eh - 29, 15.0, 15.0);
			leaf1 = new Area(leaf);
			leaf.setFrame(ew - 14, eh - 47, 30.0, 30.0);
			leaf2 = new Area(leaf);
			leaf1.intersect(leaf2);
			g2.fill(leaf1);

			// Creates the second leaf.
			leaf.setFrame(ew + 1, eh - 29, 15.0, 15.0);
			leaf1 = new Area(leaf);
			leaf2.intersect(leaf1);
			g2.fill(leaf2);

			g2.setColor(Color.black);

			// Creates the stem by filling the Area resulting from the subtraction of two
			// Area objects created from an ellipse.
			stem.setFrame(ew, eh - 42, 40.0, 40.0);
			st1 = new Area(stem);
			stem.setFrame(ew + 3, eh - 47, 50.0, 50.0);
			st2 = new Area(stem);
			st1.subtract(st2);
			g2.fill(st1);

			g2.setColor(Color.yellow);

			// Creates the pear itself by filling the Area resulting from the union of two
			// Area objects created by two different ellipses.
			circle.setFrame(ew - 25, eh, 50.0, 50.0);
			oval.setFrame(ew - 19, eh - 20, 40.0, 70.0);
			circ = new Area(circle);
			ov = new Area(oval);
			circ.add(ov);
			g2.fill(circ);

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

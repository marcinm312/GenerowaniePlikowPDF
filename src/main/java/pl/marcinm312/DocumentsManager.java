package pl.marcinm312;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentsManager {

	private final List<SimpleDocument> listOfDocuments = new ArrayList<>();

	public List<SimpleDocument> getListOfDocuments() {
		return listOfDocuments;
	}

	public void createDocument(BufferedReader input) throws IOException {

		System.out.println("Enter title: ");
		String title = input.readLine();
		System.out.println("Enter content: ");
		String content = input.readLine();
		SimpleDocument simpleDocument = new SimpleDocument(title, content);
		listOfDocuments.add(simpleDocument);
		System.out.println("The document has been created and added to the list");
	}

	public void showDocuments() {

		for (SimpleDocument simpleDocument : listOfDocuments) {
			System.out.print("Dokument\n");
			System.out.print(simpleDocument.getTitle() + "\n");
			System.out.print(simpleDocument.getContent() + "\n");
		}
	}

	public void loadDocumentsFromTextFile() throws IOException {

		File file = new File("files/dokumenty.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		listOfDocuments.clear();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			if (line.equals("Dokument")) {
				String title = bufferedReader.readLine();
				String content = bufferedReader.readLine();
				SimpleDocument simpleDocument = new SimpleDocument(title, content);
				listOfDocuments.add(simpleDocument);
			}
		}
		bufferedReader.close();
		System.out.println("Documents have been loaded from the file");
	}
}

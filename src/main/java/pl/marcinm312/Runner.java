package pl.marcinm312;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.lowagie.text.DocumentException;

public class Runner {

    public static void main(String[] args) throws DocumentException {

        DocumentsManager documentsManager = new DocumentsManager();

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String tmp;

        System.out.println();
        System.out.println("What do you want to do?");
        System.out.println("create - utwórz dokument");
        System.out.println("show - wyświetl dokumenty");
        System.out.println("save - zapis do pliku tekstowego");
        System.out.println("load - odczyt z pliku tekstowego");
        System.out.println("exit - wyjście z aplikacji");
        System.out.println("sample - przykład dokumentu PDF");
        System.out.println("sizes - rozmiary dokumentu PDF");
        System.out.println("margins - marginesy dokumentu PDF");
        System.out.println("metadata - metadane dokumentu PDF");
        System.out.println("invoice - faktura w dokumencie PDF");
        System.out.println("graphics - grafika w dokumencie PDF");
        System.out.println("saveToOnePdf - zapis obecnych dokumentów do 1 pliku pdf");
        System.out.println("saveToManyPdf - zapis obecnych dokumentów do osobnych plików PDF");
        System.out.println("saveTxt - zapis obecnych dokumentów do osobnych plików txt");

        while (true) {
            try {
                // read the user input
                tmp = input.readLine();

                // act accordingly
                if (tmp.equals("create"))
                    documentsManager.createDocument(input);
                if (tmp.equals("show"))
                    documentsManager.showDocuments();
                if (tmp.equals("save"))
                    FileUtils.saveDocumentsToTextFile(documentsManager.getListOfDocuments());
                if (tmp.equals("load"))
                    documentsManager.loadDocumentsFromTextFile();
                if (tmp.equals("exit"))
                    break;
                if (tmp.equals("sample"))
                    FileUtils.createSamplePdfFile();
                if (tmp.equals("sizes"))
                    FileUtils.createSamplePdfFileWithDifferentPageSizes();
                if (tmp.equals("margins"))
                    FileUtils.createSamplePdfFileWithSpecifiedMargins();
                if (tmp.equals("metadata"))
                    FileUtils.createSamplePdfFileWithAdditionalMetadata();
                if (tmp.equals("invoice"))
                    FileUtils.createInvoicePdfFile();
                if (tmp.equals("graphics"))
                    FileUtils.createSamplePdfFileWithGraphics();
                if (tmp.equals("saveToOnePdf"))
                    FileUtils.saveDocumentsToOnePdfFile(documentsManager.getListOfDocuments());
                if (tmp.equals("saveToManyPdf"))
                    FileUtils.saveDocumentsToManyPdfFiles(documentsManager.getListOfDocuments());
                if (tmp.equals("saveTxt"))
                    FileUtils.saveDocumentsToManyTextFiles(documentsManager.getListOfDocuments());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

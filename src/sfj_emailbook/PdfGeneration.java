package sfj_emailbook;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public class PdfGeneration {

    public void pdfGeneration(String fileName, ObservableList<Person> data) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();

            //adding image to PDF file
            Image logoImage = Image.getInstance(getClass().getResource("/contactsLogo.jpg"));
            logoImage.scalePercent(20);
            logoImage.setAlignment(Element.ALIGN_LEFT);
            document.add(logoImage);

            //adding paragraph to PDF file            
            Font paragraphFont1 = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.NORMAL);
            Paragraph paragraphText1 = new Paragraph("THIS IS EMAILBOOK\n\n\n", paragraphFont1);
            paragraphText1.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphText1);

            //adding table to PDF file
            float[] columnWIdths = {2, 4, 4, 6};
            PdfPTable table = new PdfPTable(columnWIdths);
            table.setWidthPercentage(90);

            // first header - 
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.NORMAL, BaseColor.WHITE);
            PdfPCell headerCell = new PdfPCell(new Phrase("Contact List", headerFont));
            headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setVerticalAlignment(Element.ALIGN_CENTER);
            headerCell.setColspan(4);
            table.addCell(headerCell);

            // second headers
            table.getDefaultCell().setBackgroundColor(new GrayColor(0.8f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Nr.");
            table.addCell("Firstname");
            table.addCell("Lastname");
            table.addCell("Email address");

            // cells
            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

            for (int i = 1; i <= data.size(); i++) {
                Person actualPerson = data.get(i - 1);
                table.addCell(i+".");
                table.addCell(actualPerson.getFirstName());
                table.addCell(actualPerson.getLastName());
                table.addCell(actualPerson.getEmail());
            }

            document.add(table);

            document.close();

        } catch (Exception e) {

        }

    }

}

package com.bharat.pdfcreateowndemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


public class FirstPDFActivity extends AppCompatActivity {

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD); // Set of font family alrady present with itextPdf library.
    private static Font redFont;
    private static Font blackFont;
    private static Font blackFont_big;
    private static Font greenFont;

    static {
        redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.BOLD, BaseColor.RED);
        blackFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.NORMAL, BaseColor.BLACK);
        blackFont_big = new Font(Font.FontFamily.TIMES_ROMAN, 14,
                Font.NORMAL, BaseColor.BLACK);
        greenFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.BOLD, BaseColor.GREEN);
    }

    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public static final String DEST = "/quick_brown_fox_PDFUA.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_pdf_create);
    }
    public void PrintDocument(String dest) throws IOException, IOException {
        try {

//            Document document = new Document();
            Document document = new Document(PageSize.PENGUIN_SMALL_PAPERBACK, 10f, 10f, 100f, 0f);//PENGUIN_SMALL_PAPERBACK used to set the paper size
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            addContent(document);
            document.close();

            File file = new File(dest);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {

        Paragraph preface = new Paragraph();
        // We add one empty line
      //  addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Transcation Report ( 30/11/2019 -3/12/2019)", blackFont_big));

       // addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Report Ggenerated At:  " +  new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                blackFont));
       // addEmptyLine(preface, 1);
        preface.add(new Paragraph(
                "Number of Transcation:  9",
                blackFont));

        preface.add(new Paragraph(
                "Total Credit: 2000",
                greenFont));
        preface.add(new Paragraph(
                "Total Debit: 2000",
                redFont));
        preface.add(new Paragraph(
                "Number of Transcation:  9",
                smallBold));
       // addEmptyLine(preface, 8);

        preface.add(new Paragraph(
                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("First Chapter", catFont);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list
        createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);

        // Next section
        anchor = new Anchor("Second Chapter", catFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(catPart);

    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }
    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    public void createPdf(View view) {
        String root = Environment.getExternalStorageDirectory().toString()+"/MyPDFDoc";
        final File dir = new File(root);
        if (!dir.exists())
            dir.mkdirs();
        try {
            PrintDocument(dir.getPath()+DEST);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

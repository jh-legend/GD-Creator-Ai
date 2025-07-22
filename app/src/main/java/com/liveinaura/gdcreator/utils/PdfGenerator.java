package com.liveinaura.gdcreator.utils;

import android.content.Context;
import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfGenerator {
    public static void generatePdf(Context context, String gdText) throws IOException, DocumentException {
        // Create a directory for the app's PDFs
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "GD-Creator");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Create a new PDF document
        File file = new File(dir, "GD_" + System.currentTimeMillis() + ".pdf");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        // Add the Bengali font
        BaseFont bf = BaseFont.createFont("assets/fonts/kalpurush.ttf", BaseFont.IDENTITY_H, Base.EMBEDDED);
        Font font = new Font(bf, 12);

        // Add the GD text to the document
        document.add(new Paragraph(gdText, font));

        // Close the document
        document.close();
    }
}

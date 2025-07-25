package com.liveinaura.gdcreator.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.IOException;
import java.io.OutputStream;

public class PdfGenerator {

    public static void generatePdf(Context context, String gdText) throws IOException {
        // Step 1: Define file metadata
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "GD_" + System.currentTimeMillis() + ".pdf");
        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/GD-Creator");
        }

        // Step 2: Get Uri to write file
        Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);

        // Step 3: Write PDF using iText7
        try (OutputStream outputStream = context.getContentResolver().openOutputStream(uri)) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Step 4: Load Bengali font
            PdfFont font = PdfFontFactory.createFont("assets/fonts/kalpurush.ttf", PdfEncodings.IDENTITY_H, true);

            // Step 5: Create paragraph and add to PDF
            Paragraph paragraph = new Paragraph(gdText)
                    .setFont(font)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.JUSTIFIED);

            document.add(paragraph);
            document.close();
        }
    }
}

package com.liveinaura.gdcreator.utils;

import android.content.Context;
import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

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
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "GD_" + System.currentTimeMillis() + ".pdf");
        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/GD-Creator");
        }

        Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);

        try (OutputStream outputStream = context.getContentResolver().openOutputStream(uri)) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add the Bengali font
            PdfFont font = PdfFontFactory.createFont("assets/fonts/kalpurush.ttf", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

            // Add the GD text to the document
            Paragraph paragraph = new Paragraph(gdText).setFont(font).setTextAlignment(TextAlignment.JUSTIFIED);
            document.add(paragraph);

            document.close();
        }
    }
}

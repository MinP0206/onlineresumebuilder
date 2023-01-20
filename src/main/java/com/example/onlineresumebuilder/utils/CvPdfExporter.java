package com.example.onlineresumebuilder.utils;


import com.example.onlineresumebuilder.enums.ResponseCode;
import com.example.onlineresumebuilder.exception.InternalException;
import com.example.onlineresumebuilder.model.Cv;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;


@Slf4j
@RequiredArgsConstructor
public class CvPdfExporter {

    private final Cv cv;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
        Font.BOLD);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
        Font.NORMAL);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
        Font.BOLD);

    private static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    private void addTitlePage(Document document)
        throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);

        preface.add(new Paragraph(
            "Information\n",
            smallBold));
        preface.add(new Paragraph(
            cv.getInformation(),
            subFont));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph(
            "Summary\n",
            smallBold));
        preface.add(new Paragraph(
            cv.getSummary(),
            subFont));
        addEmptyLine(preface, 3);

        preface.add(new Paragraph(
            "Education\n",
            smallBold));
        preface.add(new Paragraph(
            cv.getEducation(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            subFont));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph(
            "Experiences\n",
            smallBold));
        preface.add(new Paragraph(
            cv.getExperiences(),
            subFont));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph(
            "Skills\n",
            smallBold));
        preface.add(new Paragraph(
            cv.getSkills(),
            smallBold));
        addEmptyLine(preface, 3);


        document.add(preface);
        // Start a new page
        document.newPage();
    }
    @SneakyThrows
    public byte[] export() {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, bos);
            document.open();

            Font font = new Font(BaseFont.createFont("vietFontsWeb_ttf/vuArial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            Paragraph p = new Paragraph("MY CV", catFont);
            p.setAlignment(Paragraph.ALIGN_CENTER);


            document.add(p);
            addMetaData(document);
            addTitlePage(document);
//            PdfPTable table = new PdfPTable(7);
//            table.setWidthPercentage(100f);
//            table.setSpacingBefore(10);

//            writeTableHeader(table);
     
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new InternalException(ResponseCode.DOWNLOAD_FILE_FAIL);
        } finally {
            document.close();
        }
        return bos.toByteArray();
    }


}


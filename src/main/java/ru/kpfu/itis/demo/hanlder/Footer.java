package ru.kpfu.itis.demo.hanlder;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

public class Footer implements IEventHandler {

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdf = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = page.getPageSize();
        PdfCanvas pdfCanvas = new PdfCanvas(
                page.getLastContentStream(), page.getResources(), pdf);
        Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
        Paragraph paragraph = new Paragraph().setFontSize(6).add(String.valueOf(pdf.getPageNumber(page)))
                .setTextAlignment(TextAlignment.CENTER)
                .setFixedPosition(pageSize.getRight() - 50, pageSize.getBottom() + 20, 20);
//        float x = pageSize.getRight() - 30;
//        float y = pageSize.getBottom();
//        canvas.showTextAligned(
//                String.valueOf(pdf.getPageNumber(page)),
//                x, y, TextAlignment.CENTER);
        canvas.add(paragraph);

        canvas.close();
    }
}

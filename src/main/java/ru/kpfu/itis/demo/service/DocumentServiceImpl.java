package ru.kpfu.itis.demo.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.demo.hanlder.Footer;
import ru.kpfu.itis.demo.model.DocumentKit;
import ru.kpfu.itis.demo.model.MyDocument;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;

@Service
public class DocumentServiceImpl implements DocumentService {

    private static final String PDF_PATH = "C:/demo/itext.pdf";
    private static final String IMAGE_PATH = "C:/demo/src/main/resources/static/KFU.jpg";
    private static final String SHRIFT_PATH = "C:/demo/shrift.otf";

    @Override
    public void generatePdfDocument(DocumentKit doc) throws FileNotFoundException, MalformedURLException {
        Document document = createDocument();
        createFont(document);
        for (int i = 0; i < doc.getMyDocumentList().size(); i++) {
            createImage(document);
            createMainParagraph(document, doc.getMyDocumentList().get(i));
            createTable(document, doc.getMyDocumentList().get(i));
            if (i + 1 != doc.getMyDocumentList().size()) {
                AreaBreak aB = new AreaBreak();
                aB.setPageNumber(0);
//                aB.setPageNumber(1);
                document.add(aB);
//                document = createDocument();
            }
        }
        document.close();
    }

    //Генерируем основные строки таблицы
    private void generateCell(Table table, String str, int i) {
        if (i % 2 != 0) {
            table.addCell(new Cell().add(new Paragraph(str)).setFontSize(7).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        } else {
            table.addCell(new Cell().add(new Paragraph(str)).setFontSize(7).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setBackgroundColor(ColorConstants.GRAY));
        }
    }

    //Генерируем строку заголовков таблицы
    private void generateHeaderCell(Table table, String str) {
        table.addHeaderCell(new Cell()
                .setHeight(35)
                .add(new Paragraph(str)).setFontSize(7)
                .setTextAlignment(TextAlignment.CENTER).setBold()
                .setVerticalAlignment(VerticalAlignment.MIDDLE));
    }

    private void createImage(Document document) throws MalformedURLException {
        //Создаём картинку для пдф документа
        ImageData data = ImageDataFactory.create(IMAGE_PATH);
        Image img = new Image(data);
        img.setWidth(100).setFixedPosition(15, 740);
        document.add(img);
    }

    private void createFont(Document document) {
        //Создаём и добавляем собственный фонт для пдф документа(который поддерживает русский язык)
        FontSet myFont = new FontSet();
        myFont.addFont(SHRIFT_PATH);
        document.setFontProvider(new FontProvider(myFont));
        document.setProperty(Property.FONT, new String[]{"Danil"});
    }

    private Document createDocument() throws FileNotFoundException {
        //Создаём объект документа
        PdfWriter writer = new PdfWriter(PDF_PATH);
        PdfDocument pdfDoc = new PdfDocument(writer);
        //Добавляем ивент хэндлер, который срабатывает на момент, когда пдф страница достигает конца
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new Footer());
        return new Document(pdfDoc);
    }

    private void createMainParagraph(Document document, MyDocument doc) {
        //Создаём основные поля для генерации пдф
        String number = "Подготовленный отчет по данным\n" +
                "по № ";

        String key = "Институт:" + "\n"
                + "Логин:" + "\n"
                + "Количество студентов: " + "\n"
                + "Номер отчёта: " + "\n"
                + "Тип отчёта: " + "\n";
        String value = doc.getInstitute() + "\n"
                + doc.getLogin() + "\n"
                + doc.getCountStudent() + "\n"
                + doc.getNumberReport() + "\n"
                + doc.getTypeReport() + "\n";
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        String allParticipant = "Перечень участников конференции " + dateFormat.format(doc.getDate()) + ":";

        //Создаём параграфы из полей, созданных выше
        Paragraph pNumber = new Paragraph().setFontSize(9).add(number).setFixedPosition(400, 770, 200);
        Paragraph pNumber2 = new Paragraph().setFontSize(9).add("" + doc.getNumber()).setFixedPosition(425, 771, 200).setBold();
        Paragraph pInstitut = new Paragraph().setFontSize(9).add(key).setFixedPosition(30, 640, 300);
        Paragraph pIns = new Paragraph().setFontSize(9).add(value).setFixedPosition(140, 640, 300);
        Paragraph pAllParticipant = new Paragraph().setFontSize(9).add(allParticipant).setFixedPosition(30, 610, 300);

        document.add(pNumber);
        document.add(pNumber2);
        document.add(pInstitut);
        document.add(pIns);
        document.add(pAllParticipant);

    }

    private void createTable(Document document, MyDocument doc) {
        //Создаём таблицу
        float[] pointColumnWidths = {60F, 60F, 60F, 150F, 150F, 60F};
        Table table = new Table(pointColumnWidths);
        SimpleDateFormat dateFormatTable = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        //Генерируем заголовки таблицы
        generateHeaderCell(table, "Сформирован");
        generateHeaderCell(table, "Оформлен");
        generateHeaderCell(table, "Зачислил");
        generateHeaderCell(table, "Комментарий");
        generateHeaderCell(table, "ФИО, Должность");
        generateHeaderCell(table, "IP-адрес");

        //Генерируем основные строки таблицы
        for (int i = 0; i < doc.getParticipantList().size(); i++) {
            generateCell(table, dateFormatTable.format(doc.getParticipantList().get(i).getDateFormed()), i);
            generateCell(table, dateFormatTable.format(doc.getParticipantList().get(i).getDateDecorated()), i);
            generateCell(table, dateFormatTable.format(doc.getParticipantList().get(i).getDateEnrolled()), i);
            generateCell(table, doc.getParticipantList().get(i).getComment(), i);
            generateCell(table, doc.getParticipantList().get(i).getFio() + "\n" + doc.getParticipantList().get(i).getPosition(), i);
            generateCell(table, doc.getParticipantList().get(i).getIp(), i);
        }

        table.setMarginTop(225);

        document.add(table);

        //Добавляем последний элемент после таблицы
        String reference = "Примечание: время указано в часовом поясе MSK (UTC+3) в соответствии с системными часами сервера или АРМ.";
        Paragraph pReference = new Paragraph().setFontSize(8).add(reference);
        document.add(pReference);

    }
}

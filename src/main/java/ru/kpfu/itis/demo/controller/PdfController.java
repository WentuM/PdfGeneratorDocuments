package ru.kpfu.itis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.demo.model.DocumentKit;
import ru.kpfu.itis.demo.service.DocumentService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class PdfController {

    @Autowired
    public DocumentService documentService;

    @PostMapping(path = "/doc",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public void generatePdf(@RequestBody @Valid DocumentKit doc) throws IOException {

        documentService.generatePdfDocument(doc);
    }

}

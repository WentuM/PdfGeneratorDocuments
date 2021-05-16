package ru.kpfu.itis.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kpfu.itis.demo.model.DocumentKit;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public interface DocumentService {

    void generatePdfDocument(@RequestBody DocumentKit doc)
            throws FileNotFoundException, MalformedURLException;
}

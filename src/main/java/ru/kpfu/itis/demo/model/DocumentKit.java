package ru.kpfu.itis.demo.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DocumentKit {

    @Valid
    @NotNull
    private List<MyDocument> myDocumentList;
}

package ru.kpfu.itis.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class MyDocument {
    @NotBlank
    private String number;
    @NotBlank
    private String institute;
    @NotBlank
    private String login;
    @NotNull
    private Integer countStudent;
    @NotNull
    private Integer numberReport;
    @NotBlank
    private String typeReport;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd.MM.yyyy")
    private Date date;
    @Valid
    @NotNull
    private List<Participant> participantList;
}

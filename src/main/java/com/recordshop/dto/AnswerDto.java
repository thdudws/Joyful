package com.recordshop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class AnswerDto {

    private Long id;

    private String answer;

    private Date inquiryRegDate;
}

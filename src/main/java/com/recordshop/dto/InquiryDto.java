package com.recordshop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class InquiryDto {
    private Long id;

    private String title;

    private String content;

    private Date inquiryRegDate;

    private Date inquiryUpdateDate;

    private String status;
}

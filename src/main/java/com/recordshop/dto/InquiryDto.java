package com.recordshop.dto;

import com.recordshop.entity.Inquiry;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Getter @Setter
public class InquiryDto {
    private Long id;

    private String title;

    private String content;

    private String status;

    private String memberNickName;

    private AnswerDto answer;


    public InquiryDto(Inquiry inquiry) {
        this.id = inquiry.getId();
        this.title = inquiry.getTitle();
        this.content = inquiry.getContent();
        this.memberNickName = inquiry.getMember() != null ? inquiry.getMember().getNickName() : "알 수 없음";

        this.status = inquiry.getAnswerStatus() != null ? inquiry.getAnswerStatus().name() : "상태 없음";

        if (inquiry.getAnswer() != null) {
            this.answer = new AnswerDto(inquiry.getAnswer());
        }
    }
}

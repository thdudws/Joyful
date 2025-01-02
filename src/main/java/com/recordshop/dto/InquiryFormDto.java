package com.recordshop.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class InquiryFormDto {

    private Long id;

    @NotBlank(message = "제목은 필수 입력사항 입니다.")
    private String title;

    @Lob
    @NotBlank(message = "문의 내용은 필수 입력사항 입니다.")
    private String content;

    //문의글 저장 후 수정할 때 문의글 이미지 정보저장
    private List<InquiryImgDto> inquiryImgDtoList = new ArrayList<>();

    //문의글의 이미지 아이디 저장
    private List<Long> inquiryImgIds = new ArrayList<>();
}

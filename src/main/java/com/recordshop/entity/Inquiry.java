package com.recordshop.entity;

import com.recordshop.constant.AnswerStatus;
import com.recordshop.dto.InquiryFormDto;
import com.recordshop.dto.InquiryModifyFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "inquiry")
@Getter @Setter @ToString
public class Inquiry extends BaseEntity {

    @Id
    @Column(name = "inquiry_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnswerStatus answerStatus = AnswerStatus.WAITING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Inquiry createInquiry(InquiryFormDto inquiryFormDto) {
        Inquiry inquiry = new Inquiry();
        inquiry.setTitle(inquiryFormDto.getTitle());
        inquiry.setContent(inquiryFormDto.getContent());
        inquiry.setAnswerStatus(AnswerStatus.WAITING);
        return inquiry;
    }

    public void modifyInquiry(InquiryModifyFormDto inquiryModifyFormDto) {
        if (inquiryModifyFormDto.getTitle() != null && !inquiryModifyFormDto.getTitle().isEmpty()) {
            this.title = inquiryModifyFormDto.getTitle();
        }

        if (inquiryModifyFormDto.getContent() != null && !inquiryModifyFormDto.getContent().isEmpty()) {
            this.content = inquiryModifyFormDto.getContent();
        }
    }
}

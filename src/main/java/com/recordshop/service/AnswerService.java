package com.recordshop.service;

import com.recordshop.constant.AnswerStatus;
import com.recordshop.entity.Answer;
import com.recordshop.entity.Inquiry;
import com.recordshop.entity.Member;
import com.recordshop.repository.AnswerRepository;
import com.recordshop.repository.InquiryRepository;
import com.recordshop.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final InquiryRepository inquiryRepository;
    private final MemberRepository memberRepository;
    private final InquiryService inquiryService;

    //답글 저장
    public void saveAnswer(Long inquiryId, String answer) {
        Inquiry inquiry = inquiryService.findById(inquiryId);
        Answer aw = new Answer();

        aw.setInquiry(inquiry);
        aw.setAnswer(answer);
        answerRepository.save(aw);
    }

    //특정 한 게시물에 대한 답변 조회
    public List<Answer> getAnswersInquiryId(Long inquiryId) {
        return answerRepository.findByInquiryId(inquiryId);
    }


}

package com.recordshop.service;

import com.recordshop.constant.AnswerStatus;
import com.recordshop.entity.Answer;
import com.recordshop.entity.Inquiry;
import com.recordshop.entity.Member;
import com.recordshop.repository.AnswerRepository;
import com.recordshop.repository.InquiryRepository;
import com.recordshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final InquiryRepository inquiryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createAnswer(Long inquiryId, String answerContent, Long memberId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId).
                orElseThrow(() -> new IllegalArgumentException("해당 문의글은 찾을 수 없습니다."));

        Member member = memberRepository.findById(memberId).
                orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        //Answer 객체 생성
        Answer answer = new Answer();
        answer.setAnswer(answerContent);
        answer.setInquiry(inquiry);
        answer.setMember(member);

        Answer saved = answerRepository.save(answer);

        inquiry.setAnswerStatus(AnswerStatus.COMPLETED);  // 예: 답변이 달린 문의글은 '완료' 상태로 변경
        inquiryRepository.save(inquiry);

        return saved.getId();
    }


}

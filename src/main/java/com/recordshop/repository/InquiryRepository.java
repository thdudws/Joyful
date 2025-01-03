package com.recordshop.repository;

import com.recordshop.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    // 회원 이메일로 해당 회원이 작성한 문의글 조회
    Page<Inquiry> findByMemberEmail(String email, Pageable pageable);

    // 관리자용: 모든 문의글 조회
    Page<Inquiry> findAll(Pageable pageable);

    String id(Long id);

    List<Inquiry> findByMemberId(Long id, Pageable pageable);

    Inquiry findByIdAndMemberEmail(Long id, String email);

    List<Inquiry> findByMemberEmail(String email);

    Optional<Inquiry> findById(Long inqquiryId);
}

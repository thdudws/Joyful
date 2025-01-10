package com.recordshop.repository;

import com.recordshop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Member findByPhoneNumber(String phoneNumber);
    Member findByUsername(String username);




}
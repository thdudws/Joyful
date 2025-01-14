package com.recordshop.repository;

import com.recordshop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, String> {


<<<<<<< HEAD
    Member findByPhoneNumber(String phoneNumber);
    Member findByUsername(String username);

=======

    Member findByPhoneNumber(String phoneNumber);
    Member findByUsername(String username);

>>>>>>> 34e881db6c31b3223564fe05e792f8077de29c95



}

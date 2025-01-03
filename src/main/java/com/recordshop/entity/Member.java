package com.recordshop.entity;

import com.recordshop.constant.Role;
import com.recordshop.dto.MemberFormDto;
import com.recordshop.dto.MemberModifyFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nickName;

    @Column(unique = true)
    private String email;

    private String password;

    private String phoneNumber;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setNickName(memberFormDto.getNickName());
        member.setEmail(memberFormDto.getEmail());
        member.setPassword(passwordEncoder.encode(memberFormDto.getPassword()));
        member.setPhoneNumber(memberFormDto.getPhoneNumber());
        member.setAddress(memberFormDto.getAddress());
        member.setRole(Role.USER);
        return member;


    }

    //회원정보 수정
    public void modifyMember(MemberModifyFormDto memberModifyFormDto, PasswordEncoder passwordEncoder) {
        if (memberModifyFormDto.getNickName() != null && !memberModifyFormDto.getNickName().isEmpty()) {
            this.nickName = memberModifyFormDto.getNickName();
        }
        if (memberModifyFormDto.getPassword() != null && !memberModifyFormDto.getPassword().isEmpty()) {
            this.password = passwordEncoder.encode(memberModifyFormDto.getPassword());
        }
        if (memberModifyFormDto.getPhoneNumber() != null && !memberModifyFormDto.getPhoneNumber().isEmpty()) {
            this.phoneNumber = memberModifyFormDto.getPhoneNumber();
        }
        if (memberModifyFormDto.getAddress() != null && !memberModifyFormDto.getAddress().isEmpty()) {
            this.address = memberModifyFormDto.getAddress();
        }
        System.out.println("Modified Member: " + this);
    }
}

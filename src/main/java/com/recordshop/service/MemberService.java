
package com.recordshop.service;


import com.recordshop.dto.MemberModifyFormDto;
import com.recordshop.entity.Member;
import com.recordshop.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletResponse httpServletResponse;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }       //end saveMember

    public void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }       //end validateDuplicateMember


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member findByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber);
    }

    //회원 수정
    @Transactional
    public void memberUpdate(Long memberId, MemberModifyFormDto memberModifyFormDto) {
        Member updateMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("회원 정보를 찾을 수 없습니다."));

        updateMember.modifyMember(memberModifyFormDto, passwordEncoder);

        memberRepository.save(updateMember);
        System.out.println("updateMember : " + updateMember);

        memberRepository.save(updateMember);  // save 호출 시점에 반영
    }

    //회원삭제
    @Transactional
    public void memberDelete(Member member,HttpServletRequest request, HttpServletResponse response) {

        memberRepository.delete(member); // 이메일로 찾은 회원을 삭제

        // 세션 무효화
        request.getSession().invalidate();

        // 쿠키 삭제 (예: JSESSIONID, remember-me 쿠키 등)
        deleteCookie("JSESSIONID",response);   // 세션 쿠키 삭제
    }

    // 쿠키 삭제 메서드
    private void deleteCookie(String cookieName,HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");  // 도메인 전체에서 쿠키 삭제
        cookie.setMaxAge(0);  // 만료 시간을 0으로 설정하여 쿠키 삭제
        response.addCookie(cookie); // 쿠키를 응답에 추가
    }

    @Transactional
    public void updateAddressOnly(Long memberId, MemberModifyFormDto memberModifyFormDto) {
        // 회원 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("회원 정보를 찾을 수 없습니다."));

        boolean isUpdated = false; // 업데이트 여부를 추적하기 위한 플래그

        // 배송지 정보 수정
        if (memberModifyFormDto.getNickName() != null) {
            member.setNickName(memberModifyFormDto.getNickName());
            isUpdated = true;
        }
        if (memberModifyFormDto.getPhoneNumber() != null) {
            member.setPhoneNumber(memberModifyFormDto.getPhoneNumber());
            isUpdated = true;
        }
        if (memberModifyFormDto.getAddress() != null) {
            member.setAddress(memberModifyFormDto.getAddress());
            isUpdated = true;
        }

        if (!isUpdated) {
            throw new IllegalStateException("업데이트할 정보가 없습니다."); // 모든 필드가 null일 경우 예외 처리
        }

        // 변경된 사항을 DB에 저장
        memberRepository.save(member);

        log.info("Updated Address Member: " + member);
    }


}

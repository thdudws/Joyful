package com.recordshop.controller;

import com.recordshop.constant.Role;
import com.recordshop.detail.PrincipalDetails;
import com.recordshop.dto.MemberFormDto;
import com.recordshop.dto.MemberModifyFormDto;
import com.recordshop.entity.Member;
import com.recordshop.repository.MemberRepository;
import com.recordshop.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @GetMapping(value="/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());

        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";
    }       //end newMember

    /*@PostMapping(value = "/new")
    public String newMember(Member member) {
        String role = member.setRole(Role.USER);
        String username = member.getUsername();
        String rewPassword =member.getPassword();
        String encodedPassword = passwordEncoder.encode(rewPassword);
        member.setPassword(encodedPassword);
        memberRepository.save(member);
        return "redirect:/";
    }       //end newMember*/

    @GetMapping(value = "/login")
    public String loginMember() {

        return "/member/memberLoginForm";
    }



    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/myPage")
    public String myPage(Model model) {

        return "/member/myPage";
    }

    //회원 정보 수정
    @GetMapping(value = "/modify")
    public String memberModify(Model model, Authentication authentication) {
        String userName = authentication.getName();
        Member member = memberService.findByUserName(userName);

        MemberModifyFormDto memberModifyFormDto = new MemberModifyFormDto();
        memberModifyFormDto.setNickName(member.getNickName());
        memberModifyFormDto.setPhoneNumber(member.getPhoneNumber());
        memberModifyFormDto.setAddress(member.getAddress());

        model.addAttribute("memberModifyFormDto", memberModifyFormDto);
        return "member/memberModifyForm";
    }

    @PostMapping(value = "/modify")
    public String memberModify(@Valid MemberModifyFormDto memberModifyFormDto, BindingResult bindingResult,
                               Model model, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "member/memberModifyForm";
        }

        try {
            String currentEmail = authentication.getName();
            Member currentMember = memberService.findByEmail(currentEmail);

            memberService.memberUpdate(currentMember.getUsername(), memberModifyFormDto);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberModifyForm";
        }
        return "redirect:/members/myPage";
    }


    // 회원 탈퇴 메서드
    @PostMapping("/delete")
    public String deleteMember(@RequestParam String phoneNumber, HttpServletRequest request, HttpServletResponse response, Model model) {

        log.info(phoneNumber);

        //Member member = memberRepository.findByPhone(phoneNumber);
        Member member = memberService.findByPhoneNumber(phoneNumber);

        try {
            memberService.memberDelete(member, request, response);
            return "redirect:/";  // 탈퇴 후 리다이렉트
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "member/memberModifyForm";
        }
    }

    @GetMapping(value = "/contact")
    public String contact(Model model) {
        return "/member/contact";
    }


<<<<<<< HEAD
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails ) {
        System.out.println(principalDetails.getMember());

        return "member";
    }
=======
>>>>>>> 3b89c6e73e9b244ab246f93d6deeaea07607ca5a
}

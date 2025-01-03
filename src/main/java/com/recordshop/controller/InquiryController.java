package com.recordshop.controller;

import com.recordshop.dto.AnswerFormDto;
import com.recordshop.dto.InquiryDto;
import com.recordshop.dto.InquiryFormDto;
import com.recordshop.dto.InquiryModifyFormDto;
import com.recordshop.entity.Inquiry;
import com.recordshop.entity.Member;
import com.recordshop.repository.MemberRepository;
import com.recordshop.service.AnswerService;
import com.recordshop.service.InquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequestMapping("/inquiries")
@Controller
@RequiredArgsConstructor
@Log4j2
public class InquiryController {

    private final InquiryService inquiryService;
    private final MemberRepository memberRepository;
    private final AnswerService answerService;

    @GetMapping(value = {"/admin/list", "/admin/list/{page}"})
    public String adminList(@PathVariable("page") Optional<Integer> page, Model model) {

        //한 페이지당 10개의 문의글 보여주기
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<Inquiry> allInquiries = inquiryService.getAllInquiry(pageable);

        model.addAttribute("allInquiries", allInquiries);
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", page.orElse(0));

        return "inquiry/inquiryAdminList";
    }

    @GetMapping(value = {"/list", "/list/{page}"})
    public String list(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {

        String email = principal.getName();
        //한 페이지당 10개의 문의글 보여주기
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<Inquiry> userInquiries = inquiryService.getUserInquiry(pageable, email);

        model.addAttribute("inquiryList", userInquiries);
        model.addAttribute("maxPage", 5);
        model.addAttribute("currentPage", page.orElse(0));

        return "inquiry/inquiryList";
    }

    @GetMapping(value = "/new")
    public String inquiryForm(Model model) {

        model.addAttribute("inquiryFormDto", new InquiryFormDto());

        return "inquiry/inquiryForm";
    }

    @PostMapping(value = "/new")
    public String newInquiry(@Valid InquiryFormDto inquiryFormDto, BindingResult bindingResult, Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "inquiry/inquiryForm";
        }

        try {
            String email = principal.getName();

            Inquiry inquiry = Inquiry.createInquiry(inquiryFormDto);
            inquiryService.saveInquiry(inquiry, email);
            log.info("email: " + email);
        } catch (IllegalStateException e) {
            // 오류 메시지 출력
            model.addAttribute("errorMessage", e.getMessage());
            return "inquiry/inquiryForm";
        }

        return "redirect:/inquiries/list";
    }

    //글 상세보기
    @GetMapping(value = "/{inquiryId}")
    public String inquiryDtl(@PathVariable("inquiryId") Long inquiryId, Model model) {
        InquiryDto inquiryFormDto = inquiryService.getInquiryDtl(inquiryId);
        model.addAttribute("inquiry", inquiryFormDto);
        return "inquiry/inquiryDtl";
    }

    //글 수정 하기
    @GetMapping(value = "/modify/{inquiryId}")
    public String inquiryModify(@PathVariable("inquiryId") Long inquiryId, Model model) {

        Inquiry inquiry = inquiryService.findById(inquiryId);

        InquiryFormDto inquiryFormDto = new InquiryFormDto();
        inquiryFormDto.setId(inquiry.getId());  // inquiryId 사용
        inquiryFormDto.setTitle(inquiry.getTitle());
        inquiryFormDto.setContent(inquiry.getContent());

        // 수정 폼을 모델에 담아 전달
        model.addAttribute("inquiryFormDto", inquiryFormDto);

        return "inquiry/inquiryModifyForm"; // 수정 폼 페이지로 이동
    }

    @PostMapping(value = "/modify/{inquiryId}")
    public String inquiryModify(@PathVariable("inquiryId") Long inquiryId,
                                @ModelAttribute InquiryModifyFormDto inquiryModifyFormDto,
                                Authentication authentication) {

        Inquiry inquiry = inquiryService.findById(inquiryId);

        String email = authentication.getName();

        inquiry.setTitle(inquiryModifyFormDto.getTitle());
        inquiry.setContent(inquiryModifyFormDto.getContent());

        inquiryService.saveInquiry(inquiry, email);

        return "redirect:/inquiries/list";
    }

    @DeleteMapping(value = "/modify/{inquiryId}")
    public String deleteInquiry(@PathVariable("inquiryId") Long inquiryId) {
        inquiryService.deleteInquiry(inquiryId);
        return "redirect:/inquiries/list";
    }

    @GetMapping(value = "/answer/{inquiryId}")
    public String inquiryAnswer(@PathVariable("inquiryId") Long inquiryId, Model model) {
        Inquiry inquiry = inquiryService.findById(inquiryId);
        model.addAttribute("inquiry", inquiry);
        model.addAttribute("answerFormDto", new AnswerFormDto());
        return "inquiry/AnswerForm";
    }

    @PostMapping(value = "/answer/{inquiryId}")
    public String inquiryAnswer(@PathVariable("inquiryId") Long inquiryId,
                                @ModelAttribute AnswerFormDto answerFormDto){
        answerService.saveAnswer(inquiryId, answerFormDto.getAnswer());
        return "redirect:/";
    }

}

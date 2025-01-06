package com.recordshop.service;

import com.recordshop.dto.InquiryFormDto;
import com.recordshop.entity.Inquiry;
import com.recordshop.entity.InquiryImg;
import com.recordshop.repository.InquiryImgRepository;
import com.recordshop.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryImgRepository inquiryImgRepository;
    private final InquiryImgService inquiryImgService;

    public Long saveInquiry(InquiryFormDto inquiryFormDto, List<MultipartFile> inquiryImgFileList) throws Exception {

        //문의글 등록
        Inquiry inquiry = new Inquiry();
        inquiryRepository.save(inquiry);

        //이미지 등록
        for (int i=0; i<inquiryImgFileList.size(); i++) {

            InquiryImg inquiryImg = new InquiryImg();
            inquiryImg.setInquiry(inquiry);

            inquiryImgService.saveInquiryImg(inquiryImg,inquiryImgFileList.get(i));
        }

        return inquiry.getId();
    }//end saveInquiry
}

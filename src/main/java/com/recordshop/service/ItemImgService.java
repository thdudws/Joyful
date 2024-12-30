package com.recordshop.service;

import com.recordshop.entity.ItemImg;
import com.recordshop.repository.ItemImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgLocation,oriImgName,itemImgFile.getBytes());
            imgUrl = "/images/itemImg/" + imgName;
        }

        //상품 이미지 정보 저장
        itemImg.updateItemImg(oriImgName,imgName,imgUrl);
        itemImgRepository.save(itemImg);

    }   //end saveItemImg

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {

        if (!itemImgFile.isEmpty()) {
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(()->new EntityNotFoundException(""));

            //기존 이미지 삭제
            if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation+"/"+savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation,oriImgName,itemImgFile.getBytes());
            String imgUrl = "/images/itemImg/" + imgName;
            savedItemImg.updateItemImg(oriImgName,imgName,imgUrl);
        }

    }       //end updateItemImg


}

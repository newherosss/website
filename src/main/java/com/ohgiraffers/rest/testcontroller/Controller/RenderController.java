package com.ohgiraffers.rest.testcontroller.Controller;

import com.ohgiraffers.rest.testcontroller.Service.FileService;
import com.ohgiraffers.rest.testcontroller.repository.DownloadEntity;
import com.ohgiraffers.rest.testcontroller.repository.FileRepository;
import com.ohgiraffers.rest.testcontroller.repository.FilesDTO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@ResponseBody
public class RenderController {
    private final FileService fileService;
    private final FileRepository fileRepository;

    public RenderController(FileService fileService, FileRepository fileRepository) {
        this.fileService = fileService;
        this.fileRepository = fileRepository;
    }


    @GetMapping("/view1")
    public List<FilesDTO> view(Model model) {
        List<FilesDTO> boardDtoList = fileService.getBoardList();
        model.addAttribute("postList", boardDtoList);
        for(FilesDTO data : boardDtoList) {
            System.out.println("forEach 전체 출력: "+data);
        }
        return boardDtoList;
    }




    //   이미지 출력


}

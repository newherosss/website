package com.ohgiraffers.rest.testcontroller.Controller;

import com.ohgiraffers.rest.testcontroller.Service.FileService;
import com.ohgiraffers.rest.testcontroller.repository.DownloadEntity;
import com.ohgiraffers.rest.testcontroller.repository.FileRepository;
import com.ohgiraffers.rest.testcontroller.repository.FilesDTO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class FilesController {
    private final FileService fileService;
    private final FileRepository fileRepository;

    public FilesController(FileService fileService, FileRepository fileRepository) {
        this.fileService = fileService;
        this.fileRepository = fileRepository;
    }


    @GetMapping("/view")
    public String view(Model model) {
        List<FilesDTO> boardDtoList = fileService.getBoardList();
        model.addAttribute("postList", boardDtoList);
        for(FilesDTO data : boardDtoList) {
            System.out.println("forEach 전체 출력: "+data);
        }
        return "view";
    }
    //   이미지 출력
    @GetMapping("/images/{fileId}")
    @ResponseBody
    public Resource downloadImage(@PathVariable("fileId") Long id, Model model) throws IOException {

        DownloadEntity file = fileRepository.findById(id).orElse(null);
        return new UrlResource("file:" + file.getSavedPath());
    }


    @GetMapping("/view2")
    public String view2(Model model) {

        return "home";
    }

}

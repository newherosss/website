package com.ohgiraffers.rest.testcontroller.Service;

import com.ohgiraffers.rest.testcontroller.repository.DownloadEntity;
import com.ohgiraffers.rest.testcontroller.repository.FileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class databasemultipart {
    private String fileDir="C:/test/";
    // private final Path dir = Paths.get("");
    // private String fileDir="/src/main/resources/static/";
    // dir.toAbsolutePath().toString()
    private final FileRepository fileRepository;

    public databasemultipart(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @PostMapping("/test/multipart3")
    public ResponseEntity<Map<String, String>> testMultipart( @RequestParam("files") List<MultipartFile> files) {
        System.out.println(files);
        if (files.isEmpty()) {
            return null;
        }
        files.forEach(file -> {

            System.out.println(file.getContentType());
            System.out.println(file.getOriginalFilename());
            String origName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String extension = origName.substring(origName.lastIndexOf("."));
            String savedName = uuid + extension;
            String savedPath = fileDir + savedName;

            // 파일 엔티티 생성
            DownloadEntity filer = DownloadEntity.builder()
                    .orgNm(origName)
                    .savedNm(savedName)
                    .savedPath(savedPath)
                    .loginname("hi")
                    .build();
            System.out.println(origName);
            System.out.println(savedName);
            System.out.println(savedPath);

            try {
                file.transferTo(new File(savedPath));
                fileRepository.save(filer);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return ResponseEntity.ok(resultMap);
    }

}
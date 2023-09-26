package com.ohgiraffers.rest.testcontroller.Service;

import com.ohgiraffers.rest.testcontroller.repository.DownloadEntity;
import com.ohgiraffers.rest.testcontroller.repository.FileRepository;
import com.ohgiraffers.rest.testcontroller.repository.FilesDTO;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public List<FilesDTO> getBoardList() {
        List<DownloadEntity> PostList = fileRepository.findAll();

        List<FilesDTO> PostDTOList = new ArrayList<>();
        for (DownloadEntity board : PostList) {
            FilesDTO filer = FilesDTO.builder()
                    .id(board.getId())
                    .orgNm(board.getOrgNm())
                    .savedNm(board.getSavedNm())
                    .savedPath(board.getSavedPath())
                    .loginname("hi")
                    .build();

            PostDTOList.add(filer);
        }
        return PostDTOList;
    }
}

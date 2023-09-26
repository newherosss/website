package com.ohgiraffers.rest.testcontroller.repository;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class FilesDTO {
    private Long id;

    private String orgNm;

    private String savedNm;

    private String savedPath;

    private String loginname;
}

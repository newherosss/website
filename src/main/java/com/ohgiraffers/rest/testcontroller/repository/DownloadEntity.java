package com.ohgiraffers.rest.testcontroller.repository;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "files")
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
public class DownloadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "files_id")
    private Long id;

    @Column(name = "fiiles_org_name")
    private String orgNm;

    @Column(name = "files_save_name")
    private String savedNm;

    @Column(name = "files_save_path")
    private String savedPath;

    @Column(name = "files_login_name")
    private String loginname;

}
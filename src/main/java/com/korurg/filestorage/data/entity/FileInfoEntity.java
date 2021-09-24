package com.korurg.filestorage.data.entity;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "files_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "time_create")
    private OffsetDateTime timeCreate;

    @Column(name = "name")
    private String fileName;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "file_id")
    private FileEntity file;

}

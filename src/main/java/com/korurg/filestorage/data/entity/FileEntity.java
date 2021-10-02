package com.korurg.filestorage.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "id")
    private FileInfoEntity fileInfo;
}

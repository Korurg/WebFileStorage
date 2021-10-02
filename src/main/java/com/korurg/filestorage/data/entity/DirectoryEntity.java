package com.korurg.filestorage.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "directory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DirectoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private DirectoryEntity parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "directory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileInfoEntity> files;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DirectoryEntity> childs;
}

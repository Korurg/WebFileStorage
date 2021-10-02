package com.korurg.filestorage.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "directory", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "parent_id"})})
public class DirectoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private DirectoryEntity parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "directory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileInfoEntity> files;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DirectoryEntity> childs;
}

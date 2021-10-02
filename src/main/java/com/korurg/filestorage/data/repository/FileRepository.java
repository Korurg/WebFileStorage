package com.korurg.filestorage.data.repository;

import com.korurg.filestorage.data.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    @Transactional
    @Query(value = "select file from FileEntity file where file.id =:id")
    FileEntity getFileContent(@Param("id") Long id);
}

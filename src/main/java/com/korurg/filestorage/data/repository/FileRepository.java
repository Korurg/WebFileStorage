package com.korurg.filestorage.data.repository;

import com.korurg.filestorage.data.entity.FileInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FileRepository extends JpaRepository<FileInfoEntity, Long> {

    @Transactional
    @Query(nativeQuery = true, value = "select * from files_info order by random() limit 1")
    FileInfoEntity getRandomFile();
}

package com.korurg.filestorage.data.repository;

import com.korurg.filestorage.data.entity.FileInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileInfoEntity, Long> {
    @Query(value = "select * from files_info order by random() limit 1",nativeQuery = true)
    Optional<FileInfoEntity> getRandomFile();

    @Query(value = "select * from files_info where directory_id = :directoryId", nativeQuery = true)
    List<FileInfoEntity> getFilesByDirectoryId(@Param("directoryId") long directoryId);
}

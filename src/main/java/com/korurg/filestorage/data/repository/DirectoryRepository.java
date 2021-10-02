package com.korurg.filestorage.data.repository;

import com.korurg.filestorage.data.entity.DirectoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface DirectoryRepository extends JpaRepository<DirectoryEntity, Long> {

    @Transactional
    default DirectoryEntity getOrCreateDirectory(String directoryName, long parentId) {
        Optional<DirectoryEntity> directory = getDirectoryByNameAndParentId(directoryName, parentId);

        if (directory.isEmpty()) {
            DirectoryEntity parentDirectory = getById(parentId);
            directory = Optional.of(save(DirectoryEntity.builder()
                    .parent(parentDirectory)
                    .name(directoryName)
                    .build()));
        }

        return directory.get();
    }

    default DirectoryEntity getOrCreateDirectory(String directoryName) {
        return getOrCreateDirectory(directoryName, 0);
    }

    @Query(value = "select dir from DirectoryEntity dir where dir.id = 0")
    DirectoryEntity getRoot();

    @Query(value = "select dir from DirectoryEntity dir where dir.parent.id=:parentId and dir.parent.name=:name")
    Optional<DirectoryEntity> getDirectoryByNameAndParentId(@Param("name") String name, @Param("parentId") long parentId);

}

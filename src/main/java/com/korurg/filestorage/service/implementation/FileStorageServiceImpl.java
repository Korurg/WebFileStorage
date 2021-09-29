package com.korurg.filestorage.service.implementation;

import com.korurg.filestorage.data.entity.DirectoryEntity;
import com.korurg.filestorage.data.entity.FileEntity;
import com.korurg.filestorage.data.entity.FileInfoEntity;
import com.korurg.filestorage.data.repository.DirectoryRepository;
import com.korurg.filestorage.data.repository.FileRepository;
import com.korurg.filestorage.service.api.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileRepository fileRepository;
    private final DirectoryRepository directoryRepository;

    @Override
    public List<FileInfoEntity> getFiles(String path) {
        DirectoryEntity directoryEntity = getDirectoryEntityFromPath(path);
        return fileRepository.getFilesByDirectoryId(directoryEntity.getId());
    }


    @Override
    public List<DirectoryEntity> getDirectories(String path) {
        DirectoryEntity directoryEntity = getDirectoryEntityFromPath(path);
        return directoryEntity.getChilds();
    }


    @Override
    public void saveFile(byte[] fileContent) {
        FileEntity file = FileEntity.builder()
                .content(fileContent)
                .build();
        FileInfoEntity fileInfo = FileInfoEntity.builder()
                .file(file)
                .build();
        file.setFileInfo(fileInfo);

        fileRepository.save(fileInfo);
    }

    @Override
    public FileInfoEntity getFile(long id) {
        return fileRepository.getById(id);
    }

    @Override
    public void saveFile(byte[] file, String path, String fileName) throws IOException {
        String[] directories = Arrays.stream(path.split("/"))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        DirectoryEntity directoryEntity = null;
        for (String directory : directories) {
            if (directoryEntity == null) {
                directoryEntity = directoryRepository.getOrCreateDirectory(directory);
            } else {
                directoryEntity = directoryRepository.getOrCreateDirectory(directory, directoryEntity.getId());
            }
        }

        FileEntity fileEntity = FileEntity.builder()
                .content(file)
                .build();
        FileInfoEntity fileInfo = FileInfoEntity.builder()
                .file(fileEntity)
                .fileName(fileName)
                .directory(directoryEntity)
                .timeCreate(OffsetDateTime.now())
                .build();
        fileEntity.setFileInfo(fileInfo);

        fileRepository.save(fileInfo);
    }

    private DirectoryEntity getDirectoryEntityFromPath(String path) {
        String[] directories = Arrays.stream(path.split("/")).filter(s -> !s.isEmpty()).toArray(String[]::new);
        DirectoryEntity directoryEntity = directoryRepository.getRoot();
        for (String directory : directories) {
            directoryEntity = directoryEntity
                    .getChilds()
                    .stream()
                    .filter(dir -> dir.getName().equals(directory))
                    .findAny()
                    .get();
        }
        return directoryEntity;
    }
}

package com.korurg.filestorage.service.implementation;

import com.korurg.filestorage.data.entity.FileEntity;
import com.korurg.filestorage.data.entity.FileInfoEntity;
import com.korurg.filestorage.data.repository.FileRepository;
import com.korurg.filestorage.service.api.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileRepository fileRepository;

    @Override
    public List<String> getFiles(String path) {
        //TODO:
        if (path.equals("/shared")) {
            return List.of("File1");
        }

        if (path.equals("/shared/Directory1")) {
            return List.of("File2");
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getDirectories(String path) {
        //TODO:
        if (path.equals("/shared")) {
            return List.of("Directory1");
        }

        return new ArrayList<>();
    }

    @Override
    public void saveFile(byte[] fileContent) {
        FileEntity file = FileEntity.builder()
                .content(fileContent)
                .build();
        FileInfoEntity fileInfo = FileInfoEntity.builder()
                .file(file)
                .build();
        file.setFileInfoEntity(fileInfo);

        fileRepository.save(fileInfo);
    }

    @Override
    public FileInfoEntity getFile(int id) {
        return fileRepository.getRandomFile();
    }

}

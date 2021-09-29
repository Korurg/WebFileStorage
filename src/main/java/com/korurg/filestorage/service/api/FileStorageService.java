package com.korurg.filestorage.service.api;

import com.korurg.filestorage.data.entity.DirectoryEntity;
import com.korurg.filestorage.data.entity.FileInfoEntity;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {

    public List<FileInfoEntity> getFiles(String path);

    public List<DirectoryEntity> getDirectories(String path);

    void saveFile(byte[] fileContent);


    FileInfoEntity getFile(long id);

    void saveFile(byte[] file, String path, String fileName) throws IOException;
}

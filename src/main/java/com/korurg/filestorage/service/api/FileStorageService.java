package com.korurg.filestorage.service.api;

import com.korurg.filestorage.data.entity.FileInfoEntity;

import java.util.List;

public interface FileStorageService {

    public List<String> getFiles(String path);

    public List<String> getDirectories(String path);

    void saveFile(byte[] fileContent);

    FileInfoEntity getFile(int id);
}

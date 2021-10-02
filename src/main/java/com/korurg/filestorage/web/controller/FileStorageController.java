package com.korurg.filestorage.web.controller;

import com.korurg.filestorage.data.entity.FileInfoEntity;
import com.korurg.filestorage.service.api.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class FileStorageController {


    private final FileStorageService fileStorageService;

    @GetMapping()
    public String redirectToSharedFilesFromMain() {
        return "redirect:/files/shared";
    }

    @GetMapping("files/**")
    public String sharedFiles(HttpServletRequest request, Model model) {
        int prefixLength = "files/".length();
        String path = request.getRequestURI().substring(prefixLength);
        model.addAttribute("directories", fileStorageService.getDirectories(path));
        model.addAttribute("files", fileStorageService.getFiles(path));
        model.addAttribute("prevDirectory", getPreviouslyDirectory(request));
        model.addAttribute("baseUrl", getBaseUrl(request));
        model.addAttribute("pathToUpload", path);

        return "index";
    }

    //TODO:Найти лучший способ получать baseurl
    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        int prefixLength = scheme.length() + 3;
        int firstSlashIndex = request.getRequestURL().substring(prefixLength).indexOf('/');
        return request.getRequestURL().substring(0, firstSlashIndex + prefixLength);
    }

    @GetMapping(value = "files/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> downloadFile(@RequestParam long id) {
        FileInfoEntity fileInfo = fileStorageService.getFile(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileInfo.getFileName());
        return new HttpEntity(fileStorageService.getFileContent(fileInfo), headers);
    }

    //TODO:Запрет на загрузку пустых файлов
    @PostMapping("files/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("path") String path) {
        try {
            fileStorageService.saveFile(file.getBytes(), path, file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/files" + path;
    }

    private String getPreviouslyDirectory(HttpServletRequest request) {
        String url = request.getRequestURI();
        return url.substring(0, url.lastIndexOf('/'));
    }


}

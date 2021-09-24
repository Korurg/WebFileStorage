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
    public String redirectToSharedFiles() {
        return "redirect:/shared";
    }

    @GetMapping("/shared/**")
    public String sharedFiles(HttpServletRequest request, Model model) {
        model.addAttribute("directories", fileStorageService.getDirectories(request.getRequestURI()));
        model.addAttribute("files", fileStorageService.getFiles(request.getRequestURI()));
        model.addAttribute("prevDirectory", getPreviouslyDirectory(request));
        model.addAttribute("pathToUpload", request.getRequestURI());

        return "index";
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> downloadFile(@RequestParam int id) {
        FileInfoEntity fileInfo = fileStorageService.getFile(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileInfo.getFileName());
        return new HttpEntity(fileInfo.getFile().getContent(), headers);
    }

    @PostMapping("/upload")
    public String uploadFile(HttpServletRequest request,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("path") String path) {
        try {
            //TODO:
            fileStorageService.saveFile(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:" + path;
    }

    private String getPreviouslyDirectory(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.substring(0, url.lastIndexOf('/'));
    }


}

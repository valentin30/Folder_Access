package com.example.folderaccess.controller;

import com.example.folderaccess.model.FileListResponse;
import com.example.folderaccess.service.FolderService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@CrossOrigin
@RestController
@RequestMapping("/")
public class FolderController {
    private final String dir;

    private final FolderService folderService;

    public FolderController(FolderService folderService, @Value("${DIR}") String dir) {
        this.folderService = folderService;
        this.dir = dir;
    }

    @GetMapping("**")
    public FileListResponse ListFilesInFolder(HttpServletRequest request, HttpServletResponse response)  {
        String path = getPathFromRequest(request);

        File file = new File(path);

        if(!file.exists()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        if(file.isFile()){
            folderService.downloadResource(file, response);
        }

        return folderService.ListFilesInFolder(file);
    }

    private String getPathFromRequest(HttpServletRequest request){
        try {
            URL url = new URL(request.getRequestURL().toString());
            return dir + url.getPath();
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}

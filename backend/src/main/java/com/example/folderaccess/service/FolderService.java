package com.example.folderaccess.service;

import com.example.folderaccess.model.FileListResponse;
import com.example.folderaccess.model.FolderEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Service
public class FolderService {
    private final String dir;

    public FolderService(@Value("${DIR}") String dir) {
        this.dir = dir;
    }

    public FileListResponse ListFilesInFolder(File file){
        ArrayList<FolderEntry> files = new ArrayList<FolderEntry>();

        for (final File fileEntry : Objects.requireNonNull(file.listFiles())) {
            files.add(new FolderEntry(
                    fileEntry.getName(),
                    fileEntry.getAbsolutePath().substring(dir.length()),
                    fileEntry.isDirectory()
            ));
        }

        return new FileListResponse(file.getAbsolutePath(), files);
    }

    public void downloadResource(File file, HttpServletResponse response)  {
        try {
            String mimeType = getMimeType(file.getName());

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            response.setContentLength((int) file.length());

            FileCopyUtils.copy(getInputStream(file), response.getOutputStream());

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.OK);
    }

    private String getMimeType(String fileName){
        String mimeType = URLConnection.guessContentTypeFromName(fileName);
        if (mimeType == null) {
            return "application/octet-stream";
        }
        return mimeType;
    }

    private InputStream getInputStream(File file) {
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

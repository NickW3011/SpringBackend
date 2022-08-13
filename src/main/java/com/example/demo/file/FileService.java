package com.example.demo.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public File saveFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            File f = new File(fileName, file.getContentType(), file.getBytes());
            return fileRepository.save(f);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<File> getFile(Long id) {
        return fileRepository.findById(id);
    }

    public List<File> getFiles() {
        return fileRepository.findAll();
    }

    public void deleteFile(Long fileId) {
        if (!fileRepository.existsById(fileId)) {
            throw new IllegalStateException("file with id " + fileId + " does not exist");
        }
        fileRepository.deleteById(fileId);
    }
}

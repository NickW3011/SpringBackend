package com.example.demo.file;

import com.example.demo.post.Post;
import com.example.demo.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping(path = "files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private PostService postService;

    @GetMapping
    public String get(Model model) {
        List<File> files = fileService.getFiles();
        model.addAttribute("files", files);
        List<Post> posts = postService.getPosts();
        model.addAttribute("posts", posts);
        model.addAttribute("newPost", new Post());
        return "file";
    }

    @PostMapping(path = "upload")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file: files) {
            fileService.saveFile(file);
        }
        return "redirect:/files";
    }

    @GetMapping(path = "download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileId") Long fileId) {
        File file = fileService.getFile(fileId).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName() + "")
                .body(new ByteArrayResource(file.getData()));
    }

    @GetMapping(path = "/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Long id) {
        fileService.deleteFile(id);
        return "redirect:/files";
    }

    @PostMapping(path = "post")
    public String addPost(@ModelAttribute("newPost") Post post) {
        post.setDate(LocalDateTime.now());
        postService.addPost(post);
        return "redirect:/files";
    }

    @GetMapping(path = "/post/delete/{postId}")
    public String deletePost(@PathVariable("postId") Long id) {
        postService.deletePost(id);
        return "redirect:/files";
    }

}

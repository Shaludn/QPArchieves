package com.qparchives.controller;

import com.qparchives.model.QuestionPaper;
import com.qparchives.service.QuestionPaperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Controller
public class HomeController {

    private final QuestionPaperService questionPaperService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public HomeController(QuestionPaperService questionPaperService) {
        this.questionPaperService = questionPaperService;
    }

    // ================= HOME =================

    @GetMapping("/")
    public String home() {
        return "index";
    }

    // ================= UPLOAD PAGE =================

    @GetMapping("/upload")
    public String upload(HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        return "upload";
    }

    // ================= UPLOAD QUESTION PAPER =================

    @PostMapping("/upload")
    public String uploadQuestionPaper(

            @RequestParam("qpName") String qpName,
            @RequestParam("category") String category,
            @RequestParam("university") String university,
            @RequestParam("subject") String subject,
            @RequestParam("file") MultipartFile file,

            RedirectAttributes redirectAttributes,
            HttpSession session

    ) throws IOException {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        String projectPath = System.getProperty("user.dir");

        File directory = new File(projectPath + File.separator + uploadDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = file.getOriginalFilename();

        File destination = new File(directory, fileName);

        file.transferTo(destination);

        QuestionPaper questionPaper = new QuestionPaper();

        questionPaper.setQpName(qpName);
        questionPaper.setCategory(category);
        questionPaper.setUniversity(university);
        questionPaper.setSubject(subject);
        questionPaper.setFileName(fileName);

        // Store Absolute Path
        questionPaper.setFilePath(destination.getAbsolutePath());

        questionPaper.setUploadDate(LocalDate.now());

        questionPaperService.saveQuestionPaper(questionPaper);

        redirectAttributes.addFlashAttribute(
                "message",
                "Question Paper Uploaded Successfully!"
        );

        return "redirect:/upload";
    }

    // ================= SEARCH =================

    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) String keyword,
            Model model
    ) {

        if (keyword != null && !keyword.trim().isEmpty()) {

            model.addAttribute(
                    "papers",
                    questionPaperService.searchQuestionPapers(keyword)
            );

        }

        return "search";
    }

    // ================= DOWNLOAD =================

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws IOException {

        QuestionPaper paper = questionPaperService.getQuestionPaperById(id);

        if (paper == null) {
            return ResponseEntity.notFound().build();
        }

        Path path = Paths.get(paper.getFilePath());

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + paper.getFileName() + "\"")
                .body(resource);
    }

    // ================= ABOUT =================

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}
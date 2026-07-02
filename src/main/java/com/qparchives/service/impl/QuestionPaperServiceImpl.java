package com.qparchives.service.impl;

import com.qparchives.model.QuestionPaper;
import com.qparchives.repository.QuestionPaperRepository;
import com.qparchives.service.QuestionPaperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionPaperServiceImpl implements QuestionPaperService {

    private final QuestionPaperRepository repository;

    public QuestionPaperServiceImpl(QuestionPaperRepository repository) {
        this.repository = repository;
    }

    @Override
    public QuestionPaper saveQuestionPaper(QuestionPaper questionPaper) {
        return repository.save(questionPaper);
    }

    @Override
    public List<QuestionPaper> getAllQuestionPapers() {
        return repository.findAll();
    }

    @Override
    public QuestionPaper getQuestionPaperById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteQuestionPaper(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<QuestionPaper> searchQuestionPapers(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return repository.findAll();
        }

        return repository
                .findByQpNameContainingIgnoreCaseOrSubjectContainingIgnoreCaseOrUniversityContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword
                );
    }
}
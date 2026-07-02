package com.qparchives.service;

import com.qparchives.model.QuestionPaper;

import java.util.List;

public interface QuestionPaperService {

    QuestionPaper saveQuestionPaper(QuestionPaper questionPaper);

    List<QuestionPaper> getAllQuestionPapers();

    QuestionPaper getQuestionPaperById(Long id);

    void deleteQuestionPaper(Long id);

    List<QuestionPaper> searchQuestionPapers(String keyword);

}
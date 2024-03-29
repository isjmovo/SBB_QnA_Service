package com.ll.exam.sbb.Question;

import com.ll.exam.sbb.exception.DataNotFoundException;
import com.ll.exam.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
  private final QuestionRepository questionRepository;

  public Page<Question> getList(String kw, int page, String sortCode) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createDate"));

    switch (sortCode) {
      case "OLD" -> sorts.add(Sort.Order.asc("id"));
      default -> sorts.add(Sort.Order.desc("id"));
    }
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

    if (kw == null || kw.trim().length() == 0) {
      return questionRepository.findAll(pageable);
    }

    return questionRepository.findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerList_contentContainsOrAnswerList_author_usernameContains(kw, kw, kw, kw, kw, pageable);
  }

  public Question getQuestion(long id) {
    return questionRepository.findById(id).orElseThrow(() -> new DataNotFoundException("no %d question not found".formatted(id)));
  }

  public void create(String subject, String content, SiteUser author) {
    Question q = new Question();
    q.setSubject(subject);
    q.setContent(content);
    q.setAuthor(author);
    q.setCreateDate(LocalDateTime.now());
    questionRepository.save(q);
  }

  public void modify(Question question, String subject, String content) {
    question.setSubject(subject);
    question.setContent(content);
    question.setModifyDate(LocalDateTime.now());
    questionRepository.save(question);
  }

  public void delete(Question question) {
    questionRepository.delete(question);
  }

  public void vote(Question question, SiteUser siteUser) {
    question.getVoter().add(siteUser);
    questionRepository.save(question);
  }

  public void increaseHitCount(long questionId) {
    Optional<Question> optionalQuestion = questionRepository.findById(questionId);

    if (optionalQuestion.isPresent()) {
      Question question = optionalQuestion.get();
      question.setHitCount(question.getHitCount() + 1);
      questionRepository.save(question);
    }
  }
}
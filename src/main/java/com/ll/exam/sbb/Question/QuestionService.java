package com.ll.exam.sbb.Question;

import com.ll.exam.sbb.DataNotFoundException;
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

@Service
@RequiredArgsConstructor
public class QuestionService {
  private final QuestionRepository questionRepository;

  public Page<Question> getList(int page) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createDate"));

    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

    return questionRepository.findAll(pageable);
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
}
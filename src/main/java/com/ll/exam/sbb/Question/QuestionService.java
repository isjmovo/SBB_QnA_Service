package com.ll.exam.sbb.Question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
  private final QuestionRepository questionRepository;

  public List<Question> getList() {
    return questionRepository.findAll();
  }
}
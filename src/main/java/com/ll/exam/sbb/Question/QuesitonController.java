package com.ll.exam.sbb.Question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor  // 생성자 주입
public class QuesitonController {
  private final QuestionRepository questionRepository;

  @GetMapping("/question/list")
  public String list(Model model) {
    List<Question> questionList = questionRepository.findAll();

    model.addAttribute("questionList", questionList);

    return "question_list";
  }
}
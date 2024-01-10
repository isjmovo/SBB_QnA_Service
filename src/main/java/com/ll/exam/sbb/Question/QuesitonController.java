package com.ll.exam.sbb.Question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor  // 생성자 주입
public class QuesitonController {
  private final QuestionService questionService;

  @GetMapping("/question/list")
  public String list(Model model) {
    List<Question> questionList = questionService.getList();

    model.addAttribute("questionList", questionList);

    return "question_list";
  }

  @GetMapping("/question/detail/{id}")
  public String detail(Model model, @PathVariable int id) {
    Question question = questionService.getQuestion(id);

    model.addAttribute("question", question);

    return "question_detail";
  }
}
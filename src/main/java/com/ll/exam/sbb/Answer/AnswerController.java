package com.ll.exam.sbb.Answer;

import com.ll.exam.sbb.Question.Question;
import com.ll.exam.sbb.Question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
  private final QuestionService questionService;
  private final AnswerService answerService;

  @PostMapping("/create/{id}")
  public String createAnswer(Model model, @PathVariable("id") long id, @Valid AnswerForm answerForm, BindingResult bindingResult) {
    Question question = this.questionService.getQuestion(id);

    if (bindingResult.hasErrors()) {
      model.addAttribute("question", question);

      return "question_detail";
    }

    answerService.create(question, answerForm.getContent());

    return "redirect:/question/detail/%d".formatted(id);
  }
}
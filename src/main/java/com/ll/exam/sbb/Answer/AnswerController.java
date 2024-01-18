package com.ll.exam.sbb.Answer;

import com.ll.exam.sbb.Question.Question;
import com.ll.exam.sbb.Question.QuestionService;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
  private final QuestionService questionService;
  private final AnswerService answerService;
  private final UserService userService;

  @PostMapping("/create/{id}")
  public String createAnswer(Principal principal,  Model model, @PathVariable("id") long id, @Valid AnswerForm answerForm, BindingResult bindingResult) {
    Question question = this.questionService.getQuestion(id);

    if (bindingResult.hasErrors()) {
      model.addAttribute("question", question);

      return "question_detail";
    }

    SiteUser siteUser = userService.getUser(principal.getName());

    answerService.create(question, answerForm.getContent(), siteUser);

    return "redirect:/question/detail/%d".formatted(id);
  }
}
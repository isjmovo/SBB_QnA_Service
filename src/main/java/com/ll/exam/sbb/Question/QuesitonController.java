package com.ll.exam.sbb.Question;

import com.ll.exam.sbb.Answer.AnswerForm;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor  // 생성자 주입
@RequestMapping("/question")
public class QuesitonController {
  private final QuestionService questionService;
  private final UserService userService;

  @GetMapping("/list")
  public String list(HttpSession session, Model model, @RequestParam(defaultValue = "0") int page) {
    Object o = session.getAttribute("SPRING_SECURITY_CONTEXT");

    System.out.println(o);

    Page<Question> paging = questionService.getList(page);

    model.addAttribute("paging", paging);

    return "question_list";
  }

  @GetMapping("/detail/{id}")
  public String detail(Model model, @PathVariable long id, AnswerForm answerForm) {
    Question question = questionService.getQuestion(id);

    model.addAttribute("question", question);

    return "question_detail";
  }

  @GetMapping("/create")
  public String questionCreate(QuestionForm questionForm) {
    return "question_form";
  }

  @PostMapping("/create")
  public String questionCreate(Principal principal,  Model model, @Valid QuestionForm questionForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "question_form";
    }

    SiteUser siteUser = userService.getUser(principal.getName());

    questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);

    return "redirect:/question/list";
  }
}
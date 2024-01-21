package com.ll.exam.sbb.Question;

import com.ll.exam.sbb.Answer.AnswerForm;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor  // 생성자 주입
@RequestMapping("/question")
public class QuesitonController {
  private final QuestionService questionService;
  private final UserService userService;

  @GetMapping("/list")
  public String list(String kw, Model model, @RequestParam(defaultValue = "0") int page) {
    Page<Question> paging = questionService.getList(kw, page);

    model.addAttribute("paging", paging);
    model.addAttribute("kw", kw);

    return "question_list";
  }

  @GetMapping("/detail/{id}")
  public String detail(Model model, @PathVariable long id, AnswerForm answerForm) {
    Question question = questionService.getQuestion(id);

    model.addAttribute("question", question);

    return "question_detail";
  }

  @GetMapping("/create")
  @PreAuthorize("isAuthenticated()")
  public String questionCreate(QuestionForm questionForm) {
    return "question_form";
  }

  @PostMapping("/create")
  @PreAuthorize("isAuthenticated()")
  public String questionCreate(Principal principal,  Model model, @Valid QuestionForm questionForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "question_form";
    }

    SiteUser siteUser = userService.getUser(principal.getName());

    questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);

    return "redirect:/question/list";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/modify/{id}")
  public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
    Question question = questionService.getQuestion(id);

    if (!question.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
    }

    questionForm.setSubject(question.getSubject());
    questionForm.setContent(question.getContent());

    return "question_form";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/modify/{id}")
  public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
    if (bindingResult.hasErrors()) {
      return "question_form";
    }

    Question question = questionService.getQuestion(id);

    if (!question.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
    }

    questionService.modify(question, questionForm.getSubject(), questionForm.getContent());

    return String.format("redirect:/question/detail/%s", id);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/delete/{id}")
  public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
    Question question = questionService.getQuestion(id);

    if (!question.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
    }

    questionService.delete(question);

    return "redirect:/";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/vote/{id}")
  public String questionVote(Principal principal, @PathVariable("id") Long id) {
    Question question = questionService.getQuestion(id);
    SiteUser siteUser = userService.getUser(principal.getName());

    questionService.vote(question, siteUser);

    return "redirect:/question/detail/%d".formatted(id);
  }
}
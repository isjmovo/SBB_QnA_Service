package com.ll.exam.sbb.Question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuesitonController {
  @GetMapping("/question/list")
  public String list() {
    return "question_list";
  }
}
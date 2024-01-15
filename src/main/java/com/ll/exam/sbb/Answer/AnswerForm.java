package com.ll.exam.sbb.Answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {
  @NotEmpty(message = "내용은 필수 항목입니다.")
  public String content;
}
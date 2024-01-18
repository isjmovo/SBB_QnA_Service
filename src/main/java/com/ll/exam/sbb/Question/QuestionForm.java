package com.ll.exam.sbb.Question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class QuestionForm {
  @NotEmpty(message = "제목은 필수 항목입니다.")
  @Size(max=200, message = "제목은 200자 이해로 입력해주세요.")
  public String subject;

  @NotEmpty(message = "내용은 필수 항목입니다.")
  public String content;
}
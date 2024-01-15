package com.ll.exam.sbb.Question;

import com.ll.exam.sbb.Answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 200)
  private String subject;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  private List<Answer> answerList = new ArrayList<>();

  public void addAnswer(Answer answer) {
    answer.setQuestion(this);
    getAnswerList().add(answer);
  }
}
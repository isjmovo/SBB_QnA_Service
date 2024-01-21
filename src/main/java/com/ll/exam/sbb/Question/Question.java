package com.ll.exam.sbb.Question;

import com.ll.exam.sbb.Answer.Answer;
import com.ll.exam.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

  @CreatedDate
  private LocalDateTime createDate;
  private LocalDateTime modifyDate;

  @ManyToOne
  private SiteUser author;

  @ManyToMany
  Set<SiteUser> voter;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  private List<Answer> answerList = new ArrayList<>();

  private Integer hitCount = 0;

  public void addAnswer(Answer answer) {
    answer.setQuestion(this);
    getAnswerList().add(answer);
  }
}
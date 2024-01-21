package com.ll.exam.sbb.Answer;

import com.ll.exam.sbb.Question.Question;
import com.ll.exam.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate;
  private LocalDateTime modifyDate;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Question question;

  @ManyToOne
  private SiteUser author;

  @ManyToMany
  Set<SiteUser> voter;
}
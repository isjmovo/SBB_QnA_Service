package com.ll.exam.sbb;

import com.ll.exam.sbb.Answer.Answer;
import com.ll.exam.sbb.Answer.AnswerRepository;
import com.ll.exam.sbb.Question.Question;
import com.ll.exam.sbb.Question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AnswerRepositoryTests {
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	private int lastSampleDataId;

	@BeforeEach
	void beforeEach() {
		clearData();
		createSampleData();
	}

	private void createSampleData() {
		QuestionRepositoryTests.createSampleData(questionRepository);

		Question q = questionRepository.findById(1).get();

		Answer a1 = new Answer();
		a1.setContent("sbb는 질문답변 게시판입니다.");
		a1.setCreateDate(LocalDateTime.now());
		q.addAnswer(a1);

		Answer a2 = new Answer();
		a2.setContent("sbb에서는 주로 스프링 관련 내용을 다룹니다.");
		a2.setCreateDate(LocalDateTime.now());
		q.addAnswer(a2);

		questionRepository.save(q);
	}

	private void clearData() {
		QuestionRepositoryTests.clearData(questionRepository);

		answerRepository.deleteAll();
		answerRepository.truncateTable();
	}

	@Test
	@Transactional
	@Rollback(false)
	void 저장() {
		Question q = questionRepository.findById(2).get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q);
		a.setCreateDate(LocalDateTime.now());
		answerRepository.save(a);
	}

	@Test
	@Transactional
	@Rollback(false)
	void 조회() {
		Answer a = answerRepository.findById(1).get();
		assertThat(a.getContent()).isEqualTo("sbb는 질문답변 게시판입니다.");
	}

	@Test
	@Transactional
	@Rollback(false)
	void 관련된_question_조회() {
		Answer a = answerRepository.findById(1).get();
		Question q = a.getQuestion();

		assertThat(q.getId()).isEqualTo(1);
	}

	@Test
	@Transactional
	@Rollback(false)
	void question으로부터_관련된_질문들_조회() {
//		SELECT * FROM question WHERE id = 1;
		Question q = questionRepository.findById(1).get();

//		SELECT * FROM answer WHERE question_id = 1;
		List<Answer> answerList = q.getAnswerList();

		assertThat(answerList.size()).isEqualTo(2);
		assertThat(answerList.get(0).getContent()).isEqualTo("sbb는 질문답변 게시판입니다.");
	}
}
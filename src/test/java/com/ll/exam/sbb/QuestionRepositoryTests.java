package com.ll.exam.sbb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class QuestionRepositoryTests {
	@Autowired
	private QuestionRepository questionRepository;
	private static int lastSampleDataId;

	@BeforeEach
	void beforeEach() {
		clearData();
		createSampleData();
	}

	void createSampleData() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);

		lastSampleDataId = q2.getId();
	}

	void clearData() {
		questionRepository.disableForeignKeyCheck();
		questionRepository.truncate();
		questionRepository.enableForeignKeyChecks();
	}

	@Test
	void 저장() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);

		assertThat(q1.getId()).isEqualTo(lastSampleDataId + 1);
		assertThat(q2.getId()).isEqualTo(lastSampleDataId + 2);
	}

	@Test
	void 삭제() {
		assertThat(questionRepository.count()).isEqualTo(lastSampleDataId);
		Question q = questionRepository.findById(1).get();
		questionRepository.delete(q);
		assertThat(questionRepository.count()).isEqualTo(lastSampleDataId - 1);
	}
}
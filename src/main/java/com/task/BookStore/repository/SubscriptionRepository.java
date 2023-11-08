package com.task.BookStore.repository;

import com.task.BookStore.models.AuthorEntity;
import com.task.BookStore.models.StudentsEntity;
import com.task.BookStore.models.subscription.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    void deleteByStudentAndAuthor(StudentsEntity student, AuthorEntity author);

    List<SubscriptionEntity> findByStudent(StudentsEntity student);

    List<SubscriptionEntity> findByAuthor(AuthorEntity author);

    // Find a subscription by both student and author
    @Query("SELECT s FROM SubscriptionEntity s WHERE s.student = :student AND s.author = :author")
    SubscriptionEntity findByStudentAndAuthor(
            @Param("student") StudentsEntity student,
            @Param("author") AuthorEntity author
    );
}

package com.si9nal.parker.report.repository;

import com.si9nal.parker.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByUserEmailOrderByCreatedAtDesc(String email);

}

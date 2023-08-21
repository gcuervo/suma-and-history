package com.tenpo.challenge.repository;

import com.tenpo.challenge.model.entity.ApiCallHistory;
import java.time.OffsetDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ApiCallHistoryRepository
    extends PagingAndSortingRepository<ApiCallHistory, Long>, JpaRepository<ApiCallHistory, Long> {
  @Query("""
      SELECT a FROM ApiCallHistory a 
      WHERE (cast(:startDate as date) IS NULL OR a.createdDate >= :startDate) 
      AND (cast(:endDate as date) IS NULL OR a.createdDate <= :endDate)
      """)
  Page<ApiCallHistory> findByCreatedDateBetween(
      @Param("startDate") OffsetDateTime startDate,
      @Param("endDate") OffsetDateTime endDate,
      Pageable pageable
  );

}

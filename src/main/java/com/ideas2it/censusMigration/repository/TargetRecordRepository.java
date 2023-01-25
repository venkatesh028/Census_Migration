package com.ideas2it.censusMigration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideas2it.censusMigration.model.TargetRecord;

public interface TargetRecordRepository extends JpaRepository<TargetRecord, Long> {
}

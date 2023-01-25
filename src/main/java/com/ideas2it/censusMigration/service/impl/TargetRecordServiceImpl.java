package com.ideas2it.censusMigration.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideas2it.censusMigration.model.TargetRecord;
import com.ideas2it.censusMigration.repository.TargetRecordRepository;
import com.ideas2it.censusMigration.service.TargetRecordService;
@Service
public class TargetRecordServiceImpl implements TargetRecordService {
    private final TargetRecordRepository targetRecordRepository;

    public TargetRecordServiceImpl(TargetRecordRepository targetRecordRepository) {
        this.targetRecordRepository = targetRecordRepository;
    }

    @Override
    public List<TargetRecord> addTargetRecord(List<TargetRecord> targetRecords) {
        return targetRecordRepository.saveAll(targetRecords);
    }
}

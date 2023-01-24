package com.ideas2it.censusMigration.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ideas2it.censusMigration.constants.CustomQuery;
import com.ideas2it.censusMigration.model.EhrMapping;

public interface MappingRepository extends JpaRepository<EhrMapping, UUID> {
    @Query(value = CustomQuery.QUERY_TO_GET_SOURCE_FIELDS)
    List<String>getSourceFieldNameBySourceEhrNameAndServiceLineAndTargetEhrName(String sourceEhrName, String serviceLine,
                                                                                   String targetEhrName);

}

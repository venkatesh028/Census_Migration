package com.ideas2it.censusMigration.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ehr_mapping")
public class EhrMapping {

    @Id
    @Column(name = "mapping_id")
    private UUID mappingId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "source_ehr_name")
    private String sourceEhrName;

    @Column(name = "target_ehr_name")
    private String targetEhrName;

    @Column(name = "service_line")
    private String serviceLine;

    @Column(name = "target_process_name")
    private String targetProcessName;

    @Column(name = "target_sheet_name")
    private String targetSheetName;

    @Column(name = "target_field_name")
    private String targetFieldName;

    @Column(name = "target_field_type")
    private String targetFieldType;

    @Column(name = "target_field_format")
    private String targetFieldFormat;

    @Column(name = "is_target_field_mandatory")
    @ColumnTransformer(
            read = "is_target_field_mandatory::text",
            write = "?::text")
    private String isTargetFieldMandatory;

    @Column(name = "source_file_name")
    private String sourceFileName;

    @Column(name = "source_sheet_name")
    private String sourceSheetName;

    @Column(name = "source_field_name")
    private String sourceFieldName;

    @Column(name = "source_field_type")
    private String sourceFieldType;

    @Column(name = "source_field_format")
    private String sourceFieldFormat;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_on")
    private Timestamp modifiedOn;

    @Column(name = "modified_by")
    private String modifiedBy;

}

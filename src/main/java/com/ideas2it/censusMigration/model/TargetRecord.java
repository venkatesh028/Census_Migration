package com.ideas2it.censusMigration.model;

import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;

import com.ideas2it.censusMigration.utils.HashMapConverter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class TargetRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sourceEhrName;
    private String serviceLine;
    @Column(columnDefinition = "json")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> patientAttributes;

}

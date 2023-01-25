package com.ideas2it.censusMigration.constants;

public class CustomQuery {
    public static final String QUERY_TO_GET_SOURCE_FIELDS =  "select sourceFieldName from EhrMapping where" +
            " sourceEhrName = :sourceEhrName and serviceLine = :serviceLine " +
            "and targetEhrName = :targetEhrName";

    public static final String QUERY_TO_GET_TARGET_FIELD = "select targetFieldName from EhrMapping where" +
            " sourceEhrName = :sourceEhrName and serviceLine = :serviceLine " +
            "and targetEhrName = :targetEhrName and sourceFieldName = :sourceFieldName";
}

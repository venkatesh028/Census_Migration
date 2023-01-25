package com.ideas2it.censusMigration.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ideas2it.censusMigration.Logger.CustomLogger;
import com.ideas2it.censusMigration.model.TargetRecord;
import com.ideas2it.censusMigration.repository.MappingRepository;
import com.ideas2it.censusMigration.service.OneTimeMappingService;
import com.ideas2it.censusMigration.service.TargetRecordService;

@Service
public class OneTimeMappingServiceImpl implements OneTimeMappingService {
    private final MappingRepository mappingRepository;
    private final TargetRecordService targetRecordService;
    private final CustomLogger logger = new CustomLogger(OneTimeMappingServiceImpl.class);
    public OneTimeMappingServiceImpl(MappingRepository mappingRepository, TargetRecordService targetRecordService){
        this.mappingRepository = mappingRepository;
        this.targetRecordService = targetRecordService;
    }

    /**
     * Gets the Name of the cell and compare with
     * keyword if matches then get the cell index
     * Stores in cellNumber if there is No cell with given
     * cell name then custom Exception is thrown
     *
     * @param heading - Holds the Heading Row of the file
     * @param field - Holds the names of the cell
     * @return cellNumber - Holds the index position of the cell
     */
    private int getCellNumber(Row heading, String field) {
        int cellNumber = -1;

        for (int i = 0; i < heading.getLastCellNum(); i++){
            Cell cell = heading.getCell(i);
            String cellName = cell.getStringCellValue();

            if (cellName.trim().equalsIgnoreCase(field.trim())) {
                cellNumber = cell.getColumnIndex();
                break;
            }
        }
        return cellNumber;
    }

    /**
     * Gets the values of the particular cell in each row
     * adds to the list of values
     *
     * @param rowIterator - Holds the List of Rows
     * @param sourceFields - Holds all the source fields
     * @param targetFields - Holds all the fields
     * @return values - Holds the values for the particular cell in each row
     */
    private List<Map<String, Object>> getValues(Iterator<Row> rowIterator, List<String> sourceFields,
                                   List<String> targetFields, Row heading) {
        Map<String, Object> data;
        List<Map<String, Object>> values = new ArrayList<>();
        Cell cell;

        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            data = new HashMap<>();

            for (int i = 0; i < sourceFields.size(); i++) {
                cell = row.getCell(getCellNumber(heading, sourceFields.get(i)));
                data.put(targetFields.get(i),String.valueOf(cell));
            }
            values.add(data);
        }
        values.remove(values.get(0));
        return values;
    }

    /**
     * Reads the file and gets the heading using heading finds the
     * cellIndex Position and then get all the values for that cell
     * in each row
     *
     * @param file - Hols the XLSXFile
     */
    public List<TargetRecord> readXLSXFile(MultipartFile file, String sourceEhrName, String serviceLine) throws IOException{
        List<TargetRecord>  targetRecordsValues;
        List<String> sourceFields = mappingRepository.getSourceFieldNameBySourceEhrNameAndServiceLineAndTargetEhrName(
                sourceEhrName,serviceLine,"HCHB");
        logger.info(sourceFields.toString());
        List<String> targetFields = new ArrayList<>();

        for (String sourceField : sourceFields){
            targetFields.add( mappingRepository.getTargetFieldNameBySourceEhrNameAndServiceLineAndTargetEhrNameAAndSourceFieldName(
                    sourceEhrName,serviceLine,"HCHB",sourceField
                    )
            );
        }

        try(InputStream data = file.getInputStream()){
            Workbook workBook = new XSSFWorkbook(data);
            Sheet sheet = workBook.getSheetAt(0);
            Row heading = sheet.getRow(0);
            targetRecordsValues = getTargetRecordValues(sourceEhrName,serviceLine,
                    getValues(sheet.rowIterator(),sourceFields,targetFields,heading));
        }
        return targetRecordsValues;
    }

    private List<TargetRecord> getTargetRecordValues(String sourceEhrName,
                                                     String serviceLine,
                                                     List<Map<String, Object>> values) {
        List<TargetRecord> targetRecords = new ArrayList<>();

        for (int i = 0; i < values.size(); i++){
            TargetRecord targetRecord = new TargetRecord();
            targetRecord.setSourceEhrName(sourceEhrName);
            targetRecord.setServiceLine(serviceLine);
            targetRecord.setPatientAttributes(values.get(i));
            targetRecords.add(targetRecord);
        }
        targetRecords = targetRecordService.addTargetRecord(targetRecords);
        System.out.println(targetRecords);
        return targetRecords;
    }
}

package com.ideas2it.censusMigration.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ideas2it.censusMigration.Logger.CustomLogger;
import com.ideas2it.censusMigration.repository.MappingRepository;
import com.ideas2it.censusMigration.service.OneTimeMappingService;

@Service
public class OneTimeMappingServiceImpl implements OneTimeMappingService {
    private final MappingRepository mappingRepository;
    private final CustomLogger logger = new CustomLogger(OneTimeMappingServiceImpl.class);
    public OneTimeMappingServiceImpl(MappingRepository mappingRepository){
        this.mappingRepository = mappingRepository;
    }

    /**
     * Gets the Name of the cell and compare with
     * keyword if matches then get the cell index
     * Stores in cellNumber if there is No cell with given
     * cell name then custom Exception is thrown
     *
     * @param heading - Holds the Heading Row of the file
     * @param fields - Holds the names of the cell
     * @return cellNumber - Holds the index position of the cell
     */
    private List<Integer> getCellNumber(Row heading, List<String> fields) {
        List<Integer> cellNumber = new ArrayList<>();
        String cellName = null;

        for (String field: fields) {
            for (int i = 0; i < heading.getLastCellNum(); i++){
                Cell cell = heading.getCell(i);
                cellName = cell.getStringCellValue();

                if (cellName.trim().equalsIgnoreCase(field.trim())) {
                    cellNumber.add(cell.getColumnIndex());
                    break;
                }
            }
        }
        return cellNumber;
    }

    /**
     * Gets the values of the particular cell in each row
     * adds to the list of values
     *
     * @param rowIterator - Holds the List of Rows
     * @param cellNumber - Holds the cellNumber for which we need value
     * @return values - Holds the values for the particular cell in each row
     */
    private List<Object> getValues(Iterator<Row> rowIterator, int cellNumber) {
        List<Object> values = new ArrayList<>();

        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            Cell cell = row.getCell(cellNumber);

            switch (cell.getCellType()) {
                case NUMERIC -> {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        values.add(cell.getLocalDateTimeCellValue().toLocalDate());
                    } else {
                        values.add(cell.getNumericCellValue());
                    }
                }
                case STRING -> values.add(cell.getStringCellValue());
            }
        }
        return values;
    }

    /**
     * Reads the file and gets the heading using heading finds the
     * cellIndex Position and then get all the values for that cell
     * in each row
     *
     * @param file - Hols the XLSXFile
     */
    public List<Object> readXLSXFile(MultipartFile file, String sourceEhrName, String serviceLine) throws IOException{
        List<Object> values = new ArrayList<>();

        List<String> fields = mappingRepository.getSourceFieldNameBySourceEhrNameAndServiceLineAndTargetEhrName(
                sourceEhrName,serviceLine,"Cerner");
        logger.info(fields.toString());

        try(InputStream data = file.getInputStream();){
            Workbook workBook = new XSSFWorkbook(data);
            Sheet sheet = workBook.getSheetAt(0);
            List<Integer> cellNumbers;
            Row heading = sheet.getRow(0);
            cellNumbers = getCellNumber(heading,fields);

            for (Integer cellNumber : cellNumbers) {
                values.add(getValues(sheet.rowIterator(), cellNumber));
            }
        }
        return values;
    }
}

package de.fallapalooza.streamapi.annotation.retrieve;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import de.fallapalooza.streamapi.annotation.model.Point;
import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class SheetsApiRetrieveService implements RetrieveService {
    @Autowired
    private Sheets sheets;

    @Value("${spreadsheet}")
    private String spreadsheet;

    @Override
    public <T> T retrieve(CellDefinition<T> definition, Point origin) {
        List<String> cells = definition.resolveCell(origin);
        try {
            BatchGetValuesResponse response = sheets.spreadsheets().values().batchGet(spreadsheet).setRanges(cells).execute();
            Iterator<ValueRange> values = response.getValueRanges().iterator();
            return definition.convertValue(values);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot connect to sheets API", e);
        }
    }
}

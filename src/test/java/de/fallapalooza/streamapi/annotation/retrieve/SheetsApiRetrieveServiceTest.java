package de.fallapalooza.streamapi.annotation.retrieve;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import de.fallapalooza.streamapi.annotation.exception.SheetsRetrieveException;
import de.fallapalooza.streamapi.annotation.model.Point;
import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import de.fallapalooza.streamapi.annotation.processor.ConstantCellDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SheetsApiRetrieveServiceTest {
    @Mock
    private Sheets sheets;

    @Mock
    private Sheets.Spreadsheets spreadsheets;

    @Mock
    private Sheets.Spreadsheets.Values values;

    @Mock
    private Sheets.Spreadsheets.Values.BatchGet batchGet;

    @InjectMocks
    private SheetsApiRetrieveService service;

    private BatchGetValuesResponse response;

    @BeforeEach
    void beforeEach() throws IOException {
        when(sheets.spreadsheets()).thenReturn(spreadsheets);
        when(spreadsheets.values()).thenReturn(values);
        when(values.batchGet(anyString())).thenReturn(batchGet);
        when(batchGet.setRanges(any())).thenReturn(batchGet);

        ReflectionTestUtils.setField(service, "spreadsheet", "abcd");

        response = new BatchGetValuesResponse();
        response.setSpreadsheetId("abcd");
        response.setValueRanges(Collections.singletonList(null));
    }

    @Test
    void shouldRetrieveCellsBasedOnDefinition() throws IOException {
        CellDefinition<Integer> definition = new ConstantCellDefinition<>(Point.ONE, 1);
        when(batchGet.execute()).thenReturn(response);
        service.retrieve(definition);

        verify(batchGet).setRanges(Collections.singletonList("A1"));
        verify(batchGet).execute();
    }

    @Test
    void shouldParseCellsBasedOnDefinition() throws IOException {
        CellDefinition<Integer> definition = new ConstantCellDefinition<>(Point.ONE, 1);
        when(batchGet.execute()).thenReturn(response);
        Integer value = service.retrieve(definition);

        assertEquals(1, value);
    }

    @Test
    void shouldThrowConnectionExceptionWhenSheetsIsDown() throws IOException {
        when(batchGet.execute()).thenThrow(new IOException());
        CellDefinition<Integer> definition = new ConstantCellDefinition<>(Point.ONE, 1);

        assertThrows(SheetsRetrieveException.class, () -> {
            service.retrieve(definition);
        });
    }
}
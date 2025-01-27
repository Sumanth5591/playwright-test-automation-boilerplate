package io.github.tahanima.data;

import com.univocity.parsers.annotations.Parsed;
import lombok.Getter;
import lombok.ToString;

/**
 * @author tahanima
 */
@ToString
public class BaseData {
    @Getter
    @Parsed(field = "Test Case ID", defaultNullRead = "")
    private String testCaseId;

    @Getter
    @Parsed(field = "Test Case Description", defaultNullRead = "")
    private String testCaseDescription;
}

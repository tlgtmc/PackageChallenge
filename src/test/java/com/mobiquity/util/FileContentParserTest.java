package com.mobiquity.util;

import com.mobiquity.domain.Pack;
import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author tatmaca
 */

class FileContentParserTest {

    private final String filePathLocal = Paths.get("src","main","test","resources","example_input").toString();

    @Test
    void testFileRead() throws APIException {
        FileContentParser parser = new FileContentParser();
        List<Pack> packs = parser.parse(filePathLocal);

        assertEquals(4, packs.size());

        assertEquals(6, packs.get(0).getPackItems().size());
    }

}

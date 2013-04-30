package com.tagtraum.perf.gcviewer.exp;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import org.junit.Test;

import com.tagtraum.perf.gcviewer.exp.impl.*;

public class WideCSVWriterTest extends SimpleGcWriterTest {
    @Test
    public void exportLocaleDe() throws Exception {
        Locale.setDefault(new Locale("de", "ch"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        AbstractDataWriter writer = new WideCSVDataWriter(outputStream);
        
        writer.write(gcModel);

        String str = outputStream.toString();
        System.out.println(str);        
		String[] lines = str.split(System.getProperty("line.separator"));
        assertEquals("line count", 3, lines.length);
        
        String[] firstLine = lines[1].split(",");
        assertEquals("number of parts in line 1", 8, firstLine.length);
        assertEquals("name of event", "YoungGC", firstLine[6]);
        assertEquals("stopTheWorld", "1", firstLine[7]);
        assertEquals("timestamp", "0.677", firstLine[0]);
        assertEquals("PreUsed", "175499", firstLine[2]);
        assertEquals("PostUsed", "104936", firstLine[3]);
        
        String[] secondLine = lines[2].split(",");
        assertEquals("number of parts in line 2", 8, secondLine.length);
        assertEquals("name of event 2", "InitialMarkGC", secondLine[secondLine.length-2]);
    }

}

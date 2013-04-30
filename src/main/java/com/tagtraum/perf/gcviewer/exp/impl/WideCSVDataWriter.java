package com.tagtraum.perf.gcviewer.exp.impl;


import java.text.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import com.tagtraum.perf.gcviewer.model.GCEvent;
import com.tagtraum.perf.gcviewer.model.GCModel;

public class WideCSVDataWriter extends SimpleGcWriter {

	private static final DateFormat df = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss.S");
    public WideCSVDataWriter(OutputStream out) {
        super(out);
    }

    private void writeHeader() {
        out.println("TimestampSec,TimestampDate,PreUsedK,PostUsedK,TotalK,PauseInSec,GCType,StopTheWorld");
    }

    public void write(GCModel model) throws IOException {
        writeHeader();
        
        Iterator<GCEvent> i = model.getGCEvents();
        while (i.hasNext()) {
            GCEvent event = (GCEvent) i.next();
            double ts;
			if (model.hasCorrectTimestamp()) {
                ts = event.getTimestamp() - event.getPause();
            } else {
            	ts = event.getTimestamp();
            }
            out.print(String.format("%.3f", ts));
            out.print(',');
            out.print(event.getDatestamp() != null ? df.format(event.getDatestamp()) : "NA");
            out.print(',');
            out.print(event.getPreUsed());
            out.print(',');
            out.print(event.getPostUsed());
	    	out.print(',');
            out.print(event.getTotal());
            out.print(',');
            out.print(String.format("%.3f", event.getPause()));
            out.print(',');
            out.print(getSimpleType(event));
            out.print(',');
            out.println(event.isStopTheWorld() ? 1 : 0);
        }
        
        out.flush();
    }

}

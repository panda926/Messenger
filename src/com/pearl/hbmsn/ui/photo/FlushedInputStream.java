package com.pearl.hbmsn.ui.photo;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FlushedInputStream extends FilterInputStream {

	public FlushedInputStream(InputStream in) {
		super(in);
	}

	@Override
	public long skip(long byteCount) throws IOException {
		
		long totalBytesSkipped = 0L;
		
		while (totalBytesSkipped < byteCount) {
			
			long bytesSkipped = in.skip(byteCount - totalBytesSkipped);
			if (bytesSkipped == 0L) {
				int by_te = read();
				
				if (by_te < 0) {
					break; // we reached EOF
				} else {
					bytesSkipped = 1; // we read one byte
				}
			}
		totalBytesSkipped += bytesSkipped;
		}
		return totalBytesSkipped;
	}

	

}

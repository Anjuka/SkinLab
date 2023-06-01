package com.fyp.vishmi.skinlab.helper;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {

    private File mFile;
    private String mPath;
    private ImageUploadCallback mListener;
    private String content_type;
    private String upload;
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private int writeToCall = 0;

    public ProgressRequestBody(final File file, String content_type)   {
        this.content_type = content_type;
        mFile = file;
        this.writeToCall = 0;
    }


    @Override
    public MediaType contentType() {
        return MediaType.parse(content_type+"/*");
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        writeToCall++;
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long uploaded = 0;
        try (FileInputStream in = new FileInputStream(mFile)) {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {
                uploaded += read;
                sink.write(buffer, 0, read);
                if (writeToCall == 2) { // updating the progress
                    handler.post(new ProgressUpdater(uploaded, fileLength));
                }
            }
        }
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;
        ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }
        @Override
        public void run() {
            //update progress here in the UI
            if(mListener!=null)
                mListener.onProgressUpdate((int)(100 * mUploaded / mTotal)); //updating the UI of the progress
        }
    }
}

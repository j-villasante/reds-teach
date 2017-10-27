package com.berry.blue.reds_teach.share;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.berry.blue.reds_teach.interfaces.activities.ExporterView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Exporter implements ShareI.ExporterI{
    private ExporterView view;
    private Context context;

    public Exporter(ExporterView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void share(List<String[]> data) {
        File path = context.getFilesDir();
        File file = new File(path, "data.csv");
        FileOutputStream stream;
        try {
            stream = new FileOutputStream(file);
            for (String [] row : data) {
                String r = join(row);
                stream.write(r.getBytes());
            }
            stream.close();
        } catch (FileNotFoundException e) {
            this.view.showMessage("File not found");
        } catch (IOException e) {
            this.view.showMessage("I/O Error. Sorry, don't know how to handle this exception. :(");
        }

        Uri uri  = FileProvider.getUriForFile(context, "com.berry.blue.reds_teach.fileprovider", file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("text/csv");
        context.startActivity(Intent.createChooser(shareIntent, "Compartir"));
    }

    private String join(String[] data) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null)
                builder.append(data[i]);

            if (i < data.length - 1)
                builder.append(";");
        }
        builder.append("\n");
        return builder.toString();
    }

    @Override
    public void showMessage(String message) {
        this.view.showMessage(message);
    }
}

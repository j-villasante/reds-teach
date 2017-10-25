package com.berry.blue.reds_teach.share;

import java.util.List;

public interface ShareI {
    interface ExporterI {
        void share(List<String[]> data);
        void showMessage(String message);
    }
}

package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WriterLog {
    private final FileWriter fw;

    public WriterLog() throws IOException {
        File file = new File("data/logs/log-" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME).replace(':', '_') + ".log");
        if (!file.exists()) file.createNewFile();
        fw = new FileWriter(file);
    }

    public void writeLog(String data) throws IOException {
        fw.write(data + "\n");
        fw.flush();
    }
}

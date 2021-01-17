package ro.mta.se.lab.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private String message;

    public String appendMessage(String cityName) throws IOException {
        FileWriter fw = new FileWriter("src/main/java/ro/mta/se/lab/model/logger.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        bw.write(formatter.format(date)+"   "+this.message + cityName);
        bw.newLine();
        bw.close();

        return (this.message + cityName);
    }

    public Logger() {
        this.message = "Utilizatorul a cautat date despre orasul : ";
    }
}


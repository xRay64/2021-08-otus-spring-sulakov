package ru.otus.homework02.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class IOServiceImpl implements IOService {
    private final PrintStream printStream;
    private final InputStream inputStream;
    private BufferedReader reader;

    public IOServiceImpl(@Value("#{ T(java.lang.System).out}") PrintStream printStream,
                         @Value("#{ T(java.lang.System).in}") InputStream inputStream) {
        this.printStream = printStream;
        this.inputStream = inputStream;
    }

    @Override
    public void print(String message) {
        printStream.println(message);
    }

    @Override
    public void println(String message) {
        printStream.println(message);
    }

    @Override
    public String readLine() {
        String resultString = "";
        try {
            resultString = new BufferedReader(new InputStreamReader(this.inputStream)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString;
    }
}

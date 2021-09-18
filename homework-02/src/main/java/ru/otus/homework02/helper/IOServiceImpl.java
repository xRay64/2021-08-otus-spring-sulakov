package ru.otus.homework02.helper;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.*;

@Scope("prototype")
@Service
public class IOServiceImpl implements IOService {
    private final IOStreamsProvider ioStreamsProvider;
    private PrintStream printStream;
    private InputStream inputStream;
    private BufferedReader reader;

    public IOServiceImpl(IOStreamsProvider ioStreamsProvider) {
        this.ioStreamsProvider = ioStreamsProvider;
    }

    @Override
    public void print(String message) {
        lazyStreamInitializer();
        printStream.println(message);
    }

    @Override
    public void println(String message) {
        lazyStreamInitializer();
        printStream.println(message);
    }

    @Override
    public String readLine() {
        lazyStreamInitializer();
        String resultString = "";
        lazyReaderInitialize();
        try {
            resultString = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    private void lazyStreamInitializer() {
        if (printStream == null) {
            printStream = ioStreamsProvider.getPrintStream();
        }
        if (inputStream == null) {
            inputStream = ioStreamsProvider.getInputStream();
        }
    }

    private void lazyReaderInitialize() {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(this.inputStream));
        }
    }
}

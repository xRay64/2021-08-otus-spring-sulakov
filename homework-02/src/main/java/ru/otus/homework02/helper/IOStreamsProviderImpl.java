package ru.otus.homework02.helper;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;

@Scope("prototype")
@Component
public class IOStreamsProviderImpl implements IOStreamsProvider {
    @Override
    public PrintStream getPrintStream() {
        return System.out;
    }

    @Override
    public InputStream getInputStream() {
        return System.in;
    }
}

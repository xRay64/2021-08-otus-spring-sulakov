package ru.otus.homework02.helper;

import java.io.InputStream;
import java.io.PrintStream;

public interface IOStreamsProvider {
    PrintStream getPrintStream();

    InputStream getInputStream();
}

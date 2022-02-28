package ru.otus.configgenerator.service.template.ext;

import org.springframework.stereotype.Service;
import ru.otus.configgenerator.exception.ConfigGeneratorException;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ConfigFileZipperImpl implements ConfigFileZipper {
    @Override
    public File zipFiles(List<File> fileList) {
        try {
            File archive = File.createTempFile(UUID.randomUUID().toString(), ".zip");
            FileOutputStream fileOutputStream = new FileOutputStream(archive);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

            for (File file : fileList) {
                ZipEntry zipEntry = new ZipEntry(getNewFileName(file.getName()));
                zipOutputStream.putNextEntry(zipEntry);

                FileInputStream currentFileInputStream = new FileInputStream(file);
                byte[] bytesBuffer = new byte[1024];
                int length;
                while ((length = currentFileInputStream.read(bytesBuffer)) > 0) {
                    zipOutputStream.write(bytesBuffer, 0, length);
                }
                currentFileInputStream.close();
            }

            zipOutputStream.close();
            fileOutputStream.close();

            return archive;
        } catch (IOException e) {
            throw new ConfigGeneratorException("Error creating archive file", e);
        }
    }

    private String getNewFileName(String currentFileName) {
        if (currentFileName.indexOf(".") != currentFileName.lastIndexOf(".")) {
            int firstDotIndex = currentFileName.indexOf(".");
            int secondDotIndex = currentFileName.indexOf(".", firstDotIndex + 1);
            return currentFileName.substring(0, secondDotIndex);
        }
        return currentFileName;
    }
}

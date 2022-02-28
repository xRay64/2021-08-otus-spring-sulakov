package ru.otus.configgenerator.service.repository.ext;

import org.springframework.stereotype.Service;
import ru.otus.configgenerator.config.repository.RepositoryFoldersFilterParams;

@Service
public class RepositoryFoldersFilterImpl implements RepositoryFoldersFilter {
    private final String rawFilteredFoldersNames;
    private String[] filteredFoldersNames;

    public RepositoryFoldersFilterImpl(RepositoryFoldersFilterParams params) {
        this.rawFilteredFoldersNames = params.getFilteredFolders();
    }

    @Override
    public String[] getFilteredFoldersNames() {
        String[] folderNames = rawFilteredFoldersNames.split(",");
        for (int i = 0; i < folderNames.length; i++) {
            folderNames[i] = folderNames[i].trim();
        }

        return folderNames;
    }
}

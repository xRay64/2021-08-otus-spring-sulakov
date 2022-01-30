package ru.otus.homeworkintegration.services;

import org.springframework.stereotype.Service;
import ru.otus.homeworkintegration.domain.Commit;
import ru.otus.homeworkintegration.domain.VCSChanges;
import ru.otus.homeworkintegration.domain.VCSChangesType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class GitLabImpl implements GitLab {
    private static final int VSC_CHANGES_MULTIPLIER = 2;

    @Override
    public List<Commit> getChanges(int countOfChanges) {
        List<Commit> commitList = new ArrayList<>();
        for (int i = 0; i < countOfChanges; i++) {
            commitList.add(new Commit(UUID.randomUUID().toString(), getListOfVSCChanges(countOfChanges)));
        }
        return commitList;
    }

    private List<VCSChanges> getListOfVSCChanges(int countOfChanges) {
        List<VCSChanges> vcsChanges = new ArrayList<>();
        for (int i = 0; i < countOfChanges * VSC_CHANGES_MULTIPLIER; i++) {
            vcsChanges.add(new VCSChanges(getRandomType()));
        }
        return vcsChanges;
    }

    private VCSChangesType getRandomType() {
        Random random = new Random();
        VCSChangesType[] vcsChangesTypes = VCSChangesType.values();
        return vcsChangesTypes[random.nextInt(vcsChangesTypes.length)];
    }
}

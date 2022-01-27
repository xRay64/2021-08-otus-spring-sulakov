package ru.otus.homeworkbatch.models.matcher;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class AuthorIdMatcher {
    private static final Map<Long, String> idsMap = new HashMap<>();

    public String getAuthorStringId(long authorLongId) {
        String authorStringId = idsMap.get(authorLongId);
        if (authorStringId == null) {
            authorStringId = UUID.randomUUID().toString();
            idsMap.put(authorLongId, authorStringId);
        }
        return authorStringId;
    }

}

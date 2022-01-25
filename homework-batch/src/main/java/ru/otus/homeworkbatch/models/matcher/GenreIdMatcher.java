package ru.otus.homeworkbatch.models.matcher;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class GenreIdMatcher {
    private static final Map<Long, String> idsMap = new HashMap<>();

    public String getGenreStringId(long genreLongId) {
        String genreStringId = idsMap.get(genreLongId);
        if (genreStringId == null) {
            genreStringId = UUID.randomUUID().toString();
            idsMap.put(genreLongId, genreStringId);
        }
        return genreStringId;
    }

}

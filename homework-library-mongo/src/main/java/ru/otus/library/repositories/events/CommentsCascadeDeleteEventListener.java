package ru.otus.library.repositories.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.library.models.Book;
import ru.otus.library.repositories.CommentRepository;

@Component
@RequiredArgsConstructor
public class CommentsCascadeDeleteEventListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        String bookId = event.getSource().get("_id").toString();
        commentRepository.deleteByBook_Id(bookId);
    }
}

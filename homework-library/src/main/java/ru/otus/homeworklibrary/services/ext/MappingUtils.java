package ru.otus.homeworklibrary.services.ext;

import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.services.dto.BookDto;

public class MappingUtils {
    public static BookDto mapToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setGenre(book.getGenreList());
        return bookDto;
    }
}

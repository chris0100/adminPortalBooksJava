package com.books.adminportal.service;

import com.books.adminportal.domain.Book;
import com.books.adminportal.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepositoryObj;

    @Override
    public Book save(Book book) {
        return bookRepositoryObj.save(book);
    }


    @Override
    public List<Book> findAll() {
        return (List<Book>) bookRepositoryObj.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepositoryObj.findById(id).get();
    }

    @Override
    public void removeOne(Long id) {
        bookRepositoryObj.deleteById(id);
    }
}

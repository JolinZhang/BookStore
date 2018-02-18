package com.jonele.bookstore.repository;

import com.jonele.bookstore.model.Book;
import com.jonele.bookstore.model.Language;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import repository.BookRepository;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Jonelezhang on 2/10/18.
 */
@RunWith(Arquillian.class)
public class BookRepositoryTest {

    @Inject
    private BookRepository bookRepository;

    @Test(expected = Exception.class)
    public void findWithInvalidId(){
        bookRepository.find(null);
    }

    @Test(expected = Exception.class)
    public void createInvalidBook(){
        // Create a book
        Book book = new Book("isbn", null, 12F, 123, Language.ENGLISH, new Date(),"http://banana", "description" );
        book = bookRepository.create(book);
    }

    @Test
    public void create() throws Exception {
        // Testing counting books
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());
        // Create a book
        Book book = new Book("isbn", "a title", 12F, 123, Language.ENGLISH, new Date(),"http://banana", "description" );
        book = bookRepository.create(book);
        Long bookId  = book.getId();
        //check created book
        assertNotNull(bookId);
        Book bookFound = bookRepository.find(bookId);
        assertEquals("a title", bookFound.getTitle());

        // Testing counting books
        assertEquals(Long.valueOf(1), bookRepository.countAll());
        assertEquals(1, bookRepository.findAll().size());

        //delete book
        bookRepository.delete(bookId);
        // Testing counting books
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());

    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(repository.BookRepository.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addAsManifestResource("Meta-Inf/persistence.xml","persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}

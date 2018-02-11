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

import static org.junit.Assert.*;

/**
 * Created by Jonelezhang on 2/10/18.
 */
@RunWith(Arquillian.class)
public class BookRepositoryTest {

    @Inject
    private BookRepository bookRepository;

    @Test
    public void create() throws Exception {

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

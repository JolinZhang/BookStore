package repository;

import com.jonele.bookstore.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

/**
 * Created by Jonelezhang on 2/3/18.
 */
@Transactional(SUPPORTS)
public class BookRepository {

    @PersistenceContext(unitName = "bookStorePU")
    private EntityManager em;


    public Book find(@NotNull Long id){
        return em.find(Book.class, id);

    }
    @Transactional(REQUIRED)
    public Book create(@NotNull Book book){
        em.persist(book);
        return book;
    }

    @Transactional(REQUIRED)
    public void delete(@NotNull Long id){
        em.remove(em.getReference(Book.class, id));

    }

    public List<Book> findAll(){
        TypedQuery<Book> query = em.createQuery("select b from Book b order by b.title", Book.class);
        return query.getResultList();
    }

    public Long countAll(){
        TypedQuery<Long>  query =  em.createQuery("select count(b) from Book b", Long.class);
        return query.getSingleResult();
    }
}

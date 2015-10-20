package linoor.spring.blog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.dialect.function.AnsiTrimEmulationFunction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by linoor on 10/20/15.
 */

@Entity
public class Article {
    @JsonIgnore
    @ManyToOne
    private Author author;

    @Id
    @GeneratedValue
    private long id;

    public Article() {

    }

    public Article(Author author, String title, String body) {
        this.author = author;
        this.title = title;
        this.body = body;
    }

    private String title;
    private String body;

    public Author getAuthor() {
        return author;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}

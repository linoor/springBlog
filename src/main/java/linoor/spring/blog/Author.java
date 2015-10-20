package linoor.spring.blog;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by linoor on 10/20/15.
 */

@Entity
public class Author {

    @OneToMany(mappedBy = "author")
    private Set<Article> articles = new HashSet<>();

    @Id
    @GeneratedValue
    private long id;

    public long getId() {
        return id;
    }

    @JsonIgnore
    private String password;
    private String username;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Author(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Author() {

    }
}

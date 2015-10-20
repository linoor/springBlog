package linoor.spring.blog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by linoor on 10/20/15.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Collection<Article> findByAuthorUsername(String username);
}

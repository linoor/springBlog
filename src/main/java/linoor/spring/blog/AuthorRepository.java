package linoor.spring.blog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by linoor on 10/20/15.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByUsername(String username);
}

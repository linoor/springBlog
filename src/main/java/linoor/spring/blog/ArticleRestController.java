package linoor.spring.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

/**
 * Created by linoor on 10/21/15.
 */

@RestController
@RequestMapping("/{userId}/articles")
public class ArticleRestController {

    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Article input) {
        this.validateUser(userId);
        return this.authorRepository
                .findByUsername(userId)
                .map(author -> {
                    Article result = articleRepository.save(new Article(author,
                            input.getTitle(), input.getBody()));
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri());
                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    Article readArticle(@PathVariable String userId, @PathVariable Long articleId) {
        this.validateUser(userId);
        return this.articleRepository.findOne(articleId);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Article> readArticles(@PathVariable String userId) {
        this.validateUser(userId);
        return this.articleRepository.findByAuthorUsername(userId);
    }

    @Autowired
    ArticleRestController(ArticleRepository articleRepository,
                           AuthorRepository authorRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
    }

    private void validateUser(String userId) {
        this.authorRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId);
        )
    }
}

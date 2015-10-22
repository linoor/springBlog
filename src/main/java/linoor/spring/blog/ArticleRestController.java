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
@RequestMapping("users/{userId}/entries")
public class ArticleRestController {

    private final EntryRepository entryRepository;
    private final AuthorRepository authorRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Entry input) {
        this.validateUser(userId);
        return this.authorRepository
                .findByUsername(userId)
                .map(author -> {
                    Entry result = entryRepository.save(new Entry(author,
                            input.getTitle(), input.getBody()));
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri());
                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    Entry readArticle(@PathVariable String userId, @PathVariable Long articleId) {
        this.validateUser(userId);
        return this.entryRepository.findOne(articleId);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Entry> readArticles(@PathVariable String userId) {
        this.validateUser(userId);
        return this.entryRepository.findByAuthorUsername(userId);
    }

    @Autowired
    ArticleRestController(EntryRepository entryRepository,
                           AuthorRepository authorRepository) {
        this.entryRepository = entryRepository;
        this.authorRepository = authorRepository;
    }

    private void validateUser(String userId) {
        this.authorRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
    }
}

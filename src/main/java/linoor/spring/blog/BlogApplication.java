package linoor.spring.blog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class BlogApplication {

    @Bean
    CommandLineRunner init(AuthorRepository authorRepository,
                           EntryRepository entryRepository) {
        return (evt) -> Arrays.asList(
                "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(",")).forEach(
                        a -> {
                            Author author = authorRepository.save(new Author(a, "password"));
                            entryRepository.save(new Entry(author, "some title", "some body lorem ipsum."));
                            entryRepository.save(new Entry(author, "some title2", "some body lorem ipsum.2"));
                        }
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}

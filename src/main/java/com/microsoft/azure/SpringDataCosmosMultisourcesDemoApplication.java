package com.microsoft.azure;

import com.microsoft.azure.datasource1.Book;
import com.microsoft.azure.datasource1.BookRepository;
import com.microsoft.azure.datasource2.User;
import com.microsoft.azure.datasource2.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;


@SpringBootApplication
public class SpringDataCosmosMultisourcesDemoApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;


	private final User user_1 = new User("1024", "1024@geek.com", "1k", "Mars");
	private final Book book_1 = new Book("9780792745488", "Zen and the Art of Motorcycle Maintenance", "Robert M. Pirsig");


	public static void main(String[] args) {
		SpringApplication.run(SpringDataCosmosMultisourcesDemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		final List<User> users = this.userRepository.findByEmailOrName(this.user_1.getEmail(), this.user_1.getName()).collectList().block();
		users.forEach(System.out::println);
		final Book book = this.bookRepository.findById("9780792745488").block();
		System.out.println(book);
	}

	@PostConstruct
	public void setup() {
		this.userRepository.save(user_1).block();
		this.bookRepository.save(book_1).block();

	}

	@PreDestroy
	public void cleanup() {
		this.userRepository.deleteAll().block();
		this.bookRepository.deleteAll().block();
	}
}

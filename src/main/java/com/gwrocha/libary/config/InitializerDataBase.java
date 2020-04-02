package com.gwrocha.libary.config;

import static java.lang.Boolean.TRUE;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gwrocha.libary.models.Author;
import com.gwrocha.libary.models.Book;
import com.gwrocha.libary.models.GeneralConfigurations;
import com.gwrocha.libary.models.User;
import com.gwrocha.libary.models.enums.Role;
import com.gwrocha.libary.models.enums.StatusUser;
import com.gwrocha.libary.repositories.AuthorRepository;
import com.gwrocha.libary.repositories.BookRepository;
import com.gwrocha.libary.repositories.GeneralConfigurationsRepository;
import com.gwrocha.libary.repositories.UserRepository;

@Component
public class InitializerDataBase {
	
	@Autowired
	private AuthorRepository authorRepo;
	
	@Autowired
	private BookRepository bookRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private GeneralConfigurationsRepository configurationsRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostConstruct
	@Transactional
	public void init(){
		GeneralConfigurations generalConfigurations = configurationsRepo.findAll().stream().findFirst().orElse(new GeneralConfigurations());
		if(!generalConfigurations.getStartedDataBase()) {
			insertUsers();
			insertBooksAndAuthors();
			generalConfigurations.setStartedDataBase(TRUE);
			configurationsRepo.save(generalConfigurations);
		}
	}

	private void insertUsers() {
		User user = new User("Gleydson Rocha", "gwrocha",  encoder.encode("123456"), StatusUser.ACTIVE, Role.USER);
		User admin = new User("Admin", "admin", encoder.encode("admin"), StatusUser.ACTIVE, Role.USER, Role.ADMIN);
		
		userRepo.save(user);
		userRepo.save(admin);
		
	}
	
	private void insertBooksAndAuthors() {
		bookByAutor.entrySet().forEach(ent ->{
			String bookTitle = ent.getKey();
			String authorName = ent.getValue();
			
			Author author = authorRepo.save(new Author(authorName));
			bookRepo.save(new Book(bookTitle, author));
			
		});
		
	}

	private Map<String, String> bookByAutor;
	
	{
		bookByAutor = new HashMap<String, String>();
		bookByAutor.put("O homem mais rico da Babilônia" 					,"George S Clason");
		bookByAutor.put("Sapiens - Uma Breve História da Humanidade"		, "Yuval Noah Harari");
		bookByAutor.put("Mais esperto que o Diabo"							, "Napoleon Hill");
		bookByAutor.put("Fahrenheit 451"									, "Ray Bradbury");
		bookByAutor.put("O conto da aia" 									, "Margaret Atwood");
		bookByAutor.put("Os segredos da mente milionária"					, "T. Harv Eker");
		bookByAutor.put("Mindset"											, "Carol S. Dweck");
		bookByAutor.put("Do Mil ao Milhão. Sem Cortar o Cafezinho"			, "Thiago Nigro");
		bookByAutor.put("O Eternauta 1969"   								, "Héctor Germán Oesterheld");
		bookByAutor.put("A Sutil Arte de Ligar o F*da-Se"   				, "Mark Manson");
		bookByAutor.put("O investidor inteligente"   						, "Benjamin Graham");
		bookByAutor.put("A revolução dos bichos: Um conto de fadas"			, "George Orwell");
		bookByAutor.put("O poder do hábito"									, "Charles Duhigg");
		bookByAutor.put("Antifrágil: Coisas que se beneficiam com o caos"	, "Nassim Nicholas Taleb");
	}
	
}

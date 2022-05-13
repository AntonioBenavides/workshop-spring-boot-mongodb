package com.educandoweb.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.educandoweb.workshopmongo.domain.Post;
import com.educandoweb.workshopmongo.domain.User;
import com.educandoweb.workshopmongo.dto.AuthorDTO;
import com.educandoweb.workshopmongo.dto.CommentDTO;
import com.educandoweb.workshopmongo.repository.PostRepository;
import com.educandoweb.workshopmongo.repository.UserRepository;

@Configuration	
public class Instantiation implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	
		userRepository.deleteAll();
		postRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar para SP. Abraços", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		
		userRepository.save(maria);
		
		CommentDTO coment1 = new CommentDTO("Boa viagem mano", sdf.parse("28/03/2018"), new AuthorDTO(alex));
		CommentDTO coment2 = new CommentDTO("Aproveite", sdf.parse("22/03/2018"), new AuthorDTO(bob));
		CommentDTO coment3 = new CommentDTO("Tenha um ótimo dia", sdf.parse("23/03/2018"), new AuthorDTO(alex));
		
		post1.setComments(Arrays.asList(coment1, coment2));
		post2.setComments(Arrays.asList(coment3));
		
		postRepository.save(post1);
		postRepository.save(post2);
		
	}

}

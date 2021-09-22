package challenge.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import challenge.model.Post;
import challenge.model.User;
import challenge.repository.PostRepository;
import challenge.security.IAuthenticationFacade;

@RestController
public class PostController {

	@Autowired
	private PostRepository repository;
	
	@Autowired
    IAuthenticationFacade authenticationFacade;
	
	@PostMapping("/posts")
	public Post newPost(@Valid @RequestBody Post post) {
		User user = authenticationFacade.getUser();
		post.setUser(user);
		
		return repository.save(post);
		
	}
	
	@GetMapping("/posts")
	public List<Post> listPosts(@Valid @RequestBody Post post) {
		return (List<Post>) repository.findAll();
	
	}
	
	@GetMapping("/posts/{id}")
	public Optional<Post> getPost(@PathVariable(value = "id") Long id) {
		return repository.findById(id);
	
	}
}

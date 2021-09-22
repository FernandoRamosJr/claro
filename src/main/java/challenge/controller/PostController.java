package challenge.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
}

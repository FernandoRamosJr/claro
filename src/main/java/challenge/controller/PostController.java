package challenge.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<List<Post>> listPosts(@Valid @RequestBody Post post) {
		
		List<Post> listPosts = (List<Post>) repository.findAll();		
		if(listPosts.isEmpty()) {
			return ResponseEntity.notFound().build();
		}		
		return ResponseEntity.ok(listPosts );	
	
	}
	
	@GetMapping("/posts/{id}")
	public ResponseEntity<?> getPost(@PathVariable(value = "id") Long id) {
		
		Optional<Post> post = repository.findById(id);	
		if(post.isEmpty()) {
			return ResponseEntity.notFound().build();
		}		
		return ResponseEntity.ok(post);	
	}
	
	
	@PutMapping("/posts/{id}")
	public ResponseEntity<?> updatePost(@PathVariable(value = "id") Long id, @Valid @RequestBody Post post) {
		
		Optional<Post> postUpdated = repository.findById(id);
		if(postUpdated.isEmpty()) {
			return ResponseEntity.notFound().build();
		}		
		postUpdated.get().setTitle(post.getTitle());
		postUpdated.get().setText(post.getText());		
		repository.save(postUpdated.get());		
		return new ResponseEntity<>(postUpdated, HttpStatus.OK);	
	}
	
	
	@DeleteMapping("/posts/{id}")
	public ResponseEntity<?> deletePost(@PathVariable(value = "id") Long id) {
		
		Optional<Post> post = repository.findById(id);		
		if(post.isEmpty()) {
			return ResponseEntity.notFound().build();
		}        
		repository.delete(post.get());        
        return ResponseEntity.ok().build();	
	}
}

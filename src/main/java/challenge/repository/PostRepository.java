package challenge.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import challenge.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
	
	List<Post> findByTitle(String title);
	
	List<Post> findByText(String text);
}

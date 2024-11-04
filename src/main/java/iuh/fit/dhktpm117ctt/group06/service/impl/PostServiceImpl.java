package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.Post;
import iuh.fit.dhktpm117ctt.group06.repository.PostRepository;
import iuh.fit.dhktpm117ctt.group06.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findByName(String name) {
        return postRepository.findByName(name);
    }

    @Override
    public List<Post> findByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    @Override
    public List<Post> findByAuthor(String author) {
        return postRepository.findByAuthor(author);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deleteById(String id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post update(Post post) {
        // Kiểm tra xem bài viết có tồn tại không trước khi cập nhật
        Optional<Post> existingPost = postRepository.findById(post.getId());
        if (existingPost.isPresent()) {
            return postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found with id: " + post.getId());
        }
    }
}

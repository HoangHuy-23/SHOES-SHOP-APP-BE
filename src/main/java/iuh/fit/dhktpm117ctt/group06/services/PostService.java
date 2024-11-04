package iuh.fit.dhktpm117ctt.group06.services;

import iuh.fit.dhktpm117ctt.group06.entities.Post;

import java.util.List;

public interface PostService {
    List<Post> findByName(String name);
    List<Post> findByTitle(String title);
    List<Post> findByAuthor(String author);
    List<Post> findAll();
    Post save(Post post);
    void deleteById(String id);
    Post update(Post post);
}

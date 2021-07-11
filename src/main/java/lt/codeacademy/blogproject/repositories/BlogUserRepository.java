package lt.codeacademy.blogproject.repositories;

import lt.codeacademy.blogproject.repositories.dao.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {

    default List<BlogUser> getBlogUsers(){
        return this.findAll();
    }

    BlogUser getBlogUserById(Long id);

    BlogUser getBlogUserByUsername(String username);
}

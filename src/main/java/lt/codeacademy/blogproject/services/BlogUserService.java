package lt.codeacademy.blogproject.services;

import lt.codeacademy.blogproject.controllers.dto.BlogUserRequest;
import lt.codeacademy.blogproject.controllers.dto.BlogUserResponse;
import lt.codeacademy.blogproject.repositories.BlogUserRepository;
import lt.codeacademy.blogproject.repositories.dao.BlogUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogUserService {

    private final BlogUserRepository blogUserDao;

    @Autowired
    public BlogUserService(BlogUserRepository blogUserDao){
        this.blogUserDao = blogUserDao;
    }

    @Transactional
    public BlogUserResponse createBlogUser(BlogUserRequest blogUserRequest){
        BlogUserResponse blogUserResponse = new BlogUserResponse();
        blogUserDao.save(new BlogUser(blogUserRequest.getUsername(),blogUserRequest.getPassword()));
        blogUserResponse.setUsername(blogUserRequest.getUsername());
        return blogUserResponse;
    }

    public BlogUser getBlogUserUsername(Long id){
        return blogUserDao.getBlogUserById(id);
    }

    public List<BlogUserResponse> getBlogUsers(){
        return blogUserDao.getBlogUsers().stream()
                .map(user -> new BlogUserResponse(user.getUsername())).collect(Collectors.toList());
    }
}

package lt.codeacademy.blogproject.services;

import lt.codeacademy.blogproject.controllers.dto.BlogUserRequest;
import lt.codeacademy.blogproject.controllers.dto.BlogUserResponse;
import lt.codeacademy.blogproject.repositories.BlogUserRepository;
import lt.codeacademy.blogproject.repositories.RoleRepository;
import lt.codeacademy.blogproject.repositories.dao.BlogUser;
import lt.codeacademy.blogproject.repositories.dao.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogUserService {

    private final BlogUserRepository blogUserDao;
    private final RoleRepository roleRepository;

    @Autowired
    public BlogUserService(BlogUserRepository blogUserDao, RoleRepository roleRepository){
        this.blogUserDao = blogUserDao;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public BlogUserResponse createBlogUser(BlogUserRequest blogUserRequest) throws RoleNotFoundException {
        BlogUserResponse blogUserResponse = new BlogUserResponse();

        Set<Role> roles = Set.of(roleRepository.getRoleByRoleName("USER").orElseThrow(() -> new RoleNotFoundException("USER")));

        blogUserDao.save(new BlogUser(blogUserRequest.getUsername(),blogUserRequest.getPassword(),roles));
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

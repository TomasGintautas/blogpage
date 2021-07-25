package lt.codeacademy.blogproject.services;

import lt.codeacademy.blogproject.controllers.dto.BlogUserRequest;
import lt.codeacademy.blogproject.repositories.BlogUserRepository;
import lt.codeacademy.blogproject.repositories.RoleRepository;
import lt.codeacademy.blogproject.repositories.dao.BlogUser;
import lt.codeacademy.blogproject.repositories.dao.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;
import java.util.Set;

@Service
public class BlogUserService implements UserDetailsService {

    private final BlogUserRepository blogUserDao;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public BlogUserService(BlogUserRepository blogUserDao, RoleRepository roleRepository, PasswordEncoder encoder){
        this.blogUserDao = blogUserDao;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Transactional
    public BlogUser createBlogUser(BlogUserRequest blogUserRequest) throws RoleNotFoundException {
        Set<Role> roles = Set.of(roleRepository.getRoleByRoleName("USER").orElseThrow(() -> new RoleNotFoundException("USER")));
        BlogUser user = new BlogUser();
        user.setUsername(blogUserRequest.getUsername());
        user.setPassword(encoder.encode(blogUserRequest.getPassword()));
        user.setRoles(roles);
        return blogUserDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return blogUserDao.getBlogUserByUsername(username);
    }
}

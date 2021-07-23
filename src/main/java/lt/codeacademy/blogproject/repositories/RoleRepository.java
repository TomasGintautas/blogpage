package lt.codeacademy.blogproject.repositories;

import lt.codeacademy.blogproject.repositories.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> getRoleByRoleName(String name);
}

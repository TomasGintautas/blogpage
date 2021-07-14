package lt.codeacademy.blogproject.repositories;

import lt.codeacademy.blogproject.repositories.dao.DrinkCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkCategoryRepository extends JpaRepository<DrinkCategory, Long> {

    public DrinkCategory getDrinkCategoryByCategoryName(String name);
}

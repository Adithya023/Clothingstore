package repository.custom;

import entity.CategoryEntity;
import repository.SuperRepository;

import java.util.List;

public interface CategoryRepository extends SuperRepository {
    List<CategoryEntity> findAll();

    CategoryEntity findByName(String name);

    CategoryEntity findByCategoryID(String categoryID);
}

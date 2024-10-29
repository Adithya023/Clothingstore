package service.custom.impl;

import entity.CategoryEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import repository.RepositoryFactory;
import repository.custom.CategoryRepository;
import service.custom.CategoryService;
import util.Type;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository= RepositoryFactory.getInstance().getRepositoryType(Type.CATEGORY);

    @Override
    public ObservableList<Category> getAllCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        ObservableList<Category> categoryObservableList= FXCollections.observableArrayList();
        if (categoryEntityList!=null){
            categoryEntityList.forEach(entity-> categoryObservableList.add(new Category(entity.getCategoryId(), entity.getName())));
        }
        return categoryObservableList;
    }

    @Override
    public Category findCategoryByName(String name) {
        CategoryEntity categoryEntity = categoryRepository.findByName(name);
        if(categoryEntity !=null){
            return new Category(categoryEntity.getCategoryId(), categoryEntity.getName());
        }
        return new Category();
    }
}

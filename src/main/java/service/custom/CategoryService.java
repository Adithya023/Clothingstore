package service.custom;

import javafx.collections.ObservableList;
import model.Category;
import service.SuperService;

public interface CategoryService extends SuperService {
    ObservableList<Category> getAllCategories();

    Category findCategoryByName(String name);
}

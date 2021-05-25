package service;
import entity.Category;
import repository.GenericModel;
import java.util.List;

public class CategoriesService {
    private GenericModel<Category> categoryRepository;

    public CategoriesService() {
        categoryRepository = new GenericModel<>(Category.class);
    }

    public boolean create(Category category){
        if (category.isValid()){
            category.setStatus(1);
            return categoryRepository.save(category);
        }
        return false;
    };

    public List<Category> getList() {
        return categoryRepository.findAll();
    }
    public Category getById(int id) {
        return categoryRepository.findById(id);
    }
}

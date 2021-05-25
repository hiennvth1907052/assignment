package service;

import entity.Food;
import repository.GenericModel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class FoodService {
    private GenericModel<Food> foodRepository;

    java.sql.Date date = new java.sql.Date(System.currentTimeMillis());

    public FoodService() {
        foodRepository = new GenericModel<>(Food.class);
    }

    public boolean create(Food obj) {
        obj.setId(obj.getId());
        obj.setStartDate(date);
        obj.setModifiedDate(date);
        obj.setStatus(1);
        return foodRepository.save(obj);
    }

    public List<Food> getList(){
        return foodRepository.findAll();
    }

    public Food getById(int id){
        return foodRepository.findById(id);
    }

    public boolean edit(int id, Food food) {
        if (food.isValid()){
            return foodRepository.update(id, food);
        }
        return false;
    }

    public boolean delete(int id, Food food){
        food.setStatus(0);
        return foodRepository.update(id, food);
    }
}

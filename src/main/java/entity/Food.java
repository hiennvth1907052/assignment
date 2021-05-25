package entity;

import hannotation.Column;

import hannotation.Entity;
import hannotation.Id;
import helper.SQLConstant;
import helper.ValidateConstant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@Entity(tableName = "foods")
public class Food {
    @Id(autoIncrement = true)
    @Column(columnName = "id", columnType = "int")
    private int id;
    @Column(columnName = "name", columnType = "varchar(255)")
    private String name;
    @Column(columnName = "categoriesId", columnType = "int")
    private int categoriesId;
    @Column(columnName = "descriptions", columnType = "LONGTEXT")
    private String descriptions;
    @Column(columnName = "avatar", columnType = "LONGTEXT")
    private String avatar;
    @Column(columnName = "price", columnType = "double")
    private double price;
    @Column(columnName = "startDate", columnType = "date")
    private Date startDate;
    @Column(columnName = "modifiedDate", columnType = "date")
    private Date modifiedDate;
    @Column(columnName = "status", columnType = "int")
    private int status;



    public Food() {
    }

    public Food(int id, String name, int categoriesId, String descriptions, String avatar, double price, Date startDate, Date modifiedDate, int status) {
        this.id = id;
        this.name = name;
        this.categoriesId = categoriesId;
        this.descriptions = descriptions;
        this.avatar = avatar;
        this.price = price;
        this.startDate = startDate;
        this.modifiedDate = modifiedDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(int categoriesId) {
        this.categoriesId = categoriesId;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private HashMap<String, ArrayList<String>> errors = new HashMap<>();

    public HashMap<String, ArrayList<String>> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, ArrayList<String>> errors) {
        this.errors = errors;
    }

    public boolean isValid() {
        checkName();
        checkPrice();
        checkCategories();
        return errors.size() == 0;
    }

    private boolean checkName() {
        ArrayList<String> nameErrors = new ArrayList<>();
        if (this.name == null){
            nameErrors.add(ValidateConstant.FOOD_FIELNAME_NAME);
            errors.put(ValidateConstant.FOOD_MESSAGE_NAME_REQUIRED, nameErrors);
        }
        if (this.name.length() < 7){
            nameErrors.add(ValidateConstant.FOOD_FIELNAME_NAME);
            errors.put(ValidateConstant.FOOD_MESSAGE_NAME_MIN, nameErrors);
        }
        return nameErrors.size() == 0;
    }

    private boolean checkPrice() {
        ArrayList<String> priceErrors = new ArrayList<>();
        if (this.price <= 0){
            priceErrors.add(ValidateConstant.FOOD_FIELNAME_NAME);
            errors.put(ValidateConstant.FOOD_MESSAGE_NAME_MIN, priceErrors);
        }
        return priceErrors.size() == 0;
    }

    private boolean checkCategories() {
        ArrayList<String> categoriesErrors = new ArrayList<>();
        if (this.categoriesId == 0){
            categoriesErrors.add(ValidateConstant.FOOD_FIELNAME_CATEGORIES);
            errors.put(ValidateConstant.FOOD_MESSAGE_CATEGORIES_REQUIRED, categoriesErrors);
        }
        return categoriesErrors.size() == 0;
    }
}

package entity;

import hannotation.Column;
import hannotation.Entity;
import hannotation.Id;
import helper.ValidateConstant;

import java.util.ArrayList;
import java.util.HashMap;

@Entity(tableName = "categories")
public class Category {
    @Id(autoIncrement = true)
    @Column(columnName = "id", columnType = "int")
    private int id;
    @Column(columnName = "name", columnType = "varchar(255)")
    private String name;
    @Column(columnName = "status", columnType = "int")
    private int status;

    private HashMap<String, ArrayList<String>> errors = new HashMap<>();

    public Category() {
    }

    public Category(String name, int status) {
        this.name = name;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isValid() {
        checkName();
        return errors.size() == 0;
    }

    private boolean checkName() {
        ArrayList<String> nameErrors = new ArrayList<>();
        if (this.name == null){
            nameErrors.add(ValidateConstant.CATEGORY_FIELNAME_NAME);
            this.errors.put(ValidateConstant.CATEGORY_MESSAGE_NAME_REQUIRED, nameErrors);
        }
        return nameErrors.size() == 0;
    }

    public HashMap<String, ArrayList<String>> getErrors() {
        return new HashMap<>();
    }
}

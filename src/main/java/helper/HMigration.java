package helper;

import entity.Category;
import hannotation.Column;
import hannotation.Entity;
import hannotation.Id;
import org.reflections.Reflections;
import repository.GenericModel;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Set;

public class HMigration {
    public static void main(String[] args) throws SQLException {
        Reflections reflections = new Reflections("entity");
        Set<Class<?>> allClasses =
                reflections.getTypesAnnotatedWith(Entity.class);
        for (Class<?> c :
                allClasses) {
            registerClass(c);
        }
        GenericModel genericModel = new GenericModel(Category.class);
        Category category1 = new Category();
        Category category2 = new Category();
        Category category3 = new Category();
        Category category4 = new Category();
        category1.setName("Món nướng");
        category2.setName("Món luộc");
        category3.setName("Món chay");
        category4.setName("Đồ uống");
        category1.setStatus(1);
        category2.setStatus(1);
        category3.setStatus(1);
        category4.setStatus(1);
        genericModel.save(category1);
        genericModel.save(category2);
        genericModel.save(category3);
        genericModel.save(category4);

    }

    public static void registerClass(Class clazz) throws SQLException {
        try {
            if (!clazz.isAnnotationPresent(Entity.class)) {
                return;
            }
            Entity entityInfor = (Entity) clazz.getAnnotation(Entity.class);
            StringBuilder strQuery = new StringBuilder();
            strQuery.append(SQLConstant.CREATE_TABLE);
            strQuery.append(SQLConstant.SPACE);
            strQuery.append(entityInfor.tableName());
            strQuery.append(SQLConstant.SPACE);
            strQuery.append(SQLConstant.OPEN_PARENTHESES);
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }
                Column columnInfor = (Column) field.getAnnotation(Column.class);
                strQuery.append(columnInfor.columnName());
                strQuery.append(SQLConstant.SPACE);
                strQuery.append(columnInfor.columnType());
                if (field.isAnnotationPresent(Id.class)) {
                    Id idInfor = (Id) field.getAnnotation(Id.class);
                    strQuery.append(SQLConstant.SPACE);
                    strQuery.append(SQLConstant.PRIMARY_KEY);
                    if (idInfor.autoIncrement()) {
                        strQuery.append(SQLConstant.SPACE);
                        strQuery.append(SQLConstant.AUTO_INCREMENT);
                    }
                }
                strQuery.append(SQLConstant.COMMON);
                strQuery.append(SQLConstant.SPACE);
            }
            strQuery.setLength(strQuery.length() - 2);
            strQuery.append(SQLConstant.CLOSE_PARENTHESES);
            ConnectionHelper.getConnection().createStatement().execute(strQuery.toString());
            System.out.printf("Tạo bảng %s thành công.\n", entityInfor.tableName());
        } catch (java.sql.SQLSyntaxErrorException sqlSyntaxErrorException) {
            System.err.printf("Có lỗi xảy ra trong quá trình tạo bảng. Error %s.\n", sqlSyntaxErrorException.getMessage());
        }
    }
}

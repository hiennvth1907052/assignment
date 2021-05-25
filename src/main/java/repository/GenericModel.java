package repository;

import hannotation.Column;
import hannotation.Entity;
import hannotation.Id;
import helper.ConnectionHelper;
import helper.ConvertHelper;
import helper.SQLConstant;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenericModel<T> {
    private Class<T> clazz;

    public GenericModel(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            System.err.printf("Class %s không được đăng ký làm việc với database.", clazz.getSimpleName());
            return;
        }
        this.clazz = clazz;
    }

    public boolean save(T obj) {
        try {
            // Lấy ra giá trị của annotation @Entity vì cần những thông tin liên quan đến tableName.
            Entity entityInfor = clazz.getAnnotation(Entity.class);
            // Build lên câu query string.
            StringBuilder strQuery = new StringBuilder();
            // Build chuỗi chứa giá trị các trường tương ứng.
            StringBuilder fieldValues = new StringBuilder();
            fieldValues.append(SQLConstant.OPEN_PARENTHESES);
            // Xây dựng câu lệnh insert theo tên bảng, theo tên các field cùa đối tượng truyền vào.
            strQuery.append(SQLConstant.INSERT_INTO); // insert into
            strQuery.append(SQLConstant.SPACE); //
            strQuery.append(entityInfor.tableName()); // giangvien
            strQuery.append(SQLConstant.SPACE); //
            strQuery.append(SQLConstant.OPEN_PARENTHESES); // (
            for (Field field : clazz.getDeclaredFields()) {
                // check xem trường có phải là @Column không.
                if (!field.isAnnotationPresent(Column.class)) {
                    // bỏ qua trong trường hợp không được đánh là @Column.
                    continue;
                }
                // cần set bằng true để có thể set, get giá trị của field trong một object nào đó.
                field.setAccessible(true);
                // lấy thông tin column để check tên trường, kiểu giá trị của trường.
                // Không lấy danh sách column theo tên field mà lấy theo annotation đặt tại field đó.
                Column columnInfor = field.getAnnotation(Column.class);
                // check xem trường có phải là id không.
                if (field.isAnnotationPresent(Id.class)) {
                    // lấy thông tin id.
                    Id idInfor = field.getAnnotation(Id.class);
                    if (idInfor.autoIncrement()) {
                        // trường hợp đây là trường tự tăng, thì next sang trường tiếp theo.
                        continue;
                    }
                }
                strQuery.append(columnInfor.columnName()); // nối tên trường.
                strQuery.append(SQLConstant.COMMON); //,
                strQuery.append(SQLConstant.SPACE); //
                // nhanh trí, xử lý luôn phần value, tránh sử dụng 2 vòng lặp.
                // check kiểu của trường, nếu là string thì thêm dấu '
                if (field.getType().getSimpleName().equals(String.class.getSimpleName()) ||
                        field.getType().getSimpleName().equals(Date.class.getSimpleName())) {
                    fieldValues.append(SQLConstant.QUOTE);
                }
                // lấy ra thông tin giá trị của trường đó tại obj truyền vào.
                fieldValues.append(field.get(obj)); // field.setAccessible(true);
                // check kiểu của trường, nếu là string thì thêm dấu '
                if (field.getType().getSimpleName().equals(String.class.getSimpleName())||
                        field.getType().getSimpleName().equals(Date.class.getSimpleName())) {
                    fieldValues.append(SQLConstant.QUOTE);
                }
                fieldValues.append(SQLConstant.COMMON); //,
                fieldValues.append(SQLConstant.SPACE); //
            }
            strQuery.setLength(strQuery.length() - 2); // trường hợp là field cuối cùng thì bỏ dấu , và khoảng trắng đi.
            fieldValues.setLength(fieldValues.length() - 2);
            strQuery.append(SQLConstant.CLOSE_PARENTHESES); // )
            fieldValues.append(SQLConstant.CLOSE_PARENTHESES); // )
            strQuery.append(SQLConstant.SPACE);
            strQuery.append(SQLConstant.VALUES); // values
            strQuery.append(SQLConstant.SPACE);
            strQuery.append(fieldValues); // nối giá trị các trường vào.
            return ConnectionHelper.getConnection().createStatement().execute(strQuery.toString());
        } catch (IllegalAccessException | SQLException e) {
            System.err.printf("Có lỗi xảy ra trong quá trình làm việc với database. Error %s.\n", e.getMessage());
        }
        return true;
    }
    public final List<T> getAll() {
        List<T> result = new ArrayList<>(); // khởi tạo một danh sách rỗng.
        StringBuilder stringQuery = new StringBuilder();
        stringQuery.append("select * from categories where status = 1");
        try {
            PreparedStatement preparedStatement = ConnectionHelper.getConnection().prepareStatement(stringQuery.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            Field[] fields = clazz.getDeclaredFields(); //
            while (resultSet.next()) {
                T obj = clazz.newInstance();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(Column.class)) {
                        continue;
                    }
                    field.setAccessible(true);
                    Column columnInfor = field.getAnnotation(Column.class);
                    switch (field.getType().getSimpleName()) {
                        case SQLConstant.PRIMITIVE_INT:
                            field.set(obj, resultSet.getInt(columnInfor.columnName()));
                            break;
                        case SQLConstant.PRIMITIVE_STRING:
                            field.set(obj, resultSet.getString(columnInfor.columnName()));
                            break;
                        case SQLConstant.PRIMITIVE_DOUBLE:
                            field.set(obj, resultSet.getDouble(columnInfor.columnName()));
                            break;
                        case SQLConstant.DATE:
                            field.set(obj, resultSet.getDate(columnInfor.columnName()));
                            break;
                        case SQLConstant.DATETIME:
                            field.set(obj, ConvertHelper.convertSqlTimeStampToJavaDate(resultSet.getTimestamp(columnInfor.columnName())));
                        default:
                            break;
                    }
                }
                result.add(obj);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            System.err.printf("55555555.\n");
            System.err.printf("Có lỗi xảy ra trong quá trình làm việc với database. Error %s.\n", e.getMessage());
        }
        return result;
    }
    public final List<T> findAll() {
        List<T> result = new ArrayList<>();
        Entity entityInfor = clazz.getAnnotation(Entity.class);
        StringBuilder stringQuery = new StringBuilder();
        stringQuery.append(SQLConstant.SELECT_ASTERISK); // select *
        stringQuery.append(SQLConstant.SPACE);
        stringQuery.append(SQLConstant.FROM); // from
        stringQuery.append(SQLConstant.SPACE);
        stringQuery.append(entityInfor.tableName()); // tableName
        stringQuery.append(SQLConstant.SPACE);
        stringQuery.append(SQLConstant.WHERE);
        stringQuery.append(SQLConstant.SPACE);
        stringQuery.append("status");
        stringQuery.append(SQLConstant.SPACE);
        stringQuery.append(SQLConstant.EQUAL);
        stringQuery.append(SQLConstant.SPACE);
        stringQuery.append("1");
        try {
            PreparedStatement preparedStatement = ConnectionHelper.getConnection().prepareStatement(stringQuery.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            Field[] fields = clazz.getDeclaredFields(); //
            while (resultSet.next()) {
                T obj = clazz.newInstance();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(Column.class)) {
                        continue;
                    }
                    field.setAccessible(true);
                    Column columnInfor = field.getAnnotation(Column.class);
                    switch (field.getType().getSimpleName()) {
                        case SQLConstant.PRIMITIVE_INT:
                            field.set(obj, resultSet.getInt(columnInfor.columnName()));
                            break;
                        case SQLConstant.PRIMITIVE_STRING:
                            field.set(obj, resultSet.getString(columnInfor.columnName()));
                            break;
                        case SQLConstant.PRIMITIVE_DOUBLE:
                            field.set(obj, resultSet.getDouble(columnInfor.columnName()));
                            break;
                    }
                }
                result.add(obj);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            System.err.printf("findall.\n");
            System.err.printf("Có lỗi xảy ra trong quá trình làm việc với database. Error %s.\n", e.getMessage());
        }
        return result;
    }

    public T findById(Object id) {
        Entity entity = clazz.getAnnotation(Entity.class);
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder fieldValues = new StringBuilder();
        stringBuilder.append(SQLConstant.SELECT_ASTERISK);
        stringBuilder.append(SQLConstant.SPACE);
        stringBuilder.append(SQLConstant.FROM);
        stringBuilder.append(SQLConstant.SPACE);
        stringBuilder.append(entity.tableName());
        stringBuilder.append(SQLConstant.SPACE);
        stringBuilder.append(SQLConstant.WHERE);
        stringBuilder.append(SQLConstant.SPACE);
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }
            field.setAccessible(true);

            Column columnInfor = field.getAnnotation(Column.class);
            if (field.isAnnotationPresent(Id.class)) {
                stringBuilder.append(columnInfor.columnName());
                stringBuilder.append(SQLConstant.SPACE);
                stringBuilder.append(SQLConstant.EQUAL);
                stringBuilder.append(SQLConstant.SPACE); //
                if (field.getType().getSimpleName().equals(String.class.getSimpleName())) {
                    fieldValues.append(SQLConstant.QUOTE);
                }
                fieldValues.append(id);
                if (field.getType().getSimpleName().equals(String.class.getSimpleName())) {
                    fieldValues.append(SQLConstant.QUOTE);
                }
                fieldValues.append(SQLConstant.SPACE); //
                stringBuilder.append(fieldValues); //
            }
        }
        try {
            System.out.println(stringBuilder.toString());
            PreparedStatement preparedStatement = ConnectionHelper.getConnection().prepareStatement(stringBuilder.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            Field[] fields = clazz.getDeclaredFields(); //
            while (resultSet.next()) {
                T obj = clazz.newInstance();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(Column.class)) {
                        continue;
                    }
                    field.setAccessible(true);
                    Column columnInfor = field.getAnnotation(Column.class);
                    switch (field.getType().getSimpleName()) {
                        case SQLConstant.PRIMITIVE_INT:
                            field.set(obj, resultSet.getInt(columnInfor.columnName()));
                            break;
                        case SQLConstant.PRIMITIVE_STRING:
                            field.set(obj, resultSet.getString(columnInfor.columnName()));
                            break;
                        case SQLConstant.PRIMITIVE_DOUBLE:
                            field.set(obj, resultSet.getDouble(columnInfor.columnName()));
                            break;
                        case SQLConstant.DATE:
                            field.set(obj, resultSet.getDate(columnInfor.columnName()));
                            break;
                        case SQLConstant.DATETIME:
                            field.set(obj, ConvertHelper.convertSqlTimeStampToJavaDate(resultSet.getTimestamp(columnInfor.columnName())));
                        default:
                            break;
                    }
                }
                return obj;
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            System.err.printf("findid.\n");
            System.err.printf("Có lỗi xảy ra trong quá trình làm việc với database. Error %s.\n", e.getMessage());
        }
        return null;
    }

    public boolean update(int id, T obj) {
        Entity entityInfor = clazz.getAnnotation(Entity.class);
        StringBuilder strQuery = new StringBuilder();
        StringBuilder fieldValues = new StringBuilder();

        strQuery.append(SQLConstant.UPDATE);
        strQuery.append(SQLConstant.SPACE);
        strQuery.append(entityInfor.tableName());
        strQuery.append(SQLConstant.SPACE);
        strQuery.append(SQLConstant.SET);
        strQuery.append(SQLConstant.SPACE);
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }
            field.setAccessible(true);

            Column columnInfor = field.getAnnotation(Column.class);
            if (!field.isAnnotationPresent(Id.class)) {
                strQuery.append(columnInfor.columnName());
                strQuery.append(SQLConstant.SPACE);
                strQuery.append(SQLConstant.EQUAL);
                strQuery.append(SQLConstant.SPACE);

                if (field.getType().getSimpleName().equals(String.class.getSimpleName())||
                        field.getType().getSimpleName().equals(Date.class.getSimpleName())) {
                    strQuery.append(SQLConstant.QUOTE);
                }
                try {
                    strQuery.append(field.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (field.getType().getSimpleName().equals(String.class.getSimpleName())||
                        field.getType().getSimpleName().equals(Date.class.getSimpleName())) {
                    strQuery.append(SQLConstant.QUOTE);
                }
                strQuery.append(SQLConstant.COMMON);
                strQuery.append(SQLConstant.SPACE);
            }
        }
        strQuery.setLength(strQuery.length() - 2);
        strQuery.append(SQLConstant.SPACE);
        strQuery.append(SQLConstant.WHERE);
        strQuery.append(SQLConstant.SPACE);
        for (Field field1 : clazz.getDeclaredFields()) {
            if (!field1.isAnnotationPresent(Column.class)) {
                continue;
            }
            field1.setAccessible(true);
            Column columnInfor = field1.getAnnotation(Column.class);
            if (field1.isAnnotationPresent(Id.class)) {
                strQuery.append(columnInfor.columnName());
                strQuery.append(SQLConstant.SPACE);
                strQuery.append(SQLConstant.EQUAL);
                strQuery.append(SQLConstant.SPACE);
                if (field1.getType().getSimpleName().equals(String.class.getSimpleName())||
                        field1.getType().getSimpleName().equals(Date.class.getSimpleName())) {
                    fieldValues.append(SQLConstant.QUOTE);
                }
                fieldValues.append(id);
                if (field1.getType().getSimpleName().equals(String.class.getSimpleName())||
                        field1.getType().getSimpleName().equals(Date.class.getSimpleName())) {
                    fieldValues.append(SQLConstant.QUOTE);
                }
                fieldValues.append(SQLConstant.SPACE);
                strQuery.append(fieldValues);
            }
        }
        try {
            ConnectionHelper.getConnection().createStatement().execute(strQuery.toString());
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj != null;
    }
}

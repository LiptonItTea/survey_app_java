package org.liptonit.db;

import org.liptonit.entity.DBEntity;
import org.liptonit.util.Patcher;
import org.liptonit.util.Sanitaizer;
import org.liptonit.util.SearchCondition;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PostgreDatabase extends Database{
    private Connection connection;

    public PostgreDatabase() {
        this.connection = JDBC.getConnection();
    }

    @Override
    protected <T extends DBEntity> T createEntity(Class<T> entityClass, T entity) {
        String tableName = getTableName(entityClass);

        var result = createEntitySql(entityClass, entity, tableName);
        String sql = result.getKey();
        List<Object> values = result.getValue();

        System.out.println(sql);

        String readSql;
        try (var stmt = connection.prepareStatement(sql, new String[]{"id"})) {
            for (int i = 0; i < values.size(); i++)
                stmt.setObject(i + 1, values.get(i));
            stmt.executeUpdate();
            ResultSet gkeys = stmt.getGeneratedKeys();

            readSql = readByIdsSql(gkeys, tableName);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (var stmt = connection.prepareStatement(readSql)) {
            ResultSet entitySet = stmt.executeQuery();

            if (entitySet.next())
                return entityClass.getConstructor(ResultSet.class).newInstance(entitySet);
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Looks like your entity class is missing constructor, or your constructor is not public, or your class doesn't have constructor(Long, DBEntity). You dumbass.");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    protected <T extends DBEntity> T readEntityById(Class<T> entityClass, long id) {
        String readSql = "SELECT * FROM " + getTableName(entityClass) + "\nWHERE id=?";

        try (var stmt = connection.prepareStatement(readSql, new String[]{"id"})) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return entityClass.getConstructor(ResultSet.class).newInstance(rs);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    protected <T extends DBEntity> List<T> readEntities(Class<T> entityClass, SearchCondition<T> condition) {
        String readSql = "SELECT * FROM " + getTableName(entityClass) + ";";

        List<T> result = new ArrayList<>();
        try (var stmt = connection.prepareStatement(readSql)) {
            stmt.setFetchSize(1000);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                T e = entityClass.getConstructor(ResultSet.class).newInstance(rs);

                if (condition.select(e))
                    result.add(e);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    protected <T extends DBEntity> T updateEntityById(Class<T> entityClass, long id, Patcher<T> patcher) throws IllegalArgumentException {
        // todo maybe SELECT then DELETE then INSERT ?
        return null;
    }

    @Override
    protected <T extends DBEntity> List<T> updateEntities(Class<T> entityClass, SearchCondition<T> condition, Patcher<T> patcher) {
        // todo
        return List.of();
    }

    @Override
    protected <T extends DBEntity> T deleteEntityById(Class<T> entityClass, long id) {
        // todo classname -> sql
        return null;
    }

    @Override
    protected <T extends DBEntity> List<Long> deleteEntities(Class<T> entityClass, SearchCondition<T> condition) {
        // todo classname -> sql (SELECT * ), obj -> sql, then filter neccessary objects, then delete
        return List.of();
    }

    public static <T extends DBEntity> Map.Entry<String, List<Object>> createEntitySql(Class<T> entityClass, T entity, String tableName) {
        // reflectively build SQL
        var fields = entityClass.getDeclaredFields();
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");

        for (int i = 0; i < fields.length - 1; i++) {
            var f = fields[i];

            if (f.getName().equalsIgnoreCase("id") ||
                f.getName().equalsIgnoreCase("registrationDate")) continue;
            f.setAccessible(true);
            sql.append(Sanitaizer.convertCamelCaseToSnakeRegex(f.getName())).append(",");
        }
        sql.append(Sanitaizer.convertCamelCaseToSnakeRegex(fields[fields.length - 1].getName())).append(")\nVALUES\n");

        List<Object> values = new ArrayList<>();
        sql.append("(");
        for (var f : fields) {
            if (f.getName().equalsIgnoreCase("id") ||
                    f.getName().equalsIgnoreCase("registrationDate")) continue;
            f.setAccessible(true);

            try {
                sql.append("?,");
                values.add(f.get(entity));
            }
            catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        sql.deleteCharAt(sql.length() - 1).append(")");

        return new AbstractMap.SimpleEntry<>(sql.toString(), values);
    }

    private String readByIdsSql(ResultSet generatedKeys, String tableName) throws SQLException {
        StringBuilder builder = new StringBuilder();

        builder.append("SELECT * FROM ").append(tableName).append("\nWHERE id=(");
        while (generatedKeys.next()) {
            builder.append(generatedKeys.getLong(1)).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(");");

        return builder.toString();
    }

    private <T extends DBEntity> String getTableName(Class<T> entityClass) {
        String tableName = Sanitaizer.convertCamelCaseToSnakeRegex(entityClass.getSimpleName());
        if (tableName.equalsIgnoreCase("user")) // i'm sorry john
            tableName = "user_accs";

        return tableName;
    }

}

package com.test.dao;
import com.test.pojo.User;
import java.util.List;

public class UserDao extends BaseDao {
    public int addUser(User user) {
        String sql = "insert into user values(DEFAULT,?,?)";
        int rows = baseUpdate(sql,user.getUsername(),user.getPassword());
        return rows;
    }

    public User getUserByUsername(String username) {
        String sql = "select * from user where username = ?";
        List<User> user = baseQuery(User.class,sql,username);
        return user.isEmpty() ? null : user.get(0);
    }

}

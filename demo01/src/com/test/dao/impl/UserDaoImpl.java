package com.test.dao.impl;
import com.test.dao.BaseDao;
import com.test.dao.UserDao;
import com.test.pojo.Merchant;
import com.test.pojo.Normaluser;
import com.test.pojo.User;

import java.util.List;


public class UserDaoImpl extends BaseDao implements UserDao {


    @Override
    public int addNormal(User user) {
        int rows1, rows2;
        String sql = "insert into user values(DEFAULT,?,?,?)";
        rows1 = baseUpdate(sql, user.getType(), user.getUsername(), user.getPassword());
        String sql2 = "insert into normaluser values(DEFAULT,?,?,?,'normalDefault.png')";
        rows2 = baseUpdate(sql2, user.getUsername(), user.getPassword(), user.getSex());
        if (rows1 > 0 && rows2 > 0) {
            return rows1;
        } else {
            return 0;
        }
    }

    @Override
    public int addMerchant(User user) {
        int rows1, rows2;
        String sql = "insert into user values(DEFAULT,?,?,?)";
        rows1 = baseUpdate(sql, user.getType(), user.getUsername(), user.getPassword());
        String sql2 = "insert into merchant values(DEFAULT,?,?,?,?,'merchantDefault.png')";
        rows2 = baseUpdate(sql2, user.getUsername(), user.getPassword(), user.getName(), user.getDescription());
        if (rows1 > 0 && rows2 > 0) {
            return rows1;
        } else {
            return 0;
        }
    }

    @Override
    public int updateNormal(User user) {
        int rows,rows1,rows2,rows3;
        if (!(user.getPassword().equals("") || user.getPassword() == null)) {
            String sql1 = "update user set password = ? where username = ?";
            rows1 = baseUpdate(sql1, user.getPassword(), user.getUsername());
            String sql2 = "update normaluser set password = ? where username = ?";
            rows2 = baseUpdate(sql2, user.getPassword(), user.getUsername());
        }else{
            rows1 = rows2 = 1;
        }
        String sql3 = "update normaluser set sex =?,img = ? where username = ?";
        rows3 = baseUpdate(sql3, user.getSex(), user.getImg(), user.getUsername());
        if (rows1 > 0 && rows2 > 0 && rows3 > 0) {
            rows = 1;
        }else {
            rows = 0;
        }
        return rows;
    }

    @Override
    public int updateMerchant(User user) {
        int rows,rows1,rows2,rows3;
        if (!(user.getPassword().equals("") || user.getPassword() == null)) {
            String sql1 = "update user set password = ? where username = ?";
            rows1 = baseUpdate(sql1, user.getPassword(), user.getUsername());
            String sql2 = "update merchant set password = ? where username = ?";
            rows2 = baseUpdate(sql2, user.getPassword(), user.getUsername());
        }else {
            rows1 = rows2 = 1;
        }
        String sql3 = "update merchant set name = ? ,description = ?,logo = ? where username = ?";
        rows3 = baseUpdate(sql3, user.getName(), user.getDescription(), user.getImg(), user.getUsername());
        if (rows1 > 0 && rows2 > 0 && rows3 > 0) {
            rows = rows3;
        }else {
            rows = 0;
        }
        return rows;
    }

    public User getUserByUsername(String username) {
        String sql = "select * from user where username = ?";
        List<User> user = baseQuery(User.class, sql, username);
        return user.isEmpty() ? null : user.get(0);
    }

    @Override
    public int addCollect(Integer uid, Integer sid) {
        String sql = "insert into collect values(?,?)";
        return baseUpdate(sql, uid, sid);
    }

    @Override
    public List<Merchant> findCollect(Integer uid) {
        String sql = "select name,description,logo from collect,merchant where user_id = ? and merchant.id = store_id";
        return baseQuery(Merchant.class, sql, uid);
    }

}
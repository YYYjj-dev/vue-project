package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.MerchantDao;
import com.test.pojo.Merchant;
import java.util.List;
import java.util.Map;

public class MerchantDaoImpl extends BaseDao implements MerchantDao {

    @Override
    public Merchant findMerchantByUsername(String username) {
        String sql = "select * from merchant where username = ?";
        List<Merchant> merchant = baseQuery(Merchant.class,sql,username);
        return merchant.isEmpty() ? null : merchant.get(0);
    }

    @Override
    public List<Merchant> findAllMerchants() {
        String sql = "select * from merchant";
        return baseQuery(Merchant.class,sql);
    }

    @Override
    public List<Merchant> findMerchantByType(String type) {
        String sql = "select * from merchant where type = ?";
        return baseQuery(Merchant.class,sql,type);
    }

    @Override
    public int addMerchant(Merchant merchant) {
        String sql = "insert into merchant values(DEFAULT,?,?,?,?,?,?)";
        return baseUpdate(sql,merchant.getUsername(),merchant.getName(),merchant.getDescription(),merchant.getLogo(),merchant.getType());
    }

    @Override
    public int updateMerchant(Merchant merchant) {
        String sql = "update merchant set username = ?,name = ?,description = ?,logo = ?,type = ? where id = ?";
        return baseUpdate(sql,merchant.getUsername(),merchant.getName(),merchant.getDescription(),merchant.getLogo(),merchant.getType(),merchant.getId());
    }

    @Override
    public int deleteMerchant(Integer id) {
        String sql = "delete from merchant where id = ?";
        return baseUpdate(sql,id);
    }

    @Override
    public List<Merchant> findMerchant(Map<String, Object> queryParams) {
        String sql = buildQuery(queryParams,"*","merchant");
        return baseQuery(Merchant.class,sql);
    }

    @Override
    public Merchant findMerchantById(Integer id) {
        String sql = "select * from merchant where id = ?";
        List<Merchant> merchant = baseQuery(Merchant.class,sql,id);
        return merchant.isEmpty() ? null : merchant.get(0);
    }

    @Override
    public Merchant findMerchantBySid(Integer id) {
        String sql = "select merchant.id,username,merchant.name,merchant.description,logo,merchant.type from merchant,shangpin where store_id = merchant.id and shangpin.id = ?";
        List<Merchant> merchant = baseQuery(Merchant.class,sql,id);
        return merchant.isEmpty() ? null : merchant.get(0);
    }


}

package com.test.service.impl;

import com.test.dao.MerchantDao;
import com.test.dao.impl.MerchantDaoImpl;
import com.test.pojo.Merchant;
import com.test.service.MerchantService;

import java.util.List;

public class MerchantServiceImpl implements MerchantService {

    private MerchantDao merchantDao = new MerchantDaoImpl();
    @Override
    public Merchant findMerchantByUsername(String username) {
        return merchantDao.findMerchantByUsername(username);
    }

    @Override
    public List<Merchant> findAllMerchants() {
        return merchantDao.findAllMerchants();
    }

    @Override
    public List<Merchant> findMerchantByType(Integer type) {
        return merchantDao.findMerchantByType(type);
    }

    @Override
    public int addMerchant(Merchant merchant) {
        return merchantDao.addMerchant(merchant);
    }

    @Override
    public int updateMerchant(Merchant merchant) {
        return merchantDao.updateMerchant(merchant);
    }

    @Override
    public int deleteMerchant(String id) {
        return merchantDao.deleteMerchant(id);
    }

}

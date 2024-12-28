package com.test.dao;

import com.test.pojo.Merchant;
import java.util.List;

public interface MerchantDao {

    Merchant findMerchantByUsername(String username);

    List<Merchant> findAllMerchants();

    List<Merchant> findMerchantByType(String type);

    int addMerchant(Merchant merchant);

    int updateMerchant(Merchant merchant);

    int deleteMerchant(Integer id);
}

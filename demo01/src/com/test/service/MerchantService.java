package com.test.service;

import com.test.pojo.Merchant;

import java.util.List;

public interface MerchantService {

    Merchant findMerchantByUsername(String username);

    List<Merchant> findAllMerchants();

    List<Merchant> findMerchantByType(Integer type);

    int addMerchant(Merchant merchant);

    int updateMerchant(Merchant merchant);

    int deleteMerchant(String id);
}

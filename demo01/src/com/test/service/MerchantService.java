package com.test.service;

import com.test.pojo.Merchant;
import com.test.pojo.News;

import java.util.List;
import java.util.Map;

public interface MerchantService {

    Merchant findMerchantByUsername(String username);

    List<Merchant> findAllMerchants();

    List<Merchant> findMerchantByType(String type);

    int addMerchant(Merchant merchant);

    int updateMerchant(Merchant merchant);

    int deleteMerchant(Integer id);

    List<Merchant> findMerchant(Map<String, Object> queryParams);

    Merchant findMerchantById(Integer id);

    Merchant findMerchantBySid(Integer id);
}

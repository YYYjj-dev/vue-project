package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.ShangpinDao;
import com.test.pojo.Score;
import com.test.pojo.Shangpin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ShangpinDaoImpl extends BaseDao implements ShangpinDao {
    @Override
    public int addShangpin(Shangpin shangpin) {
        String sql = "insert into shangpin (id,store_id,`group`,type,name,description,standard,price,num,imgpath) " +
                "values(DEFAULT,?,?,?,?,?,?,?,0,?)";
        return baseUpdate(sql, shangpin.getStoreId(),
                shangpin.getGroup(), shangpin.getType(),
                shangpin.getName(), shangpin.getDescription(),
                shangpin.getStandard(), shangpin.getPrice(), shangpin.getImgpath());
    }

    @Override
    public int deleteShangpinById(Integer id) {
        String sql = "delete from shangpin where id = ?\n" +
                "delete form score where shangpin_id = ?";
        return baseUpdate(sql, id);
    }

    @Override
    public List<Shangpin> findShangpinByType(String type) {
        String sql = "select id,store_id storeId,`group`,type,name,description,standard,price,num,imgpath " +
                "from shangpin,score" +
                "where type = ?";
        List<Shangpin> shangpinList= baseQuery(Shangpin.class, sql, type);
        for (Shangpin shangpin : shangpinList) {
            shangpin.setScore(findScoreBySid(shangpin.getId()));
        }
        return shangpinList;
    }

    @Override
    public int updateShangpin(Shangpin shangpin) {
        String sql = """
                UPDATE shangpin
                SET `group` = ?,type=?,name=?,description=?,standard=?,price=?,imgpath=?
                WHERE id=?;""";
        return baseUpdate(sql, shangpin.getGroup(), shangpin.getType(), shangpin.getName(), shangpin.getDescription(), shangpin.getStandard(), shangpin.getPrice(), shangpin.getImgpath(), shangpin.getId());
    }

    @Override
    public Shangpin findShangpinById(int id) {
        String sql = "select id,store_id storeId,`group`,type,name,description,standard,price,num,imgpath " +
                "from shangpin " +
                "where id = ?";
        List<Shangpin> shangpinList = baseQuery(Shangpin.class, sql, id);
        if (!shangpinList.isEmpty()) {
            Shangpin shangpin = shangpinList.get(0);
            shangpin.setScore(findScoreBySid(shangpin.getId()));
            return shangpin;
        }else return null;
    }

    @Override
    public List<Shangpin> findShangpinByName(String name) {
        String sql = "select id,store_id storeId,`group`,type,name,description,standard,,price,num,,imgpath " +
                "from shangpin " +
                "where name like ?";
        List<Shangpin> shangpinList = baseQuery(Shangpin.class, sql, name);
        for (Shangpin shangpin : shangpinList) {
            shangpin.setScore(findScoreBySid(shangpin.getId()));
        }
        return shangpinList;
    }

    @Override
    public List<Shangpin> findShangpinByGroup(String group) {
        String sql = "select id,store_id storeId,`group`,type,name,description,standard,price,num,imgpath " +
                "from shangpin " +
                "where `group` = ? ";
        List<Shangpin> shangpinList = baseQuery(Shangpin.class, sql, group);
        for (Shangpin shangpin : shangpinList) {
            shangpin.setScore(findScoreBySid(shangpin.getId()));
        }
        return shangpinList;
    }

    @Override
    public List<Shangpin> findAllShangpin() {
        String sql = "select id,store_id storeId,`group`,type,name,description,standard,price,num,imgpath " +
                "from shangpin" ;
        List<Shangpin> shangpinList = baseQuery(Shangpin.class, sql);
        for (Shangpin shangpin : shangpinList) {
            shangpin.setScore(findScoreBySid(shangpin.getId()));
        }
        return shangpinList;
    }


    public List<Shangpin> findShangpinByMid(Integer Mid) {
        String sql = "select id,store_id storeId,`group`,type,name,description,standard,price,num,imgpath " +
                "from shangpin" +
                "where store_id = ?";
        List<Shangpin> shangpinList = baseQuery(Shangpin.class, sql, Mid);
        for (Shangpin shangpin : shangpinList) {
            shangpin.setScore(findScoreBySid(shangpin.getId()));
        }
        return shangpinList;
    }

    @Override
    public int addScore(Score score) {
        String sql = "insert score values(?,?)";
        return baseUpdate(sql, score.getShangpinId(), score.getScore());
    }

    @Override
    public String findScoreBySid(Integer sid) {
        String sql = "select Round(avg(score),2) score from score where shangpin_id = ?";
        return baseQueryObject(Double.class, sql, sid).toString();

    }
}

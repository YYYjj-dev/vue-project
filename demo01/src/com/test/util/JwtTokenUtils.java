package com.test.util;

import com.test.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtils {

    //用于签名的私钥
    private static final String PRIVATE_KEY = "myTokenKeyYuanCunJavaWeb20241229ProjectDemo01BackEndDevelopment";
    //签发者
    private static final String ISS = "JiaM";

    //过期时间1小时
    private static final long EXPIRATION_ONE_HOUR = 3600L;
    //过期时间1天
    private static final long EXPIRATION_ONE_DAY = 604800L;

    /**
     * 生成Token
     *
     * @param user
     * @return
     */
    public static String createToken(User user, int expireTimeType) {
        //过期时间
        long expireTime = 0;
        if (expireTimeType == 0) {
            expireTime = EXPIRATION_ONE_HOUR;
        } else {
            expireTime = EXPIRATION_ONE_DAY;
        }

        //Jwt头
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        Map<String, Object> claims = new HashMap<>();
        //自定义有效载荷部分
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("password", user.getPassword());
        claims.put("type", user.getType());
        claims.put("img", user.getImg());
        claims.put("gender", user.getGender());

        return Jwts.builder()
                //发证人
                .setIssuer(ISS)
                //Jwt头
                .setHeader(header)
                //有效载荷
                .setClaims(claims)
                //设定签发时间
                .setIssuedAt(new Date())
                //设定过期时间
                .setExpiration(new Date(System.currentTimeMillis() + expireTime * 1000))
                //使用HS256算法签名，PRIVATE_KEY为签名密钥
                .signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)
                .compact();
    }

    /**
     * 验证Token，返回数据只有id和account和type的User对象
     *
     * @param token
     * @return
     */
    public static User checkToken(String token) {
        //解析token后，从有效载荷取出值
        Integer id = (Integer) getClaimsFromToken(token).get("id");
        String type = (String) getClaimsFromToken(token).get("type");
        String username = (String) getClaimsFromToken(token).get("username");
        String password = (String) getClaimsFromToken(token).get("password");
        String img = (String) getClaimsFromToken(token).get("img");
        String gender = (String) getClaimsFromToken(token).get("gender");
        //封装为User对象
        User user = new User();
        user.setId(id);
        user.setType(type);
        user.setUsername(username);
        user.setPassword(password);
        user.setImg(img);
        user.setGender(gender);

        return user;
    }

    /**
     * 获取有效载荷
     *
     * @param token
     * @return
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    //设定解密私钥
                    .setSigningKey(PRIVATE_KEY)
                    //传入Token
                    .parseClaimsJws(token)
                    //获取载荷类
                    .getBody();
        } catch (Exception e) {
            return null;
        }
        return claims;
    }

}

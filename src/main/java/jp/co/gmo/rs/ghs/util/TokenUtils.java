/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import javax.crypto.spec.SecretKeySpec;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dto.UserSessionDto;
import jp.co.gmo.rs.ghs.model.DataConfig;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * WebUtils
 *
 * @author LongVNH
 * @version 1.0
 */
public class TokenUtils {

    private static final Logger LOGGER = Logger.getLogger(TokenUtils.class);

    private static String secretKey = DataConfig.getProperty().getProperty("secret.key");

    private static String algorithm = DataConfig.getProperty().getProperty("algorithm");

    /**
     * Constructor
     */
    protected TokenUtils() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * createToken
     *
     * @param pathServlet
     * @param userId
     * @param userName
     * @param uuid
     * @return
     */
    @SuppressWarnings("serial")
    public static String createToken(final String userId, final String userName, final String uuid,
            final String deviceName, final String deviceVersion, final String location, final Integer idLog) {
        // get now
        Date now = Calendar.getInstance().getTime();

        Key key = new SecretKeySpec(secretKey.getBytes(), algorithm);
        System.out.println("userId: " + userId);
        System.out.println("Secret key: " + key.toString());

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(new TreeMap<String, Object>() {
                    {
                        put(ConstValues.TokenAuthentication.CLAIM_USER_ID, userId);
                        put(ConstValues.TokenAuthentication.CLAIM_USER_NAME, userName);
                        put(ConstValues.TokenAuthentication.CLAIM_UUID, uuid);
                        // TODO lang
                        put(ConstValues.TokenAuthentication.CLAIM_USER_LANG, ConstValues.DEFAULT_LANGUAGE);
                        put(ConstValues.TokenAuthentication.CLAIM_DEVICE_NAME, deviceName);
                        put(ConstValues.TokenAuthentication.CLAIM_DEVICE_VERSION, deviceVersion);
                        put(ConstValues.TokenAuthentication.CLAIM_LOCATION, location);
                        put(ConstValues.TokenAuthentication.CLAIM_ID_LOG, idLog);
                    }
                })
                .setIssuedAt(now)
                .setExpiration(DateUtils.addSeconds(now, ConstValues.TokenAuthentication.TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        System.out.println("token: " + token);

        return token;

    }

    /**
     * validateToken
     *
     * @param token
     */
    public static UserSessionDto validateToken(String token) {
        // 1) Check empty
        if (StringUtils.isEmpty(token)) {
            LOGGER.debug("Token is empty");
            return null;
        }

        // 2): check authorize jwt
        UserSessionDto userSessionDto = new UserSessionDto();
        try {
            Key key = new SecretKeySpec(secretKey.getBytes(), algorithm);
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            userSessionDto.setUserId(Integer.valueOf((String) claims.get(ConstValues.TokenAuthentication.CLAIM_USER_ID)));
            userSessionDto.setUserName((String) claims.get(ConstValues.TokenAuthentication.CLAIM_USER_NAME));
            userSessionDto.setUuid((String) claims.get(ConstValues.TokenAuthentication.CLAIM_UUID));
            userSessionDto.setNativeLanguage((String) claims.get(ConstValues.TokenAuthentication.CLAIM_USER_LANG));
            userSessionDto.setDeviceName((String) claims.get(ConstValues.TokenAuthentication.CLAIM_DEVICE_NAME));
            userSessionDto.setDeviceVersion((String) claims.get(ConstValues.TokenAuthentication.CLAIM_DEVICE_VERSION));
            userSessionDto.setLocation((String) claims.get(ConstValues.TokenAuthentication.CLAIM_LOCATION));
            userSessionDto.setIdLog((Integer) claims.get(ConstValues.TokenAuthentication.CLAIM_ID_LOG));
            userSessionDto.setToken(token);
        } catch (Exception e) {
            // return login
            e.printStackTrace();
            LOGGER.debug("Parsing token has errors");
            return null;
        }

        // all conditions passed
        return userSessionDto;
    }

}

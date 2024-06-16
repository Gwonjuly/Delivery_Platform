package org.delivery.account.domain.token.helper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.delivery.account.domain.token.ifs.TokenHelperIfs;
import org.delivery.account.domain.token.model.TokenDto;
import org.delivery.common.error.TokenErrorCode;
import org.delivery.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper implements TokenHelperIfs {
    //서명을 위한 키(암호 키), token에 대한 만료 지정. yaml에 등록

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    @Override
    public TokenDto issueAccessToken(Map<String,Object> data) {
        //만료시간 지정: 현재 시간+accessTokenPlusHour(1h)
        var expiredLocalDateTime= LocalDateTime.now().plusHours(accessTokenPlusHour);

        //위에서 구한 만료 시간 -> Date 형식(현재 시스템 존)
        var expiredAt= Date.from(
                expiredLocalDateTime
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
        //서명 키
        var key= Keys.hmacShaKeyFor(secretKey.getBytes());

        //토큰 생성
        var jwtToken= Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)//키, 알고리즘은 HS256으로 사인
                .setClaims(data)//데이터 세팅
                .setExpiration(expiredAt)//만료 시간
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    @Override
    public TokenDto issueRefreshToken(Map<String,Object> data) {
        //만료 시간 지정
        var expiredLocalDateTime=LocalDateTime.now().plusHours(refreshTokenPlusHour);

        //위에서 구한 만료 시간 -> Date 형식(현재 시스템 존)
        var expiredAt=Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        //서명 키
        var key=Keys.hmacShaKeyFor(secretKey.getBytes());

        //토큰 생성
        var jwtToken=Jwts.builder()
                .signWith(key,SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {
        //키 생성
        var key=Keys.hmacShaKeyFor(secretKey.getBytes());

        //파서 생성
        var parser=Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        //파싱 시, 발생하는 에러 처리(3가지)
        try{
            //토큰의 파싱 진행
             var result=parser.parseClaimsJws(token);
             return new HashMap<String ,Object>(result.getBody());

        }catch (Exception e){
            if (e instanceof SignatureException){
                //토큰이 유효하지 않을 때, 발생
                throw new ApiException(TokenErrorCode.INVALID_TOKEN,e);
            }
            else if(e instanceof ExpiredJwtException){
                //만료된 토큰
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN,e);
            }
            else{
                //그 외 에러
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN,e);
            }
            //발생한 에러 -> ApiExceptionHandler
        }
    }
}

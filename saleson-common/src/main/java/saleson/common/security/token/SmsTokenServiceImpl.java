package saleson.common.security.token;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Sha512DigestUtils;
import java.util.Base64;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.security.token.TokenMapper;
import com.onlinepowers.framework.security.token.TokenService;
import com.onlinepowers.framework.security.token.domain.Token;

@Service("smsTokenService")
public class SmsTokenServiceImpl implements TokenService {

	@Autowired
	private TokenMapper tokenMapper;
	
	private static int pseudoRandomNumberBits = 256;
	
	@Override
	public Token allocateToken(Token token) {
		int serverInteger = 780706;
		long creationTime = new Date().getTime();
		int value = (int) (creationTime % serverInteger);
        String serverSecret = ":" + value;
        
        byte[] randomizedBits = new byte[pseudoRandomNumberBits];
        
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomizedBits);
        
        String pseudoRandomNumber = new String(Hex.encode(randomizedBits));
        String content = new Long(creationTime).toString() + ":" + pseudoRandomNumber + ":" + token.getRemoteAddr();

        // Compute key
        String sha512Hex = Sha512DigestUtils.shaHex(content + ":" + serverSecret);
        String keyPayload = content + ":" + sha512Hex;
        String key = Base64.getEncoder().encodeToString(convertToBytes(keyPayload));
        String requestToken = key.substring(20, 60);
        //String requestToken = convertToString(Base64.encode(MD5Utils.encrypt(key).getBytes()));
        
        token.setRequestToken(requestToken);
        

        
        // 2. 토큰생성 
        tokenMapper.insertToken(token);

        
		return token;
	}

	@Override
	public boolean isValidToken(Token token) {
		// 1. 만료 토큰 삭제 
        tokenMapper.deleteExpiredToken();		


        
		Token dbToken = tokenMapper.getToken(token);
		

		if (dbToken == null) {
			return false;
		}
		
		
		// AccessToken(SMS 인증번호) 이 일치하는지 여부를 체크 
		if (dbToken.getAccessToken().equals(token.getAccessToken())) {
			return true;
		}

		return false;
				
				
		// tokenMapper.deleteToken(dbToken.getRequestToken());
		
		// 접속 IP 일치 여부와 AccessToken(SMS 인증번호) 이 일치하는지 여부를 체크 
		/*
		if (dbToken.getRemoteAddr().equals(token.getRemoteAddr())
				&& dbToken.getAccessToken().equals(token.getAccessToken())) {
			return true;
		}

		return false;
		*/
	}
	
	
	
	
	private String convertToString(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	
	private byte[] convertToBytes(String input) {
        try {
            return input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}

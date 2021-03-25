package saleson.common.utils;

import java.security.SecureRandom;
import java.util.regex.Pattern;

public class RandomStringUtils {

	private static SecureRandom random = new SecureRandom();

	private RandomStringUtils() {
	}

	/**
     * 랜덤 문자열 생성
     * @param prefix 접두어
     * @param minmunAlphabet 최소 알파벳수
     * @param length 문자열 길이
     * @return
     */
    public static String getRandomString(String prefix, int minmunAlphabet,int length) {

        String randomString = getRandomString(minmunAlphabet, length);

        while(getAlphabetCount(randomString) <= minmunAlphabet) {
            randomString = getRandomString(minmunAlphabet, length);
        }

        return prefix+randomString;
    }

    /**
     * 랜덤 문자열 생성
     * @param minmunAlphabet 최소 알파벳수
     * @param length 문자열 길이
     * @return
     */
    private static String getRandomString(int minmunAlphabet,int length) {

        StringBuffer randomString = new StringBuffer();

        for (int i=0; i< length; i++) {

            boolean flag = random.nextBoolean();

            if(flag){
                randomString.append((char)((int)(random.nextInt(26))+65));
            }else{
                randomString.append((random.nextInt(10)));
            }
        }

        return randomString.toString();

    }

    /**
     * 문자열에 알파벳 갯수 체크
     * @param value
     * @return
     */
    private static int getAlphabetCount(String value) {
        int count = 0;

        for (int i=0; i<value.length(); i++) {
            char charCode = value.charAt(i);

            boolean isMaches = Pattern.matches("[a-zA-Z]", String.valueOf(charCode));

            if (isMaches) {
                count++;
            }

        }

        return count;
    }

}

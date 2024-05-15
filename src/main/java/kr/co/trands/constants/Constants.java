package kr.co.trands.constants;

public class Constants {
	public final static String USE_YN_Y = "Y";
	public final static String USE_YN_N = "N";

	public final static String ACCESS_TOKEN_REDIS_KEY = "accessToken";
	public final static String REFRESH_TOKEN_REDIS_KEY = "refreshToken";
	public final static String TOKEN_POLICY_SINGLE = "single";
	public final static String TOKEN_POLICY_MULTI = "multi";
	public final static String TOKEN_POLICY_PC_MOBILE = "pcMobile";
	public final static String TOKEN_POLICY_PC_KEY = "pc";
	public final static String TOKEN_POLICY_MOBILE_KEY = "mobile";
	public final static long ACCESS_EXP_TIME = 1000 * 60L * 60 * 1;
	public final static long REFRESH_EXP_TIME = 1000 * 60L * 60 * 24 * 1;
	public final static long AUTO_REFRESH_EXP_TIME = 1000 * 60L * 60 * 24 * 7;
}

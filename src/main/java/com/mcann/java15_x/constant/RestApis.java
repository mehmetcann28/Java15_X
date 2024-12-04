package com.mcann.java15_x.constant;

public class RestApis {
	private static final String VERSION = "/v1";
	private static final String API = "/api";
	private static final String DEVELOPER = "/dev";
	private static final String TEST = "/test";
	private static final String PROD = "/prod";
	
	private static final String ROOT = VERSION + DEVELOPER;
	
	public static final String USER = ROOT + "/user";
	public static final String POST = ROOT + "/post";
	
	public static final String REGISTER = "/register";
	public static final String DOLOGIN = "/dologin";
	public static final String GETPROFILE = "/get-profile";
	public static final String NEWPOST = "/new-post";
	public static final String GETALLMYPOSTS = "/get-all-my-posts";
	public static final String GETALLPOSTS = "/get-all-posts";
	

}
package io.github.iethem.model;

public class User {
	private String uid;
	private String name;
	private String email;
	private String bio;
	private long lastLogin;

	public User(String uid, String name, String email, String bio, long lastLogin) {
		super();
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.setBio(bio);
		this.lastLogin = lastLogin;
	}

	public User() {
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}
}
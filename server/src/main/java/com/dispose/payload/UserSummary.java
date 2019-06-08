package com.dispose.payload;

import java.util.Date;
import com.dispose.model.RoleName;

public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String profileURL;
    private String token;
    private Date expire;
    private RoleName role;

    public UserSummary(Long id, String username, String name, String email, String url, Date expire,RoleName role,String token) {
    	
    	this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.profileURL = url;
        this.token = token;
        this.setExpire(expire);
        this.setRole(role);
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

	public String getProfileURL() {
		return profileURL;
	}

	public void setProfileURL(String profileURL) {
		this.profileURL = profileURL;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public void setRole(RoleName role) {
		this.role = role;
	}
	
	public RoleName getRole() {
		return role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

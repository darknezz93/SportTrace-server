package com.facebook.integration.example


import com.facebook.integration.example.User

class FacebookUser {

    Long uid
    String accessToken
    long accessTokenExpires

    static belongsTo = [user: User]
	
	static mapping = {
		table '`facebook_user`'
		password column: '`password`'
		user cascade: 'save-update'
	}

    static constraints = {
        uid unique: true
    }
}

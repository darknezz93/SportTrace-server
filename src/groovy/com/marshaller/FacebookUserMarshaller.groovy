package com.marshaller

import java.util.Date;

import com.facebook.integration.example.FacebookUser

import grails.converters.JSON


class FacebookUserMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(FacebookUser) { FacebookUser fbUser ->
			return [
				uid: fbUser.uid,
				accessToken: fbUser.accessToken,
				accessTokenExpires: fbUser.accessTokenExpires,
				user: fbUser.user
				]
			
		}
	}

}

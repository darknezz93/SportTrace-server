package com.marshaller

import com.facebook.integration.example.User
import grails.converters.JSON


class UserMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(User) { User user ->
			return [
				id: user.id,
				name: user.name,
				username: user.username,
				emailAddress: user.emailAddress
				]
			
		}
	}

}

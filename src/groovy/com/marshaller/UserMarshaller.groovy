package com.marshaller

import com.facebook.integration.example.User
import grails.converters.JSON
import com.facebook.integration.example.UserRole
import com.facebook.integration.example.Role


class UserMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(User) { User user ->
			return [
				id: user.id,
				name: user.name,
				username: user.username,
				emailAddress: user.emailAddress,
				role: UserRole.findAllByUser(user).role.authority,
				categories: user.categories
				]
			
		}
	}

}

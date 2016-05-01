package com.marshaller

import com.event.SportCategory
import com.facebook.integration.example.FacebookUser
import grails.converters.JSON


class SportCategoryMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(SportCategory) { SportCategory sportCategory ->
			return [
				id: sportCategory.id,
				name: sportCategory.name
				]
			
		}
	}

}

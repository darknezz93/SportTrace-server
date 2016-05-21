package com.marshaller

import com.facebook.integration.example.Area
import grails.converters.JSON


class AreaMarshaller {
	void register() {
		JSON.registerObjectMarshaller(Area) { Area area ->
			return [
				id: area.id,
				name: area.name,
				latitude: area.latitude,
				longitude: area.longitude,
				radius: area.radius
				]
			
		}
	}
}

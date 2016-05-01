package com.event
import com.facebook.integration.example.User


class SportCategory {
		
	String name
		
	static mapping = {

	}
	
	
	static constraints = {
		name blank: false, unique: true
	}
}

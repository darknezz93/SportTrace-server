package com.facebook.integration.example

class Area {
	
	String name
	double longitude
	double latitude
	double radius
	
	

    static constraints = {
		name nullable: false, unique: true
		longitude nullable: false
		latitude nullable: false
		radius nullable: false
    }
}

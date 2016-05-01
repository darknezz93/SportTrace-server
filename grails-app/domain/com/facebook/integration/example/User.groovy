package com.facebook.integration.example
import com.event.Event;
import com.event.SportCategory;


class User {

	transient springSecurityService
	

	String username
	//String password
	String name
	String emailAddress;
	
	
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	//boolean passwordExpired

	static transients = ['springSecurityService']
	
	static hasMany = [events: Event, categories: SportCategory]
	static mappedBy = [events : "user"]
	//static belongsTo = [event: Event]
	

	static constraints = {
		username blank: false, unique: true
		events nullable: true
		emailAddress nullable: true
	}
	
	
	static mapping = {
		table '`user`'
		//events joinTable: [name: "user_events", key: 'event_id']
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

}

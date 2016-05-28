package com.event
import com.facebook.integration.example.User

class Event {
	
	String name
	Date date
	double missingParticipantsNumber
	double participantsNumber
	boolean special
	double longitude;
	double latitude;
	String location;
	User user
	SportCategory category;
	
	static hasMany = [users: User, appliers: User]
	//static hasOne = [category: Category]
	static belongsTo = [user: User, category: SportCategory]
	

    static constraints = {
		name blank: false, unique: true
		date nullable: false
		missingParticipantsNumber nullable: true
		participantsNumber nullable: false
		special nullable: false
		category nullable: false
		longitude nullable: false
		latitude nullable: false
		location nullable: false
		users nullable: true
    }
	
	static mapping = {
		//users joinTable: [name: "user_events", key: 'user_id']
	}
}

package com.marshaller


import java.util.Date;
import com.facebook.integration.example.User;
import com.event.Event
import com.event.SportCategory
import grails.converters.JSON


class EventMarshaller {
	
	void register() {
		JSON.registerObjectMarshaller(Event) { Event event ->
			return [
				id: event.id,
				name: event.name,
				date: event.date,
				participantsNumber: event.participantsNumber,
				missingParticipantsNumber: event.missingParticipantsNumber,
				special: event.special,
				longitude: event.longitude,
				latitude: event.latitude,
				location: event.location,
				owner: event.user,
				sportCategory: event.category,
				participants: event.users.id,
				appliers: event.appliers != null ? event.appliers.id : []
				]
			
		}
	}

}


/*

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
	
	static hasMany = [users: User]
	//static hasOne = [category: Category]
	static belongsTo = [user: User, category: SportCategory]
*/
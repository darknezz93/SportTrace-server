package com.notification

import com.event.Event
import com.event.EventService
import com.facebook.integration.example.Area
import com.facebook.integration.example.User
import grails.transaction.Transactional

import groovyx.net.http.*
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*
import groovyx.net.http.HTTPBuilder


@Transactional
class NotificationService {
	
	
	
	EventService eventService;
	
    //TODO : dodac eventId do parametrow
	def notifyApplicationUser(User user, String message, String title, Integer type, def eventId) {
		 
		String appHeader = "key=AIzaSyAt9WmW8RR9bWqSq23OAas9ZgO9eqwM5ps"
		
		notifyUsersAboutEvent(user)

		def http = new HTTPBuilder( 'https://gcm-http.googleapis.com/gcm/send' )
		http.setHeaders([Authorization: appHeader])
		http.request( POST, JSON ) { req ->
			 body = [data: [message: message, title: title, type: type, eventId: eventId],to: user.regId]
			 response.success = { resp, json ->
				 //
			 }
		}
	}
	
	
	
	def notifyUsersAboutEvent(User eventOwner, Event event) {

		List<User> users = User.getAll()

		for(User user: users) {
			if(user != eventOwner) {
				Area area = user.area
				if(area) {
					double distance = eventService.calculateDistance(event.latitude, event.longitude, area.latitude, area.longitude)
					if(distance <= area.radius) {
						notifyApplicationUser(user, "New sport event in your searching area! Check it out!", event.name, 1, event.id)
					}
				}
			}
		}
	}
	
	def notifyOrganizerAboutApplier(User eventOwner, User applier, Event event) {
		String title = event.name 
		String message = applier.username + " applies to Your event"
		notifyApplicationUser(eventOwner, message, title, 3, event.id)
	}
	
	def notifyApplierAboutAcceptance(User applier, Event event) {
		String title = event.name
		String message = "You have been accepted by the organizer of the event"
		notifyApplicationUser(applier, message, title, 2,  event.id)
	}
	
	
	
	
}

package com.notification

import com.facebook.integration.example.User
import grails.transaction.Transactional

import groovyx.net.http.*
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*
import groovyx.net.http.HTTPBuilder


@Transactional
class NotificationService {

    //TODO : dodac eventId do parametrow
	def notifyApplicationUser(User user, String message, String title, Integer type) {
		 
		String appHeader = "key=AIzaSyAt9WmW8RR9bWqSq23OAas9ZgO9eqwM5ps"

		def http = new HTTPBuilder( 'https://gcm-http.googleapis.com/gcm/send' )
		http.setHeaders([Authorization: appHeader])
		http.request( POST, JSON ) { req ->
			 body = [data: [message: message, title: title, type: type],to: user.regId]
			 response.success = { resp, json ->
			 	println(json)
			 }
		}
		
		
	}
	
}

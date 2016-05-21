package com.event

import com.facebook.integration.example.Area
import com.facebook.integration.example.User
import grails.transaction.Transactional




@Transactional
class EventService {
	
	
	List<Event> getAdjustedEvents(User user) {
		
		List<Event> events = new ArrayList<Event>()
		List<Event> finalEvents = new ArrayList<Event>()
		
		for(SportCategory category: user.categories) {
			if(Event.findAllByCategory(category)) {
				List<Event> categoryEvents = Event.findAllByCategory(category)
				for(Event catEvent: categoryEvents) {
					events.add(catEvent)
				}
			}
		}
		
		Area area = user.area
		
		if(area) {
			for(Event event: events) {
				double distance = calculateDistance(event.latitude, event.longitude, area.latitude, area.longitude)
				println(distance)
				if(distance <= area.radius) {
					finalEvents.add(event)
				}
			}
		}
		
		if(area) {
			return finalEvents
		}
		
		
		return events
		
		
	}
	
	
	double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6371; //km
		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(lng2-lng1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
				Math.sin(dLng/2) * Math.sin(dLng/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double dist = (double) (earthRadius * c);

		return dist;
	}
	
}

package com.event



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import com.facebook.FacebookAuthService
import com.facebook.integration.example.FacebookUser
import com.facebook.integration.example.User
import com.facebook.integration.example.UserController


@Transactional
class EventController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	//UserController userController = new UserController()
	def beforeInterceptor = [action: this.&authorize]
	
	EventService eventService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Event.list(params), [status: OK]
    }
	
	def adjustEvents() {
		def token = request.getHeader("token")
		FacebookUser fbUser = FacebookUser.findByAccessToken(token)
		User user = fbUser.user
		
		List<Event> events = eventService.getAdjustedEvents(user)
		
		/*for(SportCategory category: user.categories) {
			if(Event.findAllByCategory(category)) {
				events.add(Event.findAllByCategory(category))
			}
		}*/
		
		respond events, [status: OK]
	}

    @Transactional
    def save() {
		def token = request.getHeader("token")
		FacebookUser fbUser = FacebookUser.findByAccessToken(token)
		User user = fbUser.user
		def jsonObject =  request.JSON
		
		if(!jsonObject.category_id || !jsonObject.date || !jsonObject.latitude || !jsonObject.longitude 
			|| !jsonObject.special ||  !jsonObject.location || !jsonObject.name || !jsonObject.participantsNumber) {
			
			render status: NOT_ACCEPTABLE
			return
		}
		
		Event checkEvent = Event.findByName(jsonObject.name)
		if(checkEvent) {
			render status: CONFLICT
			return
		}		
		
	    SportCategory sportCategory = SportCategory.findById(jsonObject.category_id)
		
		if(!sportCategory) {
			render status: NOT_ACCEPTABLE
			return 
		}
		
		Event eventInstance = new Event(category: sportCategory, date: jsonObject.date, latitude: jsonObject.latitude,
			longitude: jsonObject.longitude, special: jsonObject.special, name: jsonObject.name, location: jsonObject.location,
			participantsNumber: jsonObject.participantsNumber, missingParticipantsNumber: jsonObject.participantsNumber)	 
		
		
		eventInstance.user = user
		
        eventInstance.validate()
        if (eventInstance.hasErrors()) {
			println eventInstance.errors
            render status: NOT_ACCEPTABLE
            return
        }

        eventInstance.save flush:true
        respond eventInstance, [status: CREATED]
    }
	
	/*
    @Transactional
    def update(Event eventInstance) {
		
        if (eventInstance == null) {
            render status: NOT_FOUND
            return
        }

        eventInstance.validate()
        if (eventInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        eventInstance.save flush:true
        respond eventInstance, [status: OK]
    }
    */

    @Transactional
    def delete(Event eventInstance) {
		
        if (eventInstance == null) {
            render status: NOT_FOUND
            return
        }

        eventInstance.delete flush:true
        render status: NO_CONTENT
    }
	
	
	
	
	
	
	
	/**
	 * Metoda do autoryzacji dostępu - w każdym zapytaniu idzie w headerze token
	 * @return
	 */
	def authorize() {
		//def jsonObject =  request.JSON
		println("Authorize")
		def token = request.getHeader("token")

		if(token) {
			FacebookUser user = FacebookUser.findByAccessToken(token)
			if(user == null) {
				render status: UNAUTHORIZED
				return false
			}
		} else {
			render status: NOT_ACCEPTABLE
			return false
		}
		return true
	}
}

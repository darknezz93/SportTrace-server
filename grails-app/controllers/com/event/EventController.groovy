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
			participantsNumber: jsonObject.participantsNumber, missingParticipantsNumber: jsonObject.participantsNumber - 1)	 
		
		eventInstance.user = user
		eventInstance.addToUsers(user)
		
		
        eventInstance.validate()
        if (eventInstance.hasErrors()) {
			println eventInstance.errors
            render status: NOT_ACCEPTABLE
            return
        }

        eventInstance.save flush:true
        respond eventInstance, [status: CREATED]
    }
	
	
	def applyToEvent() {
		
		def token = request.getHeader("token")
		FacebookUser fbUser = FacebookUser.findByAccessToken(token)
		User user = fbUser.user
		
		def id = params.id
		
		Event event = Event.findById(id)
		
		if(!event) {
			render status: NOT_FOUND
			return
		}
		
		if(event.appliers.id.contains(user.id) || event.users.id.contains(user.id)) {
			render status: CONFLICT
			return
		}
		
		event.addToAppliers(user)
		
		render status: OK
	}
	
	def resignFromEvent() {
		
		def token = request.getHeader("token")
		FacebookUser fbUser = FacebookUser.findByAccessToken(token)
		User user = fbUser.user
		
		def id = params.id
		
		Event event = Event.findById(id)
		
		if(!event) {
			render status: NOT_FOUND
			return
		}
		
		if(event.user == user) {
			render status: FORBIDDEN
			return
		}
		
		for(User usr: event.users) {
			if(usr.id == user.id) {
				event.users.remove(usr)
				event.missingParticipantsNumber += 1
			}
		}
		
		for(User usr: event.appliers) {
			if(usr.id == user.id) {
				event.appliers.remove(usr)
			}
		}
		
		println(user)
		println("Participants: " + event.users)
		println("Appliers: " + event.appliers)
		
		respond event, [status: OK]
		
	}
	
	
	def acceptAppliers() {

		def token = request.getHeader("token")
		FacebookUser fbUser = FacebookUser.findByAccessToken(token)
		User user = fbUser.user
		
		def id = params.id
		
		Event event = Event.findById(id)
		
		if(!event) {
			render status: NOT_FOUND
			return
		}
		
		// moze akceptowac tylko wlasciciel wydarzenia
		if(event.user != user) {
			render status: UNAUTHORIZED
			return
		}
		
		def jsonObject =  request.JSON
		
		if(!jsonObject.appliers) {
			render status: NOT_ACCEPTABLE
			return
		}
		
		def appliersIds = jsonObject.appliers
		
		List<User> appliers = new ArrayList<User>();
		
		for(int i = 0 ; i < appliersIds.size(); i++) {
			User applierUser = User.findById(appliersIds.get(i))
			if(!applierUser) {
				render status: NOT_FOUND
				return
			}
			appliers.add(applierUser)
		}
		
		for(User usr: appliers) {
			if(!event.appliers.id.contains(usr.id)) {
				render status: NOT_FOUND
				println("Nie ma takiego w appliers wydarzenia")
				return
			}
		}
		
		for(User usr: appliers) {
			event.addToUsers(usr)
			event.removeFromAppliers(usr)
			event.missingParticipantsNumber -= 1
		}

		respond event, [status: OK]
	}
	

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

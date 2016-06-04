package com.facebook.integration.example



import static org.springframework.http.HttpStatus.*

import com.facebook.FacebookAuthService;
import com.notification.NotificationService
import com.user.UserService

import grails.transaction.Transactional



@Transactional
class UserController {
	
	//generate-restful-controller sciezka do klasy domentowej
    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	def beforeInterceptor = [action: this.&authorize, except: ['create', 'checkToken', 'authenticate', 'testNotification']]
	
	FacebookAuthService facebookAuthService;
	UserService userService;
	NotificationService notificationService;
	
	
	def authenticate() {
		
		def token = request.getHeader("token")
		if(!token) {
			render status: NOT_ACCEPTABLE
			return
		}
		
		FacebookUser fbUser = FacebookUser.findByAccessToken(token)
		
		if(!fbUser) {
			render status: NOT_FOUND
			return
		} else {
			
			def expireDate = fbUser.accessTokenExpires
			Date date = new Date(expireDate);
			Date currentDate = new Date();
			
			if(currentDate.after(date)) {
				render status: UNAUTHORIZED
				return
			}

		}
		
		User user = fbUser.user;
		respond fbUser, [status: OK]
		
	}
	
	
	def checkToken() {
		
		String token = params.token
		
		if(token) {
			boolean result = facebookAuthService.checkFacebookExistanceByToken(token)
			if(result) {
				render status: OK
				return
			} else {
				render status: NOT_FOUND
				return
			}
		} else {
			render status: NOT_ACCEPTABLE
			return
		}
	}
	
	def create() {
		
		def jsonObject =  request.JSON
		
		if(!jsonObject.token || !jsonObject.uid || !jsonObject.expiredDate || !jsonObject.regId) {
			render status: NOT_ACCEPTABLE
			return
		}
		
		String token = jsonObject.token
		Long uid = Long.valueOf(jsonObject.uid)
		def expiredDate = jsonObject.expiredDate
		String regId = jsonObject.regId
		
		FacebookUser fbUserInstance
		
		if(token) {
			FacebookUser fbUser = FacebookUser.findByAccessToken(token);
			if(fbUser) {
				render status: CONFLICT
				return
			}
			else {
				boolean result = facebookAuthService.checkFacebookExistanceByToken(token)
				if(!result) {
					render status: NOT_FOUND
					return
				}
				else {
					
					FacebookUser uidUser = FacebookUser.findByUid(uid)
					if(uidUser) {
						fbUserInstance = facebookAuthService.updateTokenAndExpiredDate(token, uid, expiredDate)
						respond fbUserInstance, [status: OK]
					}
					else {
						fbUserInstance = facebookAuthService.create(token, uid, expiredDate, regId)
					}
				}
			}
		} 
		else {
			render status: NOT_ACCEPTABLE
			return
		}
		
		
		respond fbUserInstance, [status: CREATED]
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
	
	
	def events() {
		def id = params.id
		def user = User.findById(id)
		if(user != null) {
			def events = user.events
			respond events, [status: OK]
		} 
		render status: NOT_FOUND
	}
	
	
	def updatePreferences() {
		println("Update Preferences")

		def token = request.getHeader("token")
		FacebookUser fbUser = FacebookUser.findByAccessToken(token)
		User user = fbUser.user

		def jsonObject =  request.JSON

		List<String> sportCategoriesNames = new ArrayList<>()

		for(int i = 0; i < jsonObject.size(); i++){
			if(!jsonObject[i].name) {
				render status: NOT_ACCEPTABLE
				return
			} else {
				sportCategoriesNames.add(jsonObject[i].name)
			}
		}
		
		for(String category: sportCategoriesNames) {
			println(category)
		}
		
		userService.updateUserSportCategories(user, sportCategoriesNames)


		respond fbUser, [status: OK]
	}


	

    def index(Integer max) {
		
        //params.max = Math.min(max ?: 10, 100)
        //respond User.list(params), [status: OK]
		respond FacebookUser.list(), [status: OK]
    }
	
	
	def testNotification() {
		
		User user = User.findById(34)
		
		notificationService.notifyApplicationUser(user, "Znaleziono nowe wydarzenie w pobliżu", "Nowe wydarzenie ", 1, 2)
	}
/*
    @Transactional
    def save(User userInstance) {
        if (userInstance == null) {
            render status: NOT_FOUND
            return
        }

        userInstance.validate()
        if (userInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        userInstance.save flush:true
        respond userInstance, [status: CREATED]
    }

    @Transactional
    def update(User userInstance) {
        if (userInstance == null) {
            render status: NOT_FOUND
            return
        }

        userInstance.validate()
        if (userInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        userInstance.save flush:true
        respond userInstance, [status: OK]
    }

    @Transactional
    def delete(User userInstance) {

        if (userInstance == null) {
            render status: NOT_FOUND
            return
        }

        userInstance.delete flush:true
        render status: NO_CONTENT
    }
    
    */
}

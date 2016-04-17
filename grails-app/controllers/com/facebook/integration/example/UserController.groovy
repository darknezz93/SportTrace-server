package com.facebook.integration.example



import static org.springframework.http.HttpStatus.*

import com.facebook.FacebookAuthService;

import grails.transaction.Transactional



@Transactional
class UserController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	FacebookAuthService facebookAuthService;
	
	
	def checkToken() {

		def jsonObject =  request.JSON
		String token = jsonObject.token
		
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
		
		if(!jsonObject.token || !jsonObject.uid || !jsonObject.expiredDate) {
			render status: NOT_ACCEPTABLE
			return
		}
		
		String token = jsonObject.token
		Long uid = Long.valueOf(jsonObject.uid)
		String expiredDate = jsonObject.expiredDate
		
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
					}
					else {
						fbUserInstance = facebookAuthService.create(token, uid, expiredDate)
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

	/*
    def index(Integer max) {
		
        //params.max = Math.min(max ?: 10, 100)
        //respond User.list(params), [status: OK]
		respond User.list(), [status: OK]
    }

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

package com.event



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import com.facebook.integration.example.FacebookUser
import com.facebook.integration.example.UserController


@Transactional()
class SportCategoryController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	//UserController userController = new UserController()
	def beforeInterceptor = [action: this.&authorize]
	

    def all(Integer max) {
		//params.max = Math.min(max ?: 10, 100)
        respond SportCategory.list(), [status: OK]
    }
	
	/*
    @Transactional
    def save(SportCategory sportCategoryInstance) {
        if (sportCategoryInstance == null) {
            render status: NOT_FOUND
            return
        }

        sportCategoryInstance.validate()
        if (sportCategoryInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        sportCategoryInstance.save flush:true
        respond sportCategoryInstance, [status: CREATED]
    }

    @Transactional
    def update(SportCategory sportCategoryInstance) {
        if (sportCategoryInstance == null) {
            render status: NOT_FOUND
            return
        }

        sportCategoryInstance.validate()
        if (sportCategoryInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        sportCategoryInstance.save flush:true
        respond sportCategoryInstance, [status: OK]
    }

    @Transactional
    def delete(SportCategory sportCategoryInstance) {

        if (sportCategoryInstance == null) {
            render status: NOT_FOUND
            return
        }

        sportCategoryInstance.delete flush:true
        render status: NO_CONTENT
    } */
	
	
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

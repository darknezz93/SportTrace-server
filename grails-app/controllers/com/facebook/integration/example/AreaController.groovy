package com.facebook.integration.example



import static org.springframework.http.HttpStatus.*
import com.area.AreaService
import grails.transaction.Transactional

@Transactional
class AreaController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	def beforeInterceptor = [action: this.&authorize]
	
	AreaService areaService
	

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Area.list(params), [status: OK]
    }
	
	@Transactional
	def addUserArea() {
		
		def token = request.getHeader("token")
		FacebookUser fbUser = FacebookUser.findByAccessToken(token)
		User user = fbUser.user
		
		def jsonObject =  request.JSON
		
		if(!jsonObject.longitude || !jsonObject.latitude || !jsonObject.radius || !jsonObject.name) {
			render status: NOT_ACCEPTABLE
			return
		}
		
		if(areaService.checkIfAreaExists(jsonObject.name)) {
			render status: CONFLICT
			return
		}
		
		Area area = new Area(latitude: jsonObject.latitude, longitude: jsonObject.longitude, radius: jsonObject.radius, name: jsonObject.name)
		area.validate()
		
		if(area.hasErrors()) {
			respond area.getErrors() status INTERNAL_SERVER_ERROR
		}
		
		area.save flush: true
		
		user.area = area
		render status: OK
	}
	
	def updateUserArea() {
		
		def token = request.getHeader("token")
		FacebookUser fbUser = FacebookUser.findByAccessToken(token)
		User user = fbUser.user
		
		def jsonObject =  request.JSON
		
		if(!jsonObject.longitude || !jsonObject.latitude || !jsonObject.radius || !jsonObject.name) {
			render status: NOT_ACCEPTABLE
			return
		}
		
		if(!areaService.checkIfAreaExists(jsonObject.name)) {
			render status: NOT_FOUND 
			return
		}
		
		Area area = Area.findByName(jsonObject.name)
		
		Area updatedArea = new Area(latitude: jsonObject.latitude, longitude: jsonObject.longitude, radius: jsonObject.radius, name: jsonObject.name)
		
		if(updatedArea.hasErrors()) {
			respond updatedArea.getErrors()
			return
		}
		
		area.latitude = jsonObject.latitude
		area.longitude = jsonObject.longitude
		area.radius = jsonObject.radius
		
		
		area.save flush: true
		render status: OK
		
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
	

    /*@Transactional
    def save(Area areaInstance) {
        if (areaInstance == null) {
            render status: NOT_FOUND
            return
        }

        areaInstance.validate()
        if (areaInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        areaInstance.save flush:true
        respond areaInstance, [status: CREATED]
    }

    @Transactional
    def update(Area areaInstance) {
        if (areaInstance == null) {
            render status: NOT_FOUND
            return
        }

        areaInstance.validate()
        if (areaInstance.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        areaInstance.save flush:true
        respond areaInstance, [status: OK]
    }

    @Transactional
    def delete(Area areaInstance) {

        if (areaInstance == null) {
            render status: NOT_FOUND
            return
        }

        areaInstance.delete flush:true
        render status: NO_CONTENT
    } */
}

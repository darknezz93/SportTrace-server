package com.user

import com.facebook.integration.example.User
import grails.transaction.Transactional
import com.event.SportCategory




@Transactional
class UserService {

    def updateUserSportCategories(User user, List<String> sportCategoriesNames) {
		
		def sportCategories = SportCategory.findAllByNameInList(sportCategoriesNames)

		user.categories = sportCategories
		user.save()
	}
}

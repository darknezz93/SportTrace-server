package com.area

import com.facebook.integration.example.Area
import grails.transaction.Transactional





@Transactional
class AreaService {

    def checkIfAreaExists(String name) {
		Area area = Area.findByName(name)
		if(area != null) {
			return true;
		}
		return false;
	}
}

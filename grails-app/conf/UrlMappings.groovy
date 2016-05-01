class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
		
		// USER
		"/api/users/checkToken/$token"(controller: "user", action: "checkToken", method: "GET", parseRequest: true)
		"/api/users"(controller: "user", action: "create", method: "POST", parseRequest: true)
		"/api/users/$id/events"(controller: "user", action: "events", method: "GET", parseRequest: true)
		"/api/users"(controller: "user", action: "index", method: "GET", parseRequest: true)
		
		
		// EVENT
		"/api/events"(controller: "event", action: "index", method: "GET", parseRequest: true)	
		"/api/events"(controller: "event", action: "save", method: "POST", parseRequest: true)
		"/api/events/$id"(controller: "event", action: "delete", method: "DELETE", parseRequest: true)
		
		
		
		// SPORT CATEGORY
		"/api/sportCategories"(controller: "SportCategory", action: "all", method: "GET", parseRequest: true)
			
		
	}
}

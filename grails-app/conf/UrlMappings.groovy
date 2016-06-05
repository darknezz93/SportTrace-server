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
		"/api/users/preferences"(controller: "user", action: "updatePreferences", method: "PUT", parseRequest: true)
		"/api/users/authenticate"(controller: "user", action: "authenticate", method: "GET", parseRequest: true)
		
		
		// EVENT
		"/api/events"(controller: "event", action: "index", method: "GET", parseRequest: true)
		"/api/events/adjust"(controller: "event", action: "adjustEvents", method: "GET", parseRequest: true)
		"/api/events"(controller: "event", action: "save", method: "POST", parseRequest: true)
		"/api/events/$id"(controller: "event", action: "delete", method: "DELETE", parseRequest: true)
		"/api/events/apply/$id"(controller: "event", action: "applyToEvent", method: "PUT", parseRequest: true)
		"/api/events/appliers/accept/$id"(controller: "event", action: "acceptAppliers", method: "POST", parseRequest: true)
		"/api/events/resign/$id"(controller: "event", action: "resignFromEvent", method: "POST", parseRequest: true)
		
		
		
		// SPORT CATEGORY
		"/api/sportCategories"(controller: "SportCategory", action: "all", method: "GET", parseRequest: true)
		
		// AREA
		"/api/users/areas"(controller: "Area", action: "addUserArea", method: "POST", parseRequest: true)
		"/api/users/areas"(controller: "Area", action: "updateUserArea", method: "PUT", parseRequest: true)
		
		
		
			
		
	}
}

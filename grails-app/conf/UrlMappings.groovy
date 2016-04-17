class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
		
		"/api/user/checkToken"(controller: "user", action: "checkToken", method: "POST", parseRequest: true)
		"/api/user/create"(controller: "user", action: "create", method: "POST", parseRequest: true)
	}
}

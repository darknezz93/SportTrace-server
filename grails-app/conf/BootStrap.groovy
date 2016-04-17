import com.facebook.integration.example.Role

class BootStrap {

    def init = { servletContext ->
		createDefaultRoles()
    }
	
    def destroy = {
    }
	
	
	def createDefaultRoles() {
		def user_role = Role.findByAuthority("ROLE_USER");
		def admin_role = Role.findByAuthority("ROLE_ADMIN");
		def facebook_role = Role.findByAuthority("ROLE_FACEBOOK")
		
		if(user_role == null) {
			def role_user = new Role(authority: 'ROLE_USER', description: 'User role').save()
		}
		if(admin_role == null) {
			def role_admin = new Role(authority: 'ROLE_ADMIN', description: 'Admin role').save()
		}
		if(facebook_role == null) {
			def role_facebook = new Role(authority: 'ROLE_FACEBOOK', description: 'Facebook role').save()
		}
	}
}

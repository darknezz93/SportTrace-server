import com.event.SportCategory;
import com.facebook.integration.example.Role
import org.springframework.web.context.support.WebApplicationContextUtils


class BootStrap {

    def init = { servletContext ->
		
		def springContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )
		springContext.getBean( "customObjectMarshallers" ).register()
		
		
		createDefaultRoles()
		createDefaultSportCategories()
    }
	
    def destroy = {
    }
	
	
	def createDefaultRoles() {
		def role_user = new Role(authority: 'ROLE_USER', description: 'User role').save()
		def role_admin = new Role(authority: 'ROLE_ADMIN', description: 'Admin role').save()
		def role_facebook = new Role(authority: 'ROLE_FACEBOOK', description: 'Facebook role').save()
	}
	
	def createDefaultSportCategories() {
		def football = new SportCategory(name: 'FOOTBALL').save() //SportCategory.findByName("FOOTBALL")
		def basketball = new SportCategory(name: 'BASKETBALL').save() //SportCategory.findByName("BASKETBALL")
		def volleyball = new SportCategory(name: 'VOLLEYBALL').save() //SportCategory.findByName("VOLLEYBALL")
		def hockey = new SportCategory(name: 'HOCKEY').save() //SportCategory.findByName("HOCKEY")
		def handball = new SportCategory(name: 'HANDBALL').save() //SportCategory.findByName("HANDBALL")
		def running = new SportCategory(name: 'RUNNING').save() //SportCategory.findByName("RUNNING")
		def cycling = new SportCategory(name: 'CYCLING').save() //SportCategory.findByName("CYCLING")
		def mountainBiking = new SportCategory(name: 'MOUNTAIN BIKING').save() //SportCategory.findByName("MOUNTAIN BIKING")
		def swimming = new SportCategory(name: 'SWIMMING').save() //SportCategory.findByName("SWIMMING")
		def tennis = new SportCategory(name: 'TENNIS').save() //SportCategory.findByName("TENNIS")
		def badminton = new SportCategory(name: 'BADMINTON').save() //SportCategory.findByName("BADMINTON")
		def ski = new SportCategory(name: 'SKI').save() //SportCategory.findByName("SKI")
		def crossCountrySkiing = new SportCategory(name: 'CROSS COUNTRY SKIING').save() //SportCategory.findByName("CROSS COUNTRY SKIING")
		def skating = new SportCategory(name: 'SKATING').save() //SportCategory.findByName("SKATING")
		def fitness = new SportCategory(name: 'FITNESS').save() //SportCategory.findByName("FITNESS")
		def squash = new SportCategory(name: 'SQUASH').save() //SportCategory.findByName("SQUASH")
		def acrobatics = new SportCategory(name: 'ACROBATICS').save() //SportCategory.findByName("ACROBATICS")
		def rowing = new SportCategory(name: 'ROWING').save() //SportCategory.findByName("ROWING")
		def windsurfing = new SportCategory(name: 'WINDSURFING').save() //SportCategory.findByName("WINDSURFING")
		def kitesurfing = new SportCategory(name: 'KITESURFING').save() //SportCategory.findByName("KITESURFING")
		def surfing = new SportCategory(name: 'SURFING').save() //SportCategory.findByName("SURFING")
		def climbing = new SportCategory(name: 'CLIMBING').save() //SportCategory.findByName("CLIMBING")
		def streetWorkout = new SportCategory(name: 'STREET WORKOUT').save() //SportCategory.findByName("STREET WORKOUT")
		def golf = new SportCategory(name: 'GOLF').save()
		def rugby = new SportCategory(name: 'RUGBY').save()
		def baseball = new SportCategory(name: 'BASEBALL').save()
		
		
	}
}

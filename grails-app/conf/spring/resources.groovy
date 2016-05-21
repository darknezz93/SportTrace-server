import com.marshaller.AreaMarshaller
import com.marshaller.EventMarshaller
import com.marshaller.FacebookUserMarshaller
import com.marshaller.SportCategoryMarshaller
import com.marshaller.UserMarshaller
import util.marshalling.CustomObjectMarshallers

// Place your Spring DSL code here
beans = {
    nonAuthFilter(NonAuthenticationFilter)
	
	customObjectMarshallers( CustomObjectMarshallers ) {
		marshallers = [
				new UserMarshaller(),
				new FacebookUserMarshaller(),
				new SportCategoryMarshaller(),
				new EventMarshaller(),
				new AreaMarshaller()
		]
	}
	
}



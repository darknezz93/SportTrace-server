    <sec:ifNotGranted roles="ROLE_USER">
     <facebookAuth:connect />
    </sec:ifNotGranted>
    <sec:ifAllGranted roles="ROLE_USER">
     Welcome <sec:username/>! (<g:link uri="/j_spring_security_logout">Logout</g:link>)
    </sec:ifAllGranted>
package com.facebook.integration.example


class User {

	transient springSecurityService
	

	String username
	String password
	String name
	String emailAddress;
	
	
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static transients = ['springSecurityService']
	

	static constraints = {
		username blank: false, unique: true
		password blank: false
		emailAddress nullable: true
	}

	static mapping = {
		table '`user`'
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}

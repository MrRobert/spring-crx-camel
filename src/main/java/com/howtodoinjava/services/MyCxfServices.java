package com.howtodoinjava.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.howtodoinjava.entity.User;

@Path("/{region}")
@Produces("application/json")
public class MyCxfServices {
	@Autowired
	MongoUserServices mongoUserService;

	@GET
	@Path("/getAllUser/")
	public List<User> getAllUser() {
		return null;
	}

}

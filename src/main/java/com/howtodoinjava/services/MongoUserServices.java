package com.howtodoinjava.services;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.howtodoinjava.common.SpringMongoConfig;
import com.howtodoinjava.entity.User;

@Service
public class MongoUserServices {

	MongoOperations mongoOperation = SpringMongoConfig.getMongoOperation();

	public void saveUser(User user) {
		if (user == null) {
			user = new User("TEST", 20, "MALE");
		}
		mongoOperation.save(user);
		System.out.println("1. user : " + user);
	}

	public User findUser(String userName) {
		Query searchUserQuery = new Query(Criteria.where("username").is(
				userName));

		// find the saved user again.
		User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
		System.out.println("2. find - savedUser : " + savedUser);
		return savedUser;
	}

	public User updatePassword(String userName, String password) {
		Query searchUserQuery = new Query(Criteria.where("username").is(
				userName));
		mongoOperation.updateFirst(searchUserQuery,
				Update.update("password", "new password"), User.class);

		// find the updated user object
		User updatedUser = mongoOperation.findOne(
				new Query(Criteria.where("username").is(userName)), User.class);

		System.out.println("3. updatedUser : " + updatedUser);
		return updatedUser;
	}

	public void removeUser(User user) {
		Query searchUserQuery = new Query(Criteria.where("username").is(
				user.getName()));
		mongoOperation.remove(searchUserQuery, User.class);
	}

	public List<User> findAllUser() {
		System.out.println("Log services: " + mongoOperation);
		return mongoOperation.findAll(User.class);
	}
}

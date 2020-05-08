package io.github.iethem.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import io.github.iethem.model.User;
import io.github.iethem.repository.UserRepository;
import io.quarkus.security.Authenticated;

@Path("/api/user")
@Authenticated
public class UserResource {

	@Inject
	UserRepository userRepository;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@Context SecurityContext ctx) throws Exception {
		String uid = ctx.getUserPrincipal().getName();
		User user = userRepository.get(uid);

		return Response.ok(user).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(String bio, @Context SecurityContext ctx) throws Exception {
		String uid = ctx.getUserPrincipal().getName();
		UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);

		User user = new User();
		user.setEmail(userRecord.getEmail());
		user.setName(userRecord.getDisplayName());
		user.setUid(uid);
		user.setLastLogin(userRecord.getUserMetadata().getLastSignInTimestamp());
		user.setBio(bio);

		User savedUser = userRepository.save(user);

		return Response.ok(savedUser).build();
	}

}
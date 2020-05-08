package io.github.iethem.repository;

import java.util.concurrent.ExecutionException;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.iethem.model.User;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@ApplicationScoped
public class UserRepository {

	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
	public User get(String uid) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<DocumentSnapshot> apiFuture = db.collection("user").document(uid).get();

		DocumentSnapshot document = apiFuture.get();
		if (document.exists()) {
			logger.info("User found: {}", uid);
			return document.toObject(User.class);
		}

		logger.info("User not found: {}", uid);
		return null;
	}

	public User save(User user) throws Exception {
		Firestore db = FirestoreClient.getFirestore();

		ApiFuture<WriteResult> apiFuture = db.collection("user").document(user.getUid()).set(user, SetOptions.merge());
		WriteResult writeResult = apiFuture.get();
		logger.info("Succesfully saved, updated time: {}", writeResult.getUpdateTime());
		return user;
	}

}
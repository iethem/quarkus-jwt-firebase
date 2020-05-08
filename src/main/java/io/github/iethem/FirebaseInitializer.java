package io.github.iethem;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class FirebaseInitializer {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseInitializer.class);

    public void onStart(@Observes StartupEvent ev) {
        logger.info("Initializing Firebase App...");
        try {
            this.initializeFirebaseApp();
        } catch (IOException e) {
            logger.error("Initializing Firebase App {}", e);
        }
    }

    private void initializeFirebaseApp() throws IOException {
        
        if (FirebaseApp.getApps() == null || FirebaseApp.getApps().isEmpty()) {
            InputStream serviceAccount = FirebaseInitializer.class.getResourceAsStream("/firebase-service-credentials.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();

            FirebaseApp.initializeApp(options);
        }

    }

    public void onStop(@Observes ShutdownEvent ev) {
        logger.info("The application is stopping...");
    }

}

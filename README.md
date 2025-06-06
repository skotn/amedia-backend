# STEP 1 Ensure frontend docker image is created

# STEP 2 Build backend here
```./gradlew build```

# STEP 3 Dockerize
```docker build -t amedia-skotn-backend:latest .```

# STEP 4 Docker compose the whole thing
```docker-compose up```

# Go to localhost:3000 to check frontend
# Test backend on localhost:8080 (or change the ports in the docker-compose, and the build of frontend)

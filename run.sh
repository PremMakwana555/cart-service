#!/bin/bash
export JAVA_HOME="/Users/premmakwana555/Library/Java/JavaVirtualMachines/ms-21.0.9/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"

echo "Using Java from: $JAVA_HOME"
java -version

echo "Building project..."
./mvnw clean package -DskipTests

echo "Starting Cart Service..."
java -jar -Dspring.profiles.active=local target/cart-service-0.0.1-SNAPSHOT.jar

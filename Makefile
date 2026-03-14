.PHONY: build
.DEFAULT_GOAL := build-run
clean:
	./gradlew clean
build:
	./gradlew build
run:
	java -jar ./build/libs/02-Project-Simulation-1.0-SNAPSHOT-all.jar

build-run: build run

SHELL=/bin/bash

.PHONEY: all
all:
	# Valid targets are:
	# 	- build: 	builds all the implementations 
	#	- test:		runs all automated tests for all implementations
	#	- site:		builds the site with automated docs for all implementations
	#	- clean: 	cleans out all artefacts for all implementations
	#	- all:		provides this summary!


.PHONEY: build
build: build-java
	true

.PHONEY: test
test: test-java
	true

.PHONEY: site
site: site-java
	# Create the skeleton folder structure.
	mkdir -p build-gh-pages
	mkdir -p build-gh-pages/java
	mkdir -p build-gh-pages/java/jarfiles
	# Move the java artefacts that I would like to serve into the right place.
	mv java/target/site/apidocs build-gh-pages/java/docs
	mv java/target/jinxml*.jar build-gh-pages/java/jarfiles/
	# Copy all the markdown pages at top-level.
	cp *.md build-gh-pages/
	cp -r grammar build-gh-pages/
	cp -r images build-gh-pages/
	# Fix up the name of the JinXML jar file to be included in the JavaImplementation.md file.
	./scripts/overwriteJavaImplementation > build-gh-pages/JavaImplementation.md
	# Fill in the jekyll config.
	/bin/echo "theme: jekyll-theme-cayman" > build-gh-pages/_config.yml

.PHONEY: clean
clean: clean-java
	rm -rf build-gh-pages/

.PHONEY: build-java
build-java:
	cd java && mvn -q compile

.PHONEY: test-java
test-java:
	cd java && mvn -q test

.PHONEY: site-java
site-java:
	cd java && mvn -q install
	cd java && mvn -q javadoc:javadoc

.PHONEY: clean-java
clean-java:
	cd java && mvn -q clean

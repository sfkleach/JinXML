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
	mkdir -p build-gh-pages
	mv java/target/site/apidocs build-gh-pages/javadocs
	cp *.md build-gh-pages/
	cp -r grammar build-gh-pages/
	cp -r images build-gh-pages/
	echo "theme: jekyll-theme-modernist" > build-gh-pages/_config.yml
	ls -R build-gh-pages

.PHONEY: clean
clean: clean-java
	true

.PHONEY: build-java
build-java:
	cd java && mvn compile

.PHONEY: test-java
test-java:
	cd java && mvn test

.PHONEY: site-java
site-java:
	cd java && mvn javadoc:javadoc

.PHONEY: clean-java
clean-java:
	cd java && mvn clean

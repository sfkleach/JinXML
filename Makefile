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
	mkdir -p build-gh-pages/java
	mkdir -p build-gh-pages/java/jarfiles
	mv java/target/site/apidocs build-gh-pages/java/docs
	mv java/target/jinxml*.jar build-gh-pages/java/jarfiles/
	cp *.md build-gh-pages/
	cp -r grammar build-gh-pages/
	cp -r images build-gh-pages/
	echo "theme: jekyll-theme-modernist" > build-gh-pages/_config.yml

.PHONEY: clean
clean: clean-java
	rm -rf build-gh-pages/

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

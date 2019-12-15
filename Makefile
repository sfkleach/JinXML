SHELL=/bin/bash

.PHONEY: all
all:
	# Valid targets are:
	# 	- build: 		builds all the implementations 
	#	- test:			runs all automated tests for all implementations
	#	- site:			builds the site with automated docs for all implementations
	#   - release:		builds the file jinxml-$VERSION.zip
	#	- clean: 		cleans out all artefacts for all implementations
	#	- version:		echoes the version number (use with -s flag)
	#	- set-version	updates (hard-codes) all files that reference the version 
	#	- all:			provides this summary!


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
	./scripts/generateJavaImplementation > build-gh-pages/JavaImplementation.md
	# Fill in the jekyll config.
	/bin/echo "theme: jekyll-theme-cayman" > build-gh-pages/_config.yml
	# This is needed to support the custom domain jinxml.org for the site.
	/bin/echo "jinxml.org" > build-gh-pages/CNAME

.PHONEY: release
release: site
	mkdir -p _build
	mv build-gh-pages _build/jinxml-$(shell make -s version)
	( cd _build; zip -r jinxml-$(shell make -s version).zip jinxml-$(shell make -s version) )
	( cd _build; tar cf - jinxml-$(shell make -s version) | gzip > jinxml-$(shell make -s version).tar.gz )
	mv _build/*.{tar.gz,zip} .
	rm -rf _build/
	
.PHONEY: clean
clean: clean-java clean-release
	rm -rf build-gh-pages
	rm -rf _build

.PHONEY: clean-release
clean-release:
	rm -f jinxml-*.zip
	rm -f jinxml-*.tar.gz

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

.PHONEY: version
version:
	echo "1.0.0"

.PHONEY: set-version
set-version:
	python3 scripts/editPOM.py $(shell make -s version) < java/pom.xml > java/pom.xml.tmp
	mv java/pom.xml.tmp java/pom.xml

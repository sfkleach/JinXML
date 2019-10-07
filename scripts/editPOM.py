#!/usr/bin/python3

import subprocess
import sys
import re

def run():
	nmatches = 0
	for line in sys.stdin:
		if nmatches == 0 and re.match( '^\t<version>', line ):
			nmatches += 1
			print( '\t<version>{}</version>'.format( sys.argv[1] ) )
		else:
			print( line, end='' )

if __name__ == "__main__":
	run()

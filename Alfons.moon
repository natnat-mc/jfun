moonbuild = require 'moonbuild'

tasks:
	build: => moonbuild j: true
	run: => moonbuild 'run', j: true
	clean: => moonbuild 'clean'
	mrproper: => moonbuild 'mrproper'

	defs: => sh [[find src -name '*.java' | xargs cat | egrep '^\s+//.+ ::' | sed 's/\t//g' | sed 's|// ||' | sort | uniq]]

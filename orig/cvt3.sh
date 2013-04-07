#!/bin/bash

BD=./drawable

VARS="xhdpi:96x96 hdpi:72x72 mdpi:48x48 ldpi:36x36 javame:32x32"

SRC=$1
TRG=$2
AND="xhdpi hdpi mdpi ldpi"

#exit

for F in *.png; do
	for V in $VARS; do
		N=$(echo $V| cut -d: -f1)
		R=$(echo $V| cut -d: -f2)
		if [ ! -d "${BD}-$N" ]; then
		  mkdir -p "${BD}-$N"
		fi
		echo PNG32:${BD}-$N/$F
		convert $F -scale $R PNG32:${BD}-$N/$F
		# convert $F -adaptive-resize $R ${BD}-$N/$F
		# convert $F -resize $R ${BD}-$N/$F
	done
done

if [ "x$TRG" != "x" -a "x$SRC" != "x" -a -d "$TRG" ]; then
  for CPD in $AND; do
    echo cp "${BD}-${CPD}/${SRC}" "${TRG}/drawable-${CPD}/icon.png"
  done
fi

# eof
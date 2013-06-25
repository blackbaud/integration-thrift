#!/bin/sh

# Generates HTML docs the the .thrift files contained in this project.

export project_home_dir="`dirname $0`"
export generated_docs_dir="$project_home_dir/docs"

# blow away any previously generated docs
rm -rf "$generated_docs_dir"

# generate html documentation from IDL file(s)
mkdir -p "$generated_docs_dir"
thrift -strict -recurse -out "$generated_docs_dir" \
	--gen html \
	"$project_home_dir"/LuminateOnline.thrift

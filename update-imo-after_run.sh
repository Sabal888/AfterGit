#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

echo "Cloning source repository..."
git clone https://github.com/IMOitself/AfterRun.git temp_AfterRun

echo "Copying directory..."
mkdir -p app/src/main/java/imo/after_run
cp -r temp_AfterRun/app/src/main/java/imo/after_run/. app/src/main/java/imo/after_run/

echo "Removing MainActivity.java..."
rm app/src/main/java/imo/after_run/MainActivity.java

echo "Cleaning temp_AfterRun..."
rm -rf temp_AfterRun

echo "Update complete. Review and commit the changes."

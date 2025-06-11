#!/bin/bash

# A script to update a specific folder in AfterGit from the AfterRun repository.

# --- Configuration ---
# The Git repository to clone (source)
SOURCE_REPO="https://github.com/IMOitself/AfterRun.git"
# The temporary directory name after cloning
SOURCE_FOLDER_NAME="AfterRun"
# The root directory of your target repository
DEST_REPO_PATH="." # Assumes you run this script from the root of your AfterGit repo

# The specific sub-folder to copy from the source
SOURCE_SUBFOLDER="app/src/main/java/imo/after_run"
# The destination path for the sub-folder within your target repo
DEST_SUBFOLDER="app/src/main/java/imo/after_run"

# --- Script Execution ---

echo "Starting the update process..."

# 1. Clone the source repository
echo "Cloning IMOitself/AfterRun..."
if ! git clone "$SOURCE_REPO" "$SOURCE_FOLDER_NAME"; then
    echo "Error: Failed to clone the source repository. Aborting."
    exit 1
fi

# 2. Copy the specific directory
SOURCE_PATH="$SOURCE_FOLDER_NAME/$SOURCE_SUBFOLDER"
DEST_PATH="$DEST_REPO_PATH/$DEST_SUBFOLDER"

# Check if the source directory exists
if [ ! -d "$SOURCE_PATH" ]; then
    echo "Error: Source directory '$SOURCE_PATH' does not exist in the cloned repository. Aborting."
    rm -rf "$SOURCE_FOLDER_NAME"
    exit 1
fi

echo "Copying directory from '$SOURCE_PATH' to '$DEST_PATH'..."
# Create the destination parent directories if they don't exist
mkdir -p "$(dirname "$DEST_PATH")"
# Copy the contents recursively
cp -r "$SOURCE_PATH" "$DEST_PATH"

# 3. Remove the cloned repository folder
echo "Cleaning up by removing the cloned folder..."
rm -rf "$SOURCE_FOLDER_NAME"

echo "Update complete!"
echo "The folder '$DEST_SUBFOLDER' has been updated in your AfterGit project."
echo "Don't forget to review the changes and commit them to your repository."
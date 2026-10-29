#!/bin/bash
# ============================================
# Expense Tracker - GitHub Setup Script
# Run this to initialize git and push to GitHub
# ============================================

echo ""
echo "============================================"
echo "  Expense Tracker - GitHub Setup"
echo "============================================"
echo ""

# Check if git is installed
if ! command -v git &> /dev/null; then
    echo "ERROR: Git is not installed!"
    echo "Please install Git from https://git-scm.com/"
    exit 1
fi

echo "Git found: $(git --version)"

echo ""
echo "Initializing Git repository..."
git init

echo ""
echo "Adding all files..."
git add .

echo ""
echo "Creating initial commit..."
git commit -m "Initial commit: Expense Tracker Android App"

echo ""
echo "============================================"
echo "  NEXT STEPS:"
echo "============================================"
echo ""
echo "1. Create a new repository on GitHub:"
echo "   https://github.com/new"
echo "   - Name it: ExpenseTracker"
echo "   - Make it Public or Private"
echo "   - DO NOT initialize with README, .gitignore, or license"
echo ""
echo "2. Copy the repository URL (HTTPS or SSH)"
echo ""
echo "3. Run these commands (replace YOUR_USERNAME):"
echo ""
echo "   git remote add origin https://github.com/YOUR_USERNAME/ExpenseTracker.git"
echo "   git branch -M main"
echo "   git push -u origin main"
echo ""
echo "4. Go to GitHub Actions tab in your repo to watch the build"
echo ""
echo "5. Once build completes, download APK from:"
echo "   - Actions > Build Debug APK > Artifacts > app-debug-apk"
echo "   - OR create a tag (git tag v1.0.0 && git push --tags) for a Release"
echo ""
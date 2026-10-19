#!/usr/bin/env bash
set -Eeuo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
MODULE="$ROOT/flutter_module"
SOURCE_COMMIT="e946b85251ec44656cb868e541c39e291873aa41"

if ! command -v git >/dev/null 2>&1; then
  echo "ERROR: Git is required to prepare the España Outdoor Flutter module." >&2
  exit 1
fi
if ! command -v flutter >/dev/null 2>&1; then
  echo "ERROR: Flutter is required. Add the Flutter SDK to PATH and retry." >&2
  exit 1
fi
if [[ ! -d "$MODULE" ]] || ! git -C "$MODULE" rev-parse --is-inside-work-tree >/dev/null 2>&1; then
  echo "ERROR: Flutter source submodule is missing. Run: git submodule update --init flutter_module" >&2
  exit 1
fi

actual_commit="$(git -C "$MODULE" rev-parse HEAD)"
exclude_file="$(git -C "$MODULE" rev-parse --absolute-git-dir)/info/exclude"
mkdir -p "$(dirname "$exclude_file")"
grep -qxF '/.android/' "$exclude_file" || printf '\n/.android/\n' >> "$exclude_file"
if [[ "$actual_commit" != "$SOURCE_COMMIT" ]]; then
  echo "ERROR: Flutter source mismatch. Expected $SOURCE_COMMIT, got $actual_commit." >&2
  exit 1
fi
if ! grep -q '^  module:' "$MODULE/pubspec.yaml"; then
  echo "ERROR: The pinned Flutter source is missing the add-to-app module declaration." >&2
  exit 1
fi

echo "Preparing España Outdoor Flutter module at $SOURCE_COMMIT"
(
  cd "$MODULE"
  flutter pub get --enforce-lockfile
)

if [[ ! -f "$MODULE/.android/include_flutter.groovy" ]]; then
  echo "ERROR: Flutter did not generate .android/include_flutter.groovy." >&2
  echo "Check the Flutter SDK version and the output above." >&2
  exit 1
fi

echo "Flutter module prepared: $MODULE"

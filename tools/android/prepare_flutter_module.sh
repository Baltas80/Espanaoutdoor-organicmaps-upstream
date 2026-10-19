#!/usr/bin/env bash
set -Eeuo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
MODULE="$ROOT/flutter_module"
SOURCE_URL="https://github.com/Baltas80/Espana-Outdoors.git"
SOURCE_COMMIT="347395727341ebcb2f24ded8211400f9bd5320b4"

if ! command -v git >/dev/null 2>&1; then
  echo "ERROR: Git is required to prepare the España Outdoor Flutter module." >&2
  exit 1
fi
if ! command -v flutter >/dev/null 2>&1; then
  echo "ERROR: Flutter is required. Add the Flutter SDK to PATH and retry." >&2
  exit 1
fi

if [[ -d "$MODULE/.git" ]]; then
  current_commit="$(git -C "$MODULE" rev-parse HEAD 2>/dev/null || true)"
  if [[ "$current_commit" != "$SOURCE_COMMIT" ]]; then
    rm -rf "$MODULE"
  fi
elif [[ -e "$MODULE" ]]; then
  echo "ERROR: $MODULE exists but is not a Git checkout. Move or remove it, then retry." >&2
  exit 1
fi

if [[ ! -d "$MODULE/.git" ]]; then
  git clone --filter=blob:none --no-checkout "$SOURCE_URL" "$MODULE"
  git -C "$MODULE" sparse-checkout init --no-cone
  git -C "$MODULE" sparse-checkout set --no-cone \
    '/pubspec.yaml' '/lib/**' '/assets/**' '/analysis_options.yaml' '/l10n.yaml'
  git -C "$MODULE" checkout --detach "$SOURCE_COMMIT"
fi

actual_commit="$(git -C "$MODULE" rev-parse HEAD)"
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
  flutter pub get
)

if [[ ! -f "$MODULE/.android/include_flutter.groovy" ]]; then
  echo "ERROR: Flutter did not generate .android/include_flutter.groovy." >&2
  echo "Check the Flutter SDK version and the output above." >&2
  exit 1
fi

echo "Flutter module prepared: $MODULE"

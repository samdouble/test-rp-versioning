# test-rp-versioning

Multi-module repo: **core** (shared library), **A**, and **B** (applications).

Build and run from the **repository root** or from **A** / **B** (Gradle will use the root `settings.gradle`):

```bash
# From repo root
./gradlew :A:run
./gradlew :B:run

# Or, from A or B directory:
cd A && ./gradlew :A:run
cd B && ./gradlew :B:run
```

# Git hooks

**Commits that only touch `core/` must also touch `A/` and `B/`** so release-please can attribute them to both modules. This is **enforced in CI**: if you push a commit that only changes files under `core/`, the PR check "Core commits must touch A and B" will fail.

No local setup is required—CI will catch it. To fix a failing commit, add a trivial change to `A/build.gradle` and `B/build.gradle` (e.g. a newline) and amend or add a follow-up commit.

## Optional: run the hook locally

To have the hook add those touches automatically when you commit (so you don’t have to do it by hand):

```bash
git config core.hooksPath .githooks
chmod +x .githooks/pre-commit
```

After that, any commit that only has staged files under `core/` will automatically stage a trivial change to `A/build.gradle` and `B/build.gradle` as well.

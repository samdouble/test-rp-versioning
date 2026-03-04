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

# Patching an older version (e.g. 0.3.x while 1.x is current)

Say you need to ship a security patch for an existing version 0.3.0 (e.g. 0.3.1) without touching the current version (1.x):

1. **Create a maintenance branch** from the tag of the version you want to patch:
   ```bash
   git checkout -b release/moduleA-0.3.x moduleA-v0.3.0
   git push -u origin release/moduleA-0.3.x
   ```

2. **Apply the fix** on that branch (in A, in **core**, or both). The branch has the full repo; if you patch `core/`, the built A 0.3.1 will include that patched core. No need to tag or release core separately.

3. **Bump the version** in `A/build.gradle` to the patch version (e.g. `0.3.1`).

4. **Commit and tag** the release, then push the tag (your existing "Publish A" workflow will run on the tag):
   ```bash
   git add A/build.gradle
   git commit -m "chore(moduleA): release 0.3.1"
   git tag moduleA-v0.3.1
   git push origin release/moduleA-0.3.x
   git push origin moduleA-v0.3.1
   ```

5. **DON'T MERGE** this branch back into `master`. `master` stays on 1.x; release-please will still consider the latest A release as 1.0.0 (1.0.0 > 0.3.1), so future release PRs on `master` are unchanged.

To patch another time (e.g. 0.3.2), repeat from step 2 on `release/moduleA-0.3.x`.

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


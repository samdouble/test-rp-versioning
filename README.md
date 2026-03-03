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
CAS Discovery Server Overlay Template
========================================================

Generic CAS Discovery Server WAR overlay.

# Versions

- CAS Server `@casVersion@`
- JDK `{{javaVersion}}

# Deployment

To build the project, use:

```bash
# Use --refresh-dependencies to force-update SNAPSHOT versions
./gradlew[.bat] clean build
```

To see what commands/tasks are available to the build script, run:

```bash
./gradlew[.bat] tasks
```

On a successful deployment via the following methods, the server will be available at:

* `http://localhost:8761`

## Executable WAR

Run the server web application as an executable WAR.

```bash
java -jar build/libs/app.war 
```

## External

Deploy the binary web application file in `build/libs` after a successful build to a servlet container of choice.

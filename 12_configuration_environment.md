# Configuration and Environment Analysis

## Configuration Model Overview

SIMOG configuration is distributed across Maven descriptors, Java property loading, web descriptors, JBoss descriptors, persistence descriptors, and module-level version files.

The main runtime configuration hub is `SimogProperties`.

## Build Configuration

Primary files:

- root `pom.xml`
- module `pom.xml` files
- `SimogXmlBeansPDD/buildSimogXmlBeansPDD.xml`

Purpose:

- define module assembly
- define Java level and dependencies
- support XMLBeans-related auxiliary build

## Web / Container Descriptors

Primary files:

- `SimogWeb/WebContent/WEB-INF/web.xml`
- `SimogWSPDD/WebContent/WEB-INF/web.xml`
- `SimogWSAGG/WebContent/WEB-INF/web.xml`
- `SimogWSTED/WebContent/WEB-INF/web.xml`
- `SimogWSCommon/WebContent/WEB-INF/web.xml`

What they control:

- servlet/filter declarations
- SOAP endpoint mappings
- welcome files
- datasource resource references

## JBoss-Specific Descriptors

Primary files:

- `*/WebContent/WEB-INF/jboss-web.xml`
- `*/WebContent/META-INF/jboss-classloading.xml`

What they imply:

- deployment targets a JBoss-family container
- classloading behavior is explicitly controlled at module level

## Externalized Application Properties

Primary class:

- `SimogCommon/src/it/avlp/simog/util/SimogProperties.java`

Observed characteristics:

- reads external configuration file
- default path includes `/opt/SIMOG/simog.ini`
- exposes many getters for DB, authentication, WS, TED, file paths, and feature flags

Configuration families visible in code:

- JDBC driver/url/user/password
- datasource names
- login and SAML/IAM settings
- ClamAV or attachment-related parameters
- WS endpoints and interoperability parameters
- CUP/DIPE/RGS-related settings
- TED-related settings
- date-based feature toggles and legal-regulation switches

## Persistence Configuration

Primary files:

- `SimogTEDCommon/src/main/java/META-INF/persistence.xml`
- `SimogWSTED/src/META-INF/persistence.xml`

What they control:

- TED JPA persistence unit
- datasource binding
- entity list

## Version / Module Properties

Visible files include:

- `SimogWeb/WebContent/WEB-INF/simogversion.properties`
- `SimogWSPDD/src/simogWSPDDversion.properties`
- `SimogWSAGG/src/simogWSAGGversion.properties`
- `SimogWSTED/src/simogWSTEDversion.properties`
- `SimogMassLoader/src/massloaderversion.properties`

Purpose:

- embed module version metadata

## Localization

Visible property files:

- `messages_it.properties`
- `messages_ar.properties`

Observed languages:

- Italian
- Arabic

## Datasource and JNDI Assumptions

Direct evidence:

- `jdbc/SIMOG` resource reference in `SimogWeb/web.xml`
- `java:/jdbc/SIMOG` in TED persistence
- datasource lookup logic in `ServletBase` and `ConnectionWSManager`

Operational implication:

- the application is expected to run in an application server with pre-provisioned JNDI resources

## Feature Toggles and Environment Sensitivity

`SimogProperties` contains numerous date-based or option-style flags. This indicates the system may adapt behavior to regulatory deadlines, rollout phases, or environment-specific integrations.

Operational consequence:

- configuration governance is critical
- behavior may change without code changes if external properties are altered

## Configuration Risks

- core operational configuration is outside source control
- configuration breadth is concentrated in one large utility class
- many hidden dependencies are only visible through property names and lookup methods
- reproducing environments may require undocumented external files and container setup

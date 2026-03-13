# Deployment and Runtime View

## Packaging Types

Visible packaging structure:

- parent POM aggregator
- multiple WAR modules
- several shared JAR modules
- one Ant-built support artifact

## Deployable Runtime Units

Directly visible WARs:

- `SimogWeb`
- `SimogWSPDD`
- `SimogWSAGG`
- `SimogWSTED`

Likely deployment model:

- each WAR deployed to an application server
- shared library modules available on application classpath or packaged in WARs

## Application Server Assumptions

Evidence:

- JBoss descriptors
- JNDI datasource lookup patterns
- standard Java EE deployment descriptors

Inference:

The target runtime is a JBoss-family Java EE application server or a compatible environment.

## Database Connectivity

Primary assumptions:

- JNDI datasource `jdbc/SIMOG` or `java:/jdbc/SIMOG`
- fallback direct JDBC driver/url support in some paths

Operational implication:

- runtime provisioning must include datasource definition, credentials, and likely connection-pool tuning

## Filesystem Dependencies

Evidence:

- `SimogProperties` default external config path `/opt/SIMOG/simog.ini`
- mass-loader directory structure
- attachment/document-related configuration hints

Operational implication:

- some runtime behavior depends on host filesystem layout
- deployment is not fully self-contained inside WARs

## Network Dependencies

Visible dependency families:

- remote authentication services
- registry/anagrafe services
- CUP services
- AVCpass-related services
- TED-related external interactions inferred from dedicated subsystem

These imply controlled outbound network access from the application server.

## Certificates / Keystores

No concrete keystore artifacts were observed in the repository inventory. However, SOAP integrations and authentication services likely require transport/security material configured outside source control.

This remains an inferred operational dependency.

## Likely Production Topology

Best-effort reconstruction:

1. browser users access `SimogWeb`
2. external systems call SOAP WARs
3. all application modules connect to shared `SIMOG` database
4. application server accesses external authentication and registry services
5. batch host or scheduled OS job runs `SimogMassLoader`
6. external configuration files and JNDI resources shape environment-specific behavior

## Deployment Constraints

- legacy Java baseline
- heavy reliance on externalized configuration
- container-specific descriptors
- multiple interoperability endpoints to manage and expose
- shared database as central operational dependency

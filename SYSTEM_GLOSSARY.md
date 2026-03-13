# SYSTEM GLOSSARY

## Core Terms

- `SIMOG`: the overall procurement monitoring and management system analyzed in this repository
- `Gara`: procurement procedure or tender
- `Lotto`: lot belonging to a procurement procedure
- `Scheda`: form or process-specific data section used in lifecycle navigation
- `Pubblicazione`: publication-related record or event
- `Utente`: authenticated user

## Integration Terms

- `SOAP`: main interoperability style used by exposed and consumed services
- `WSDL`: service contract artifact for SOAP services
- `XSD`: XML schema artifact used to define payload structures
- `WS Session`: technical session/ticket used to authorize SOAP operations
- `IAA` / `IAM`: authentication-related systems visible in login integration code
- `AUSA`: external administrative-service acronym visible in integration package names
- `AVCPASS`: external interoperability domain visible in service and package names
- `CUP`: external service/integration domain visible in dedicated client packages

## Technical Terms

- `ServletBase`: web runtime base class centralizing session, connection, and transaction support
- `AccessiDB`: central JDBC base/helper class and architectural hub of the legacy core
- `ConnectionWSManager`: shared WS-side connection and transaction helper
- `WSSessionManager`: DB-backed manager of SOAP session/ticket lifecycle
- `TED`: procurement publication subsystem with dedicated modules and entities

## Organizational / Study Visit Terms

- `Technical Knowledge Transfer`: the process of making implicit architecture, domain rules, and runtime assumptions understandable to maintainers and visitors
- `Evidence`: conclusion directly supported by source code, descriptors, or configuration
- `Inference`: conclusion derived from structural or naming evidence but not explicitly documented in code

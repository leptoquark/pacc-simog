# SIMOG Technical Knowledge Transfer Pack

This repository documentation set reconstructs the SIMOG system from source code evidence. It is intended for technical study visits, architecture review, onboarding, and maintenance planning.

## Contents

1. [01_executive_technical_overview.md](01_executive_technical_overview.md)
2. [02_system_architecture.md](02_system_architecture.md)
3. [03_module_analysis.md](03_module_analysis.md)
4. [04_package_class_taxonomy.md](04_package_class_taxonomy.md)
5. [05_request_response_lifecycle.md](05_request_response_lifecycle.md)
6. [06_business_processes.md](06_business_processes.md)
7. [07_domain_model.md](07_domain_model.md)
8. [08_data_model_persistence.md](08_data_model_persistence.md)
9. [09_integrations.md](09_integrations.md)
10. [10_service_exposure.md](10_service_exposure.md)
11. [11_ui_navigation.md](11_ui_navigation.md)
12. [12_configuration_environment.md](12_configuration_environment.md)
13. [13_security_analysis.md](13_security_analysis.md)
14. [14_batch_background_processing.md](14_batch_background_processing.md)
15. [15_error_handling_observability.md](15_error_handling_observability.md)
16. [16_deployment_runtime_view.md](16_deployment_runtime_view.md)
17. [17_legacy_assessment_technical_debt.md](17_legacy_assessment_technical_debt.md)
18. [18_modernization_opportunities.md](18_modernization_opportunities.md)
19. [19_study_visit_support_material.md](19_study_visit_support_material.md)
20. [20_diagram_specs.md](20_diagram_specs.md)
21. [21_deep_dive_architecture.md](21_deep_dive_architecture.md)
22. [SYSTEM_GLOSSARY.md](SYSTEM_GLOSSARY.md)
23. [OPEN_QUESTIONS_AND_ASSUMPTIONS.md](OPEN_QUESTIONS_AND_ASSUMPTIONS.md)

## Repository Scope

The documentation covers the Maven multi-module codebase rooted at `SimogParent`, including:

- `SimogWeb` as the main JSP/Servlet application
- `SimogCommon` as the dominant business and JDBC layer
- `SimogWSCommon`, `SimogWSPDD`, `SimogWSAGG`, and `SimogWSTED` as SOAP-facing service layers
- `SimogAVCPLogin` as the authentication integration module
- `SimogFlusso` and validator workflow controllers as process/state orchestration components
- `SimogMassLoader` as the mass-import batch subsystem
- `SimogTEDCommon` as the TED-specific persistence and service layer


## SIMOG Technical Knowledge Transfer Pack — Final Synthesis

SIMOG is a legacy enterprise procurement-monitoring platform organized as a multi-module Java web system with a hybrid architecture: JSP/Servlet presentation, large procedural manager classes for business logic, direct JDBC persistence for the core application, SOAP service exposure for interoperability, and a more recent JPA-based TED subsystem. The central technical gravity is in `SimogCommon`, `SimogWeb`, and `SimogWSCommon`; these modules define most of the domain behavior, persistence rules, validation logic, and integration orchestration.

Technicians should understand the following first:

- the main user-facing runtime starts in `SimogWeb/WebContent/WEB-INF/web.xml`
- most business flows converge on servlets extending `ServletBase`
- the strongest architectural hub is `it.avlp.simog.db.AccessiDB`
- workflow/state rules are encoded in validator and workflow controller classes rather than in a separate BPM engine
- SOAP interoperability is first-class and implemented through dedicated WARs plus shared action managers
- configuration is highly centralized in `SimogProperties` and depends on external files and container resources

The most difficult parts to maintain are the large DB-centric manager classes, implicit workflow logic scattered across validators and access classes, hand-managed transactions, generated or semi-generated SOAP/XML artifacts, and broad module coupling around shared utility/configuration code. The most valuable parts for reuse and knowledge transfer are the domain process knowledge encoded in managers and validators, the service contracts visible in WSDL/XSD artifacts, and the concrete operational assumptions visible in web descriptors, datasource use, and integration proxies.

During a study visit, the most useful live demonstrations would be:

- one end-to-end web flow such as tender registration and lot management
- one SOAP flow such as `inserisciGara` or `comunicaIniziativa`
- one TED publication flow
- the mass-loader execution path and feedback handling
- the configuration/runtime bootstrap path from `web.xml` to `ServletBase` to DB/integration calls

Several aspects should be explained verbally because they are not obvious from code alone:

- the real production topology and network segregation
- the official meaning of some procurement codes and workflow states
- the operational runbook for failed integrations
- the external authentication infrastructure and its historical evolution
- the exact database schema ownership and release-management process

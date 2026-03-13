# User Interface / Navigation Reconstruction

## UI Technology

The main user interface is implemented as a server-rendered JSP application inside `SimogWeb`. The frontend is not a separate SPA; navigation is controlled by servlets, request dispatching, session state, and JSP views.

Evidence:

- `SimogWeb/WebContent`
- `SimogWeb/WebContent/WEB-INF/web.xml`

## Main UI Structure

Visible UI groupings include:

- authentication and landing pages
- gara management
- lotto management
- scheda-specific pages for procedural phases
- registries and lookup popups
- transaction/log views
- document management screens

## Functional Areas

## Authentication and Session Entry

Likely pages/actions:

- `index.jsp`
- `/checkAuthentication`

Backend classes:

- `SrvAutentica`
- filters and `ServletBase`

## Gara Management

Navigation evidence:

- `/inizializzaGara`
- `/InserisciGara`
- related JSPs under `WebContent`

User actions:

- create or initialize procurement procedure
- edit main tender data
- navigate into lot and scheda flows

## Lotto Management

Navigation evidence:

- `/InserisciLotto`
- `/gestisciLotto`

User actions:

- create lot
- modify lot
- progress lot through perfection and related states

## Scheda-Oriented Navigation

JSP directory structure suggests many phase-specific screens, including:

- `schedaA`
- `sottosoglia`
- `esclusi`
- `adesione`
- `stipula`
- `subappalti`
- `varianti`
- `sospensioni`
- `collaudo`
- `conclusioni`
- `R129`
- `accordo`

Interpretation:

- the UI mirrors the procurement lifecycle through specialized forms rather than a small number of generic pages
- workflow controller logic determines which schede are available or required

## Reference Data and Lookup Screens

Servlet mappings indicate several lookup/search flows:

- `/ricercaCPV`
- `/ricercaIstat`
- `/ricercaNuts`
- `/rubrica*`-related screens

These support structured reference-data selection for business forms.

## Administrative and Support Screens

Evidence suggests pages for:

- logs or transaction views
- document handling
- profile selection or user-specific operations

## Navigation Pattern

The likely navigation model is:

1. user authenticates
2. user selects or enters a procurement item
3. user works through gara and lotto screens
4. user completes phase-specific schede as permitted by workflow state
5. backend persists data and forwards to next JSP or summary view

This is inferred from servlet mappings, JSP naming, and workflow logic.

## Backend Coupling

The UI is tightly coupled to backend servlets and managers:

- navigation decisions are server-side
- session state is central
- validation and workflow feedback are reflected directly in page transitions

## Frontend Assets

Visible assets:

- JavaScript in `script`
- CSS in `theme`
- auxiliary UI widgets in `tabs`, `calendar`, `xtree`
- TLDs for reusable tag functionality

## UI Observations

- the UI reflects a legacy enterprise web pattern
- business concepts are exposed directly in page structure
- navigation is strongly process-oriented rather than task-dashboard oriented
- maintainability of UI flows depends heavily on servlet and session logic

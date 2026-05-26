# AGENTS.md

This file documents the agentic principles and technical context for the `atpkt` repository.

## Technical Philosophy
1.  **Modular Decoupling**: All core ATProtocol logic is strictly independent of any higher-level service.
2.  **Type Safety via Automation**: Data models are auto-generated from official ATProtocol schema definitions to prevent protocol drift.
3.  **Namespaced API**: Public API structure mirrors the official TypeScript SDK to facilitate developer familiarity and protocol alignment.
4.  **Verifiability**: Repository state management is built on content-addressed structures (MST/CID), providing cryptographic proof of data integrity.

## Development Workflow
- **Code Generation**: All new API endpoints should be integrated via the `LexiconGenerator` (using `KotlinPoet`) rather than manual mapping.
- **Verification**: Run `./gradlew build` to confirm integration of new generated classes.
- **Commit Scoping**: Keep commits atomic and scoped by feature (e.g., `feat(gen)`, `feat(mst)`).
- **Cross-Reference**: When modifying protocol-level components, cross-reference the official `bluesky-social/atproto` repository logic to ensure parity.

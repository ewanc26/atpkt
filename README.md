# atpkt

A professional-grade, modular ATProtocol SDK for Kotlin.

## Overview
`atpkt` provides the foundational building blocks for interacting with the Authenticated Transfer Protocol (AT Protocol). Designed with a library-first architecture, it decouples core networking, authentication, and repository management logic from specific service implementations.

## Architecture
`atpkt` follows the official ATProtocol "Agent" design:
- **Core Library**: Networking, XRPC client, and Session management.
- **Namespaced API**: Hierarchical access to protocol endpoints (e.g., `agent.com.atproto.*`, `agent.app.bsky.*`).
- **Lexicon Registry**: Auto-generation of type-safe Kotlin models from official schema definitions.
- **Repository Foundations**: Content-addressed storage and Merkle Search Tree (MST) structures.

## Roadmap
- [x] Core extraction (Tid, AtProtoClient, SessionManager, RecordManager)
- [x] Namespaced API structure
- [x] AST-driven Lexicon generation (KotlinPoet)
- [x] MST & CID foundations
- [ ] Full XRPC Subscription support
- [ ] DID/PLC Identity resolution
- [ ] OAuth2 / DPoP compliance

## Getting Started
Add `atpkt` as a dependency in your Kotlin/Minecraft project. Use the `Agent` class as the central entry point for all protocol interactions.

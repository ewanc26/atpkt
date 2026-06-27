# atpkt

> **Deprecated.** This project is no longer maintained. Use [atproto-kotlin](https://github.com/kikin81/atproto-kotlin) instead.

A professional-grade, modular AT Protocol SDK for Kotlin.

## Overview
`atpkt` provides the foundational building blocks for interacting with the Authenticated Transfer Protocol (AT Protocol). Designed with a library-first architecture, it decouples core networking, authentication, and repository management logic from specific service implementations.

## Key Features
- **High-Level Agent**: Developer-friendly `AtpAgent` with namespaced access (e.g., `agent.app.bsky.feed.*`).
- **OAuth2 & DPoP**: Robust session management with `OAuthSessionManager` and persistent `SessionStore`.
- **Jetstream Streaming**: High-efficiency, JSON-based real-time event streaming via `JetstreamClient`.
- **Automated API Surface**: 100% Lexicon coverage generated via KotlinPoet AST.
- **Robust XRPC**: Low-level `XrpcClient` with type-safe queries, procedures, and protocol error handling.
- **Identity Resolution**: Full support for DID/PLC and handle resolution.

## Architecture
`atpkt` follows a modular design inspired by `atproto-js` and `atproto.blue`:
- **Core Library**: Networking, XRPC client, and Session management.
- **Namespaced API**: Hierarchical access to protocol endpoints.
- **Lexicon Registry**: Auto-generation of type-safe Kotlin models from official schemas.
- **Streaming**: Reactive, authenticated WebSocket subscription client.

## Getting Started

### Installation
Add `atpkt` to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("uk.ewancroft:atpkt:0.1.0")
}
```

### Basic Usage
```kotlin
val agent = AtpAgent()

// Login and Session Management
val session = agent.login("handle.bsky.social", "app-password")
println("Logged in as ${session.did}")

// Using Namespaced API
val profile = agent.app.bsky.actor.getProfile(did = session.did)
println("Display Name: ${profile.displayName}")
```

### Real-time Streaming (Jetstream)
```kotlin
val jetstream = JetstreamClient()
jetstream.subscribe(wantedCollections = listOf("app.bsky.feed.post"))
    .collect { event ->
        println("New Post from ${event.did}: ${event.commit?.record}")
    }
```

## Testing
The SDK includes a comprehensive test suite using **Kotest** and **MockK**.
Run tests with:
```bash
./gradlew test
```

## Contributing
Please see [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on code generation and development workflows.

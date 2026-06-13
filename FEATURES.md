# atpkt v1.0.0 Feature Guide

This guide provides in-depth technical documentation for the core modules introduced in the v1.0.0 release.

## 1. AtpAgent & Namespaces
The `AtpAgent` is the primary entry point for the SDK. It organizes the entire AT Protocol API surface into hierarchical namespaces mirroring the official Lexicons.

- **com.atproto**: Repository management, sync, and server operations.
- **app.bsky**: Social features including feed, graph, and actor profiles.

## 2. OAuth & Session Management
The `OAuthSessionManager` handles the complex lifecycle of AT Protocol authentication, including:
- **DPoP Compliance**: Automatic generation and management of DPoP proof keys.
- **Persistence**: Integration with `SessionStore` for cross-restart session recovery.
- **Auto-Refresh**: Seamlessly uses refresh tokens to maintain active sessions.

## 3. Jetstream Streaming
Unlike the standard CBOR firehose, `JetstreamClient` provides a high-performance JSON stream.
- **Filtering**: Subscribe only to specific DIDs or Collections.
- **Coroutines**: Built on Kotlin `Flow` for easy integration into asynchronous applications.

## 4. Low-Level XRPC
For advanced use cases, the `XrpcClient` provides direct access to:
- **Type-Safe Queries**: `client.query(...)`
- **Type-Safe Procedures**: `client.procedure(...)`
- **Error Decoding**: Automatic parsing of XRPC error frames into Kotlin exceptions.

## 5. Development & Generation
The SDK's API surface is generated using `FullLexiconGenerator`. This ensures that `atpkt` can achieve 100% parity with the protocol specs by transforming Lexicon JSON files into KotlinPoet ASTs.

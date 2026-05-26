# Contributing to atpkt

Thank you for your interest in contributing to `atpkt`! We aim to maintain a high-quality, protocol-compliant Kotlin implementation of the ATProtocol.

## Core Philosophy
1. **Protocol Parity**: When implementing new features, cross-reference the official [bluesky-social/atproto](https://github.com/bluesky-social/atproto) repository to ensure alignment with protocol specifications.
2. **Type Safety & Automation**: Favor auto-generated code over manual implementation. Use `LexiconGenerator` to build data models.
3. **Modular Decoupling**: Keep core infrastructure (networking/crypto/identity) separate from higher-level service implementations.

## How to Contribute
1. **Fork the repository** and clone your fork.
2. **Propose changes** via a pull request. Ensure changes are atomic and scoped.
3. **Automate**: If adding a new API endpoint, update the lexicon resources and run the `generateLexiconClasses` Gradle task.
4. **Test**: Ensure all new features are covered by tests in the `uk.ewancroft.atpkt` package.

## Development Environment
- The project uses Gradle and Nix.
- Run tests with `./gradlew test`.
- Run generation with `./gradlew :atpkt:generateLexiconClasses`.

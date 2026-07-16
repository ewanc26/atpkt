# AGENTS.md

Guidance for `atpkt`, a deprecated experimental Kotlin/JVM AT Protocol SDK. The README directs users to `atproto-kotlin`; do not add features or present this project as production-ready unless the owner explicitly reactivates it. Historical/security repairs should remain narrow.

## Actual state and layout

- `client/`, `core/`, and `agent/` contain overlapping low/high-level XRPC, session, record, and namespace experiments. The README's `agent.login(...)` example is not implemented, query parameters in `client/XrpcClient.buildUrl` currently serialize to an empty string, and many namespace methods are `TODO("not yet implemented")`.
- `oauth/` implements metadata discovery, PAR, PKCE, token exchange/refresh, DPoP proof generation, and pluggable session stores. Session serialization includes refresh/access tokens and the raw private DPoP key; storage confidentiality depends entirely on the chosen `SessionStore`.
- `did/` resolves `did:plc` and a limited `did:web` form with an in-memory cache. `subscription/` has Jetstream JSON and subscription-stream clients. Network endpoints, cursors, reconnection, cancellation, and resource ownership need explicit lifecycle handling.
- `repo/` contains custom DAG-CBOR, CID, CAR, MST, commit, and blockstore implementations, including SQLite. These are cryptographic/wire-format experiments, not proven repository compatibility simply because they compute hashes.
- `src/main/resources/lexicons/` is a large schema snapshot. `gen/` writes Kotlin through KotlinPoet into `src/main/kotlin`; `generated/` is checked in, but Gradle excludes most of it and selectively recompiles only a handful of packages.
- There is no `src/test` tree. Kotest, JUnit, MockK, and the Ktor mock client are declared but the README's “comprehensive test suite” claim is false. `FEATURES.md` says v1.0.0 while Gradle publishes 0.1.0.

## Protocol and security constraints

- Never claim full lexicon/API parity from schema presence or generated data classes. Coverage requires correct method parameters, encodings, auth, headers, unions, errors, subscriptions, and compiled public entrypoints, verified against the relevant official AT Protocol source/spec and current replacement SDK.
- Preserve canonical DAG-CBOR map ordering, CID codecs/multihashes, CAR varints/tag-42 links, MST layer and mutation semantics, commit signing bytes, TID constraints, repository key ordering, and atomic SQLite/blockstore behavior. Use upstream fixtures and independent decoders; round-tripping through the same code is insufficient.
- OAuth/DPoP must validate issuer/resource metadata, exact redirect URI/client ID, authorization state, PKCE, endpoint HTTPS/SSRF rules, token type, audience, nonce retry, `ath`, normalized `htu`, ES256 JOSE signature encoding/low-S expectations, refresh rotation, and resource-server proofs. Do not include response bodies containing tokens in exceptions/logs.
- DIDs identify repositories; handles are mutable. Validate DID syntax/document ID and exact `#atproto_pds` service type, support the specified `did:web` path/port encoding if claimed, bound redirects/responses/timeouts, and prevent private-network resolution when endpoints originate in untrusted documents.
- Session stores must protect tokens and DPoP private keys at rest, write atomically, isolate clients/DIDs, and delete/revoke consistently. Never ship a plaintext file/example store as “secure persistence.”
- Streaming clients must URL-encode filter values, bound their sizes, expose malformed events/errors, reconnect with cursor/backoff where promised, close Ktor clients, and propagate cancellation. Silent event drops are data loss, not resilience.

## Generation and compatibility

- For an explicitly requested schema refresh, record the upstream lexicon source/revision, update resources first, run `./gradlew generateLexiconClasses`, and inspect the complete add/change/delete diff. The generator catches individual failures and continues, so a successful task does not prove complete output.
- Do not hand-edit generated files unless repairing the generator is impossible and the exception is documented. Keep the Gradle inclusion list aligned with every public generated type; checked-in but excluded classes are not usable API.
- Preserve group/artifact/version, Kotlin/JVM/serialization contracts, ESM-inspired namespace API only where implemented, and replacement/deprecation notices. Do not publish 0.1.0 artifacts or change deprecation status without explicit direction.

## Validation

- Run `./gradlew clean test build`; also run `./gradlew generateLexiconClasses` only when intentionally auditing generated output, since it rewrites source. Use `nix flake check`/the JDK 25 dev shell when the flake changes.
- Because tests are absent, add focused fixtures for any repaired component: XRPC arrays/optional params and binary bodies, error envelopes, OAuth nonce/state/DPoP vectors, DID methods, Jetstream cancellation/reconnect, CAR/CID/DAG-CBOR, MST insertion/deletion, commit proofs, and SQLite restart/atomicity.
- Compare protocol-sensitive behavior with authoritative upstream checkouts/spec fixtures and the maintained `atproto-kotlin` replacement. Compilation alone is not compatibility evidence.
- Never use real accounts/tokens, publish artifacts, overwrite the lexicon/generated tree incidentally, or commit Gradle/Nix/build output, SQLite state, OAuth sessions, keys, CAR repositories, or logs.

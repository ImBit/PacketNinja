# AGENTS Guide: PacketNinja

## What this repo is
- Fabric client-side mod (`minecraft` 1.21.11, Java 21) for packet interception/logging.
- Primary runtime logic is in `src/client/java`; `src/main/java` only contains a minimal common initializer.

## Core architecture (read these first)
- Entrypoints are declared in `src/main/resources/fabric.mod.json`:
  - client init: `xyz.bitsquidd.ninja.PacketInterceptorMod`
  - Mod Menu config hook: `xyz.bitsquidd.ninja.config.ConfigMenu`
- Packet tap is injected via Mixin in `src/client/java/xyz/bitsquidd/ninja/mixin/MixinConnection.java`:
  - intercepts outgoing `Connection#doSendPacket`
  - intercepts incoming `Connection#channelRead0`
  - unwraps `BundlePacket` and forwards each sub-packet.
- Runtime flow: Mixin -> `PacketInterceptorMod.logPacket(...)` -> `PacketFilter.shouldInterceptPacket(...)` -> `PacketLogger.addPacket(...)` -> chat output.

## Handler system (project-specific)
- Packet formatters are `PacketHandler<T>` implementations under `src/client/java/xyz/bitsquidd/ninja/handler/impl/**`.
- Registration is reflection-based (`PacketRegistry` + `org.reflections.Reflections`) scanning package `xyz.bitsquidd.ninja.handler.impl`.
- New handlers must have:
  - concrete class (not abstract), public no-arg constructor
  - `super(PacketClass, "FriendlyName", "Description", PacketType.CLIENTBOUND|SERVERBOUND)`
  - `getPacketInfoInternal(...)` returning `PacketInfoBundle` + `PacketInfoSegment` list.
- `/packets filter` suggestions and toggle lookup come from handler `friendlyName` (`PacketRegistry.findHandler`).

## Formatting and UI conventions
- Chat output uses Adventure components (`PacketLogger`, `PacketInfoBundle`) with custom glyph icons from `src/client/resources/assets/minecraft/font/default.json` and `.../textures/text/font/icons.png`.
- Use `FormatHelper` for repeated formatting patterns (positions, rotations, capped lists, item stacks).
- Packet categories/colors are centralized in `PacketType`; command response styles in `ResponseType`.

## Config and commands
- Config file is JSON at Fabric config dir: `packetninja.json` (`Config.java`), loaded on client init.
- Mod Menu screen (`ConfigScreen`) currently exposes `packetDelayMs` with 50ms snapping.
- Commands are client-only in `PacketInterceptionCommand`:
  - `/packets start`, `/packets stop`, `/packets filter [packetName]`.

## Build/dev workflow (verified from repo files)
- Build uses Gradle + Fabric Loom (`build.gradle`), Java release target is 21.
- Wrapper script may not be executable in some environments; use `bash ./gradlew <task>` if needed.
- In this environment, Gradle tasks could not be listed because `JAVA_HOME`/`java` was missing.
- No test sources were found (`**/*Test*.java`).

## Known implementation watch-outs
- `PacketInterceptorMod.MOD_ID` is `packet-interceptor` while mod id in `fabric.mod.json` is `packet-ninja`.

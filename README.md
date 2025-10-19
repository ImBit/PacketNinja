# 🥷 Packet Ninja

<p>A simple mod used to log incoming and outgoing packets for Java Minecraft.
<p>The aim is to make working with packets, specifically during development, a little easier to debug! :)

## Features:

### Pretty packet formatting:

There are plans on supporting all packets; currently there is full formatting for the essentials:

```java
// Clientbound
ClientboundAddEntityPacket;
ClientboundCustomPayloadPacket;
ClientboundRemoveEntitiesPacket;
SetPassengersHandler;

// Serverbound
ServerboundInteractPacket;
ServerboundPlayerActionPacket;
ServerboundSwingPacket;
```

> [!WARNING]  
> This mod is under active development, not all packet types have nice formatting (if any).
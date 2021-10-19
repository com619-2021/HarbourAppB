# HarbourAppB
The 'harbour master' application deals with sending pilots out to retrieve inbound ships and bring them into berths to be unloaded.

More information (including diagrams) can be found on the [wiki](https://github.com/com619-2021/HarbourAppB/wiki).

## Required REST endpoints
For our application to function, we require the following REST endpoints from other groups:

1. /get_ship_id (*insert group here*): an endpoint that returns the ID of the ship that is inbound. The ID refers to a unique value in the 'ships' table of the database, which contains a list of ships and their properties (length, width, depth, etc.).

2. /get_booking_id (*insert group here*): an endpoint that returns the ID of the booking that is next to be dealt with. We will use the booking ID to lookup the berth that is reserved for the ship as well as the time to start leading them in. We may also use this ID to verify whether the current ship that is currently waiting is the expected one.

The required endpoints **may** be inaccurate at this point and are absolutely subject to change. 

## Exposed REST endpoints
TODO: consider which REST endpoints we need to expose for other groups.

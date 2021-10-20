# Harbour Master Application (Group B)
The 'harbour master' application deals with sending pilots out to retrieve inbound ships and bring them into berths to be unloaded.

More information (including diagrams) can be found on the [wiki](https://github.com/com619-2021/HarbourAppB/wiki).

## Required REST endpoints
For our application to function, we require the following REST endpoints from other groups:

1. /get_inbound_booking_id (*insert group here*): an endpoint that returns the booking ID of the ship that is currently awaiting piloting. The booking ID will be used to retrieve ship properties (length, width) from the 'ships' table using the 'ship ID', as well as coordinates of the reserved berth from the 'berths' table using the 'berth ID'.

TODO: Confirm which REST endpoints are required.

## Exposed REST endpoints
Our application will expose the following REST endpoints:

1. /piloting_status?booking=xxxxxxxx: returns the status of the booking's piloting. Example messages could be "awaiting arrival", "bringing into berth x", or "ship successfully brought in".

TODO: Consider other endpoints that may be needed by others.

## Required Database Tables
You may refer to our [entity-relationship diagram](./documentation/entity-relationship.drawio.png) for a visual representation of our database requirements.

We require the following tables to be present in the shared database:

1. ships: a table containing all the possible ships that the port is able to handle as well as their different properties. We require the **ship_id**, **length**, **width**, **weight**, **minimum_water_depth**, and **maximum_water_depth** fields to be present. The **weight** may be supplied with the booking instead of having a permanent value in the **ships** table as a ship is likely to carry different amounts of weight each time they're inbound.

2. bookings: a table containing the bookings. We require the **booking_id**, **start_time**, **end_time**, and **assigned_berth** fields to be present.

3. berths: a table containing all the berths in the port. We require the **berth_id**, **lat**, and **lon** fields to be present.

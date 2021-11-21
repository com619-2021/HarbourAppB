package com.devops.groupb.harbourmaster.service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import com.devops.groupb.harbourmaster.dao.GPSDAO;
import com.devops.groupb.harbourmaster.dao.WaitingLocationDAO;
import com.devops.groupb.harbourmaster.dao.OrderDAO;
import com.devops.groupb.harbourmaster.dto.Berth;
import com.devops.groupb.harbourmaster.dto.GPS;
import com.devops.groupb.harbourmaster.dto.OrderStatus;
import com.devops.groupb.harbourmaster.dto.WaitingLocation;
import com.devops.groupb.harbourmaster.dto.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GPSService {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	/* information about where the pilot waits and the speed
	   of the boat. */
	private final double pilotSpeedMph = 45;
	private final double pilotLat = 50.894533;
	private final double pilotLon = -1.408522;

	/* the point at which a ship being led out is considered
	   'out of port'. */
	private final double exitLat = 50.703208;
	private final double exitLon = -0.967744;

	@Autowired
	private GPSDAO GPSDao;

	@Autowired
	private WaitingLocationDAO waitingLocationDAO;

	@Autowired
	private OrderDAO orderDAO;

	public GPS pingPresence(UUID shipUUID) {
		/* checks to see whether the location of this ship has already
		   been found. */
		GPS existingGPS = GPSDao.findByShipUUID(shipUUID);
		if (existingGPS != null) {
			return existingGPS;
		}

		/* a ship may only be waiting, at most, two hours before their
		   allocated time of arrival. */
		LocalDateTime time = LocalDateTime.now();
		LocalDateTime earliestTime = time.minusHours(2L);

		Order order = orderDAO.findByShipUUID(shipUUID);

		/* ships with no orders or orders that have been denied
		   or cancelled will not come to port. */
		if (order == null || order.getStatus() == OrderStatus.DENIED || order.getStatus() == OrderStatus.CANCELLED) {
			return null;
		}

		LocalDateTime allocatedTime = order.getAllocatedTime();

		/* checks to see whether the current time is between the time that
		   the order has been allocated and the absolute earliest that they
		   may be waiting. */
		if (allocatedTime.isAfter(time) && earliestTime.isBefore(time)) {
			/* randomises the chance of a ship appearing. not every ping
			   means a ship will be waiting, adding some variance. */
			Random rand = new Random();
			int n = rand.nextInt(3); // 1 in 3 chance.

			if (n == 1) {
				/* makes a random id to select from the waiting_location table. */
				int randomId = 3; // had problems with rand.nextInt w/ wldao.count(); will fix.
				WaitingLocation location = waitingLocationDAO.findById(randomId);
				GPS GPS = new GPS(order.getShip(), location);

				return GPS;
			} else {
				return null;
			}
		} else {
			/* ship can't possibly be waiting, hence return null */
			return null;
		}
	}

	public LocalDateTime calculateArrival(GPS gps) {
		if (gps == null) {
			return null;
		}
		double shipLat = gps.getLocation().getLat();
		double shipLon = gps.getLocation().getLon();

		double distToShip = 0;
		if ((pilotLat == shipLat) && (pilotLon == shipLon)) {
			distToShip = 0;
		} else {
			double theta = pilotLon - shipLon;
			distToShip = Math.sin(deg2rad(pilotLat)) * Math.sin(deg2rad(shipLat))
				+ Math.cos(deg2rad(pilotLat)) * Math.cos(deg2rad(shipLat)) * Math.cos(deg2rad(theta));
			distToShip = Math.acos(distToShip);
			distToShip = rad2deg(distToShip);
			distToShip = distToShip * 60 * 1.1515;
		}

		double timeToShip = (distToShip / pilotSpeedMph) * 60;

		log.info("Distance from pilot to ship: " + distToShip + " miles.");
		log.info("Estimated time to get to ship: " + timeToShip + " minutes.");

		Berth berth = orderDAO.findByShipUUID(gps.getShip().getUUID()).getBerth();

		double berthLat = berth.getLat();
		double berthLon = berth.getLon();

		log.info("Destination Berth: " + berth + ".");

		double distBackToBerth = 0;
		if ((shipLat == berthLat) && (shipLon == berthLon)) {
			distBackToBerth = 0;
		} else {
			double theta = berthLon - shipLon;
			distBackToBerth = Math.sin(deg2rad(berthLat)) * Math.sin(deg2rad(shipLat))
				+ Math.cos(deg2rad(berthLat)) * Math.cos(deg2rad(shipLat)) * Math.cos(deg2rad(theta));
			distBackToBerth = Math.acos(distBackToBerth);
			distBackToBerth = rad2deg(distBackToBerth);
			distBackToBerth = distBackToBerth * 60 * 1.1515;
		}

		double shipSpeedMph = 26.83; // random value
		double timeBackToBerth = (distBackToBerth / shipSpeedMph) * 60;

		log.info("Distance from ship to berth: " + distBackToBerth + " miles.");
		log.info("Estimated time from ship to berth: " + timeBackToBerth + " minutes.");

		LocalDateTime eta = LocalDateTime.now().plusMinutes(Math.round(timeToShip + timeBackToBerth));

		log.info("Total time: " + (timeToShip + timeBackToBerth) + " minutes.");
		log.info("Total distance to cover: " + (distToShip + distBackToBerth) + " miles.");
		log.info("ETA of ship to berth: " + eta + ".");

		Order order = orderDAO.findByShipUUID(gps.getShip().getUUID());
		order.setStatus(OrderStatus.IN_PROGRESS);

		return eta;
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}
}

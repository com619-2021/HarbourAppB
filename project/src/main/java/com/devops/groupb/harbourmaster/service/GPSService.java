package com.devops.groupb.harbourmaster.service;

import java.util.UUID;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devops.groupb.harbourmaster.dto.Ship;
import com.devops.groupb.harbourmaster.dto.GPS;
import com.devops.groupb.harbourmaster.dto.Order;
import com.devops.groupb.harbourmaster.dto.WaitingLocation;

import com.devops.groupb.harbourmaster.dao.GPSDAO;
import com.devops.groupb.harbourmaster.dao.WaitingLocationDAO;
import com.devops.groupb.harbourmaster.dao.OrderDAO;

@Service
public class GPSService {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private GPSDAO GPSDao;

	@Autowired
	private WaitingLocationDAO waitingLocationDAO;

	@Autowired
	private OrderDAO orderDAO;

	public GPS pingPresence(Ship ship) {
		/* checks to see whether the location of this ship has already
		   been found. */
		GPS existingGPS = GPSDao.findByShipUUID(ship.getUUID());
		if (existingGPS != null) {
			return existingGPS;
		}

		/* a ship may only be waiting, at most, two hours before their
		   allocated time of arrival. */
		LocalDateTime time = LocalDateTime.now();
		LocalDateTime earliestTime = time.minusHours(2L);

		Order order = orderDAO.findByShipUUID(ship.getUUID());
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
				/* makes a random id to select from the waiting_location table. consider
				   changing just in case amount of rows in table changes in the future */
				int randomId = rand.nextInt(8);
				WaitingLocation location = waitingLocationDAO.findById(randomId);
				GPS GPS = new GPS(ship, location);

				return GPS;
			} else {
				return null;
			}
		} else {
			/* ship can't possibly be waiting, hence return null */
			return null;
		}
	}
}

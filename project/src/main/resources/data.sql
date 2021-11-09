DELETE FROM waiting_location;
DELETE FROM tides;

INSERT INTO waiting_location (lat, lon) VALUES (50.7071, -1.0555);
INSERT INTO waiting_location (lat, lon) VALUES (50.6979, -1.0442);
INSERT INTO waiting_location (lat, lon) VALUES (50.6784, -0.9538);
INSERT INTO waiting_location (lat, lon) VALUES (50.6508, -1.0217);
INSERT INTO waiting_location (lat, lon) VALUES (50.6203, -1.0471);
INSERT INTO waiting_location (lat, lon) VALUES (50.6686, -0.9524);
INSERT INTO waiting_location (lat, lon) VALUES (50.6930, -0.9599);
INSERT INTO waiting_location (lat, lon) VALUES (50.6451, -1.0705);

INSERT INTO tides (day, height, start, end) VALUES (0, 1.7, '00:00:00.000000', '02:56:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (0, 4.6, '02:57:00.000000', '06:19:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (0, 0.7, '06:20:00.000000', '12:59:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (0, 4.7, '13:00:00.000000', '15:10:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (0, 4.5, '15:11:00.000000', '18:42:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (0, 0.8, '18:43:00.000000', '23:59:59.999999');

INSERT INTO tides (day, height, start, end) VALUES (1, 0.8, '00:00:00.000000', '01:28:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (1, 4.6, '01:29:00.000000', '03:38:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (1, 4.5, '03:39:00.000000', '07:05:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (1, 1.1, '07:06:00.000000', '13:54:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (1, 4.5, '13:55:00.000000', '15:19:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (1, 4.4, '15:20:00.000000', '19:29:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (1, 1.1, '19:30:00.000000', '23:59:59.999999');

INSERT INTO tides (day, height, start, end) VALUES (2, 1.1, '00:00:00.000000', '02:39:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (2, 4.4, '02:40:00.000000', '04:18:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (2, 4.4, '04:19:00.000000', '07:58:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (2, 1.5, '07:59:00.000000', '14:14:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (2, 4.1, '14:15:00.000000', '17:14:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (2, 4.1, '17:15:00.000000', '20:28:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (2, 1.5, '20:29:00.000000', '23:59:59.999999');

INSERT INTO tides (day, height, start, end) VALUES (3, 1.5, '00:00:00.000000', '03:00:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (3, 4.0, '03:01:00.000000', '05:45:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (3, 4.1, '05:46:00.000000', '09:08:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (3, 1.9, '09:09:00.000000', '15:25:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (3, 3.9, '15:26:00.000000', '18:15:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (3, 3.8, '18:16:00.000000', '21:47:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (3, 1.8, '21:48:00.000000', '23:59:59.999999');

INSERT INTO tides (day, height, start, end) VALUES (4, 1.8, '00:00:00.000000', '04:13:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (4, 3.9, '04:14:00.000000', '06:58:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (4, 4.0, '06:59:00.000000', '10:31:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (4, 2.1, '10:32:00.000000', '16:37:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (4, 3.8, '16:38:00.000000', '19:42:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (4, 3.6, '19:43:00.000000', '23:07:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (4, 1.9, '23:08:00.000000', '23:59:59.999999');

INSERT INTO tides (day, height, start, end) VALUES (5, 1.9, '00:00:00.000000', '05:19:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (5, 3.9, '05:20:00.000000', '08:24:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (5, 3.9, '08:25:00.000000', '11:44:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (5, 2.0, '11:45:00.000000', '17:38:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (5, 3.7, '17:39:00.000000', '22:18:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (5, 1.7, '22:19:00.000000', '23:59:59.999999');

INSERT INTO tides (day, height, start, end) VALUES (6, 1.7, '00:00:00.000000', '04:19:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (6, 3.9, '04:20:00.000000', '07:12:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (6, 4.1, '07:13:00.000000', '12:55:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (6, 1.8, '12:56:00.000000', '16:08:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (6, 3.9, '16:09:00.000000', '22:45:59.999999');
INSERT INTO tides (day, height, start, end) VALUES (6, 1.8, '22:46:00.000000', '23:59:59.999999');

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

INSERT INTO tides (height, start, end) VALUES (4.8, '2021-11-08 00:36:00', '2021-11-08 02:56:59');
INSERT INTO tides (height, start, end) VALUES (4.6, '2021-11-08 02:57:00', '2021-11-08 06:19:59');
INSERT INTO tides (height, start, end) VALUES (0.7, '2021-11-08 06:20:00', '2021-11-08 12:59:59');
INSERT INTO tides (height, start, end) VALUES (4.7, '2021-11-08 13:00:00', '2021-11-08 15:10:59');
INSERT INTO tides (height, start, end) VALUES (4.5, '2021-11-08 15:11:00', '2021-11-08 18:42:59');
INSERT INTO tides (height, start, end) VALUES (0.8, '2021-11-08 18:43:00', '2021-11-09 01:28:59');

INSERT INTO tides (height, start, end) VALUES (4.6, '2021-11-09 01:29:00', '2021-11-09 03:38:59');
INSERT INTO tides (height, start, end) VALUES (4.5, '2021-11-09 03:39:00', '2021-11-09 07:05:59');
INSERT INTO tides (height, start, end) VALUES (1.1, '2021-11-09 07:06:00', '2021-11-09 13:45:59');
INSERT INTO tides (height, start, end) VALUES (4.5, '2021-11-09 13:55:00', '2021-11-09 15:19:59');
INSERT INTO tides (height, start, end) VALUES (4.4, '2021-11-09 15:20:00', '2021-11-09 19:29:59');
INSERT INTO tides (height, start, end) VALUES (1.1, '2021-11-09 19:30:00', '2021-11-10 02:39:59');

INSERT INTO tides (height, start, end) VALUES (4.4, '2021-11-10 02:40:00', '2021-11-10 04:18:59');
INSERT INTO tides (height, start, end) VALUES (4.4, '2021-11-10 04:19:00', '2021-11-10 07:58:59');
INSERT INTO tides (height, start, end) VALUES (1.5, '2021-11-10 07:59:00', '2021-11-10 14:14:59');
INSERT INTO tides (height, start, end) VALUES (4.1, '2021-11-10 14:15:00', '2021-11-10 17:14:59');
INSERT INTO tides (height, start, end) VALUES (4.1, '2021-11-10 17:15:00', '2021-11-10 20:28:59');
INSERT INTO tides (height, start, end) VALUES (1.5, '2021-11-10 20:29:00', '2021-11-11 03:00:59');

INSERT INTO tides (height, start, end) VALUES (4.0, '2021-11-11 03:01:00', '2021-11-11 05:45:59');
INSERT INTO tides (height, start, end) VALUES (4.1, '2021-11-11 05:46:00', '2021-11-11 09:08:59');
INSERT INTO tides (height, start, end) VALUES (1.9, '2021-11-11 09:08:00', '2021-11-11 15:25:59');
INSERT INTO tides (height, start, end) VALUES (3.9, '2021-11-11 15:26:00', '2021-11-11 18:15:59');
INSERT INTO tides (height, start, end) VALUES (3.8, '2021-11-11 18:16:00', '2021-11-11 21:47:59');
INSERT INTO tides (height, start, end) VALUES (1.8, '2021-11-11 21:48:00', '2021-11-12 01:02:59');

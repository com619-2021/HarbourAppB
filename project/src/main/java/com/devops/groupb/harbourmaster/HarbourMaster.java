package com.devops.groupb.harbourmaster;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.DayOfWeek;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import com.devops.groupb.harbourmaster.service.PilotService;

import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.dto.ShipType;
import com.devops.groupb.harbourmaster.dto.TimePeriod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@SpringBootApplication
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class HarbourMaster {
	private transient final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(this.getClass());

	@Autowired
	private PilotService pilotService;

	public static void main(String[] args) {
		SpringApplication.run(HarbourMaster.class, args);
	}

	@Bean
	public Docket harbourMasterAPI() {
		return new Docket(DocumentationType.SWAGGER_2)
			.directModelSubstitute(LocalTime.class, String.class)
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.devops.groupb.harbourmaster.controller"))
			.paths(PathSelectors.any()).build();
	}

	@PostConstruct
	public void addExamplePilots() {
		Pilot pilot1 = new Pilot(new ArrayList<ShipType>() {{
			add(ShipType.CARGO);
			add(ShipType.PASSENGER);
			add(ShipType.FERRY);
		}},
			"John", "Smith", LocalDate.of(1970, 1, 1),
			new HashMap<DayOfWeek, TimePeriod>() {{
				put(DayOfWeek.MONDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.TUESDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.WEDNESDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.THURSDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.FRIDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.SATURDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.SUNDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
			}});

		Pilot pilot2 = new Pilot(new ArrayList<ShipType>() {{
			add(ShipType.CARGO);
			add(ShipType.PASSENGER);
			add(ShipType.FERRY);
		}},
			"Adrian", "Pilot", LocalDate.of(1986, 5, 21),
			new HashMap<DayOfWeek, TimePeriod>() {{
				put(DayOfWeek.MONDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.TUESDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.WEDNESDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.THURSDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.FRIDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.SATURDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.SUNDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
			}});

		Pilot pilot3 = new Pilot(new ArrayList<ShipType>() {{
			add(ShipType.CARGO);
			add(ShipType.PASSENGER);
			add(ShipType.FERRY);
		}},
			"Lucas", "Green", LocalDate.of(1993, 10, 3),
			new HashMap<DayOfWeek, TimePeriod>() {{
				put(DayOfWeek.MONDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.TUESDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.WEDNESDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.THURSDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.FRIDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.SATURDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.SUNDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
			}});

		Pilot pilot4 = new Pilot(new ArrayList<ShipType>() {{
			add(ShipType.FERRY);
			add(ShipType.CARGO);
			add(ShipType.PASSENGER);
		}},
			"Brian", "Teal", LocalDate.of(1972, 2, 3),
			new HashMap<DayOfWeek, TimePeriod>() {{
				put(DayOfWeek.MONDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.TUESDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.WEDNESDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.THURSDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.FRIDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.SATURDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.SUNDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
			}});

		Pilot pilot5 = new Pilot(new ArrayList<ShipType>() {{
			add(ShipType.FERRY);
			add(ShipType.CARGO);
			add(ShipType.PASSENGER);
		}},
			"Jack", "Brush", LocalDate.of(1983, 11, 13),
			new HashMap<DayOfWeek, TimePeriod>() {{
				put(DayOfWeek.MONDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.TUESDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.WEDNESDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.THURSDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.FRIDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.SATURDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
				put(DayOfWeek.SUNDAY, new TimePeriod(LocalTime.of(0, 00), LocalTime.of(23, 00)));
			}});

		pilotService.createNewPilot(pilot1);
		pilotService.createNewPilot(pilot2);
		pilotService.createNewPilot(pilot3);
		pilotService.createNewPilot(pilot4);
		pilotService.createNewPilot(pilot5);
	}
}

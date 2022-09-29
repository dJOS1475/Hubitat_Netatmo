/**
 *
 *	Netatmo Additional Module driver
 *
 *	Based on Brian Steere's original Code
 *
 *	Enhancements for Hubitat by CybrMage - March 3, 2020
 *
 */

def version() { return "cybr-030420" }

metadata {
	definition (name: "Netatmo Additional Module", namespace: "fuzzysb", author: "Stuart Buchanan") {
		capability "Sensor"
		capability "Battery"
		capability "Relative Humidity Measurement"
		capability "Temperature Measurement"
		capability "Carbon Dioxide Measurement"
		capability "Refresh"

		attribute "min_temp", "number"
		attribute "max_temp", "number"
		attribute "temp_trend", "string"
		attribute "lastupdate", "string"

		attribute "Summary", "string"
	}

	
	preferences {
		input title: "Settings", description: "To change units and time format, go to the Netatmo Connect App", displayDuringSetup: false, type: "paragraph", element: "paragraph"
		input title: "Information", description: "Your Netatmo station updates the Netatmo servers approximately every 10 minutes. The Netatmo Connect app polls these servers every 5 minutes. If the time of last update is equal to or less than 10 minutes, pressing the refresh button will have no effect", displayDuringSetup: false, type: "paragraph", element: "paragraph"
	}
	
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

def poll() {
	log.debug "Polling"
	parent.poll()
}

def refresh() {
	log.debug "Refreshing"
	parent.poll()
}

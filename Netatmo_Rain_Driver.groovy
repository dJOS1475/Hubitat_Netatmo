/**
 *
 *	Netatmo Rain Driver
 *
 *	Based on Brian Steere's original Code
 *
 *	Enhancements for Hubitat by CybrMage - March 3, 2020
 *  Now Maintained by dJOS as of 2022
 *
 */

def version() { return "v1.6" }

metadata {
	definition (name: "Netatmo Rain", namespace: "dJOS", author: "Derek Osborn", importUrl: "https://raw.githubusercontent.com/dJOS1475/Hubitat_Netatmo/refs/heads/main/Netatmo_Rain_Driver.groovy") {
		capability "Sensor"
		capability "Battery"
		capability "Refresh"

		attribute "rain", "number"
		attribute "rainSumHour", "number"
		attribute "rainSumDay", "number"
		attribute "units", "string"
		attribute "lastupdate", "string"
		attribute "Summary", "string"
	
		command "poll"
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

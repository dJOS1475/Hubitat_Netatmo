# Netatmo (Connect) - Weather Station
Netatmo Integration for Hubitat

Enhancements for Hubitat by CybrMage - March 3, 2020

  Aestetic tweaks by dJOS - September 2022
      
  Battery Status order changed + minor tweaks to summary tile aesthetic's

v1.7.1 - Signal strength (WiFi/RF) shown as Good/Average/Poor bands instead of raw values, and moved onto the last (Battery) row in the Summary tiles
 	       - Overview tile no longer prints "null" for Outdoor/Wind/Rain when the base station has no such module feeding it (lines are now omitted when data is absent)
 	       - Additional device attributes now available: presence (online/offline), firmware and last_seen (all devices); wifi_status (base); rf_status and battery_vp (modules); AbsolutePressure (base); max_wind_angle (wind)
 	v1.7.0 - Bug fix: use a dedicated state.authToken for the Netatmo API token so it no longer collides with the Hubitat OAuth token (state.accessToken)
 	       - Security fix: removed client_secret from the OAuth authorize URL (it is only used server-side during token exchange)
 	       - Removed unused state.response (full API payload was stored every poll but never read) to reduce state churn
 	       - "Connect to netatmo" button now opens the Netatmo authentication page in a new browser tab (style:"external")
 	       - Hardened OAuth token parsing (parseTokenResponse) to handle both a normally-parsed map and the legacy key-as-JSON-string response form
 	       - Removed dead/non-functional auth block (and unused parseAuthResponse) from oauthInitUrl()
 	       - Replaced invalid sendPush() call with log.warn in checkloc()
 	       - Redacted client_secret/credentials from debug logs during token exchange
 	       - Cleanup: removed unused DecimalFormat import and unused debugEvent(); UTF-8 charset on toQueryString; simplified redundant map merges; added command "poll" to all drivers for consistency
 	       - Added user-selectable polling interval (5/10/15 minutes) in the app config; defaults to 5 minutes
 	       - Sound Sensor threshold now defaults to 50 dB and is null-safe (no longer errors when left blank)
 	       - New attributes from the API: presence (reachable), wifi_status/rf_status, battery_vp, firmware, last_seen, AbsolutePressure (base), max_wind_angle (wind)
 	       - Summary tiles: added Absolute pressure + WiFi (base), RF status (all modules), and Max Gust direction (wind)
 	v1.6 - Bug fixes: OAuth token parsing, stray syntax error, capability names, namespace/author update
 	v1.5 - Added a manual reauthorize option
